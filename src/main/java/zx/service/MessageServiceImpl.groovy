package zx.service

import javapns.Push
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zx.domain.Patient
import zx.domain.PatientMessage
import zx.domain.User
import zx.repository.PatientMessageRepository

import javax.annotation.PostConstruct
import java.nio.ByteBuffer
import java.util.concurrent.Executors

@Service
@Transactional(readOnly = true)
class MessageServiceImpl implements MessageService {
    Logger logger = LoggerFactory.getLogger(MessageServiceImpl)
    static def pool = Executors.newFixedThreadPool(4)
    @Value('${mobilePush.apns.keyStore}')
    String keyStore;
    @Value('${mobilePush.apns.keyStorePassword}')
    String password;
    @Value('${mobilePush.apns.production}')
    boolean production;
    byte[] keyStoreBytes;
    @Autowired
    PatientMessageRepository patientMessageRepository

    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = MessageServiceImpl.getResourceAsStream(keyStore)
            int size = 0
            byte[] buffer = new byte[4096]
            ByteBuffer byteBuffer = ByteBuffer.allocate(inputStream.available())
            while ((size = inputStream.read(buffer)) > 0) {
                byteBuffer.put(buffer, 0, size)
            }
            inputStream.close()
            byteBuffer.flip()
            keyStoreBytes = byteBuffer.array()
        } catch (Exception e) {
            logger.error("apns init", e);
        }
    }

    @Override
    @Transactional
    def send(Patient patient, final String message, User doctor) {
        def entity = new PatientMessage(patient: patient, message: message, createdBy: doctor)
        patientMessageRepository.save(entity as PatientMessage)
        final def token = patient.token
        if (token) {
            pool.execute(new Runnable() {
                @Override
                void run() {
                    try {
                        def title = message.length() <= 30 ? message : message.substring(0, 28) + '...'
                        def list = Push.combined(title, 0, "default", keyStoreBytes, password, production, token)
//                        println "list = $list"
                    } catch (Exception e) {
                        logger.error("push to pid:$patient, msg:$message", e)
                    }
                }
            })
        }
    }
}
