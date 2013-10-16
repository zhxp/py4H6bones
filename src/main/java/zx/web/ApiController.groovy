package zx.web
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import zx.domain.PatientType
import zx.service.PatientService
import zx.service.ScheduleService
import zx.web.bean.RegisterBean
import zx.web.bean.ScheduleBean
import zx.web.bean.TrainingBean

@Controller
@RequestMapping('api')
class ApiController {
    @Autowired
    PatientService patientService
    @Autowired
    ScheduleService scheduleService

    @RequestMapping('register')
    @ResponseBody
    def register(@RequestBody RegisterBean register) {
        def map = [:]
        def patient = patientService.findByPid(register.getPid())
        if (patient == null) {
            map << [pid: '未找到用户']
        } else if (patient.patientType != PatientType.NEW) {
            map << [pid: '用户已注册']
        } else {
            register.copyTo(patient)
            patientService.save(patient)
        }
        map
    }

    @RequestMapping('schedule/{pid}')
    @ResponseBody
    def getSchedule(String pid) {
        def today = new DateTime().withTimeAtStartOfDay()
        def schedule = scheduleService.getSchedule(pid, today.toDate())
        return new ScheduleBean(schedule)
    }

    @RequestMapping('upload/{pid}')
    @ResponseBody
    def uploadTraining(String pid, @RequestBody TrainingBean trainingBean) {

    }
}
