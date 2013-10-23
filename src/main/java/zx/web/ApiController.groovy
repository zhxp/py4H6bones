package zx.web
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import zx.domain.Training
import zx.service.PatientService
import zx.web.bean.TrainingBean

import java.text.SimpleDateFormat

@Controller
@RequestMapping('api')
class ApiController {
    @Autowired
    PatientService patientService

    @RequestMapping('login')
    @ResponseBody
    def login(@RequestParam(defaultValue = '') String pid, String password, String token) {
        def result = [success: 0]
        def patient = patientService.findByPid(pid)
        if (patient && patient.password == password) {
            result.success = 1
            patient.token = token
            patientService.save(patient)
            result.name = patient.name
            switch(patient.sex) {
                case 0: result.sex = '女'; break;
                case 1: result.sex = '男'; break;
                default: result.sex = ''
            }
            result.height = patient.height
            result.weight = patient.weight
            result.surgery = patient.surgeryType?.name
            result.age = patient.age
            result.phone = patient.phone
            def dateFormat = new SimpleDateFormat('yyyy-MM-dd')
            result.surgeryDate = patient.surgeryDate ? dateFormat.format(patient.surgeryDate) : ''
            result.dischargeDate = patient.dischargeDate ? dateFormat.format(patient.dischargeDate) : ''
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

    @RequestMapping('messages/{pid}')
    @ResponseBody
    def messages(@PathVariable String pid) {
        def patient = patientService.findByPid(pid)
        def result = []
        if (patient) {
            def list = patientService.findMessages(patient)
            def dateFormat = new SimpleDateFormat('yyyy-MM-dd HH:mm')
            list.each { result << [id: it.id, text: it.message, time: dateFormat.format(it.createdAt)] }
        }
        result
    }

    @RequestMapping('histories/{pid}')
    @ResponseBody
    def histories(@PathVariable String pid) {
        def patient = patientService.findByPid(pid)
        def list = patientService.findTraining(patient)
        def result = []
        list.each { result << new TrainingBean(it) }
        result
    }
}
