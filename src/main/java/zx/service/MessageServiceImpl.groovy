package zx.service
import javapns.integration.spring.JavapnsPushService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zx.domain.Patient
import zx.domain.PatientMessage
import zx.domain.User
import zx.repository.PatientMessageRepository

@Service
@Transactional(readOnly = true)
class MessageServiceImpl implements MessageService {
    Logger logger = LoggerFactory.getLogger(MessageServiceImpl)
    @Autowired
    PatientMessageRepository patientMessageRepository
    @Autowired
    JavapnsPushService javapnsPushService

    @Override
    @Transactional
    def send(Patient patient, final String message, User doctor) {
        def entity = new PatientMessage(patient: patient, message: message, createdBy: doctor)
        patientMessageRepository.save(entity as PatientMessage)
        final def token = patient.token
        if (token) {
            javapnsPushService.alert(message, token)
        }
    }
}
