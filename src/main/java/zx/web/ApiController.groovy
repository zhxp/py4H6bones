package zx.web
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import zx.domain.Training
import zx.service.PatientService
import zx.web.bean.TrainingBean

@Controller
@RequestMapping('api')
class ApiController {
    @Autowired
    PatientService patientService

    @RequestMapping('login')
    @ResponseBody
    def login(@RequestParam(defaultValue = '') String pid, String password) {
        def result = [success: 0]
        def patient = patientService.findByPid(pid)
        if (patient && patient.password == password) {
            result.success = 1
        }
        result
    }

    @RequestMapping('plan/{pid}')
    @ResponseBody
    def plan(@PathVariable String pid) {
        def result = [:]
        def patient = patientService.findByPid(pid)
        def plan = patientService.findPlan(patient, null)
        if (plan) {
            result << [stage: plan.stage]
            result << [days: plan.days]
            result << [times: plan.times]
            result << [steps: plan.steps]
            result << [pressure: plan.pressure]
            result << [startedOn: plan.startedOn ? new DateTime(plan.startedOn).toString('yyyy-MM-dd') : '']
        }
        result
    }

    @RequestMapping('upload/{pid}')
    @ResponseBody
    def uploadTraining(@PathVariable String pid, @RequestBody TrainingBean bean) {
        def result = [errors:[]]
        bean.pid = pid
        def training = bean.toTraining()
        patientService.save(training as Training)
        result
    }
}
