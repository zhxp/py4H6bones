package zx.web
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import zx.domain.Patient
import zx.domain.PatientType
import zx.domain.Plan
import zx.service.PatientService
import zx.service.PlanService
import zx.service.UserService
import zx.web.bean.PatientBean
import zx.web.bean.PlanBean

import java.text.SimpleDateFormat

@Controller
@RequestMapping('patient')
class PatientController {
    @Autowired
    PatientService patientService
    @Autowired
    UserService userService
    @Autowired
    PlanService planService

    @RequestMapping('registered/{doctorId}')
    @ResponseBody
    List<Map<String, Object>> findRegisteredPatients(@PathVariable Long doctorId) {
        def doctor = userService.findById(doctorId)
        List<Patient> patients = patientService.findRegisteredPatientsByDoctor(doctor)
        List<Map<String, Object>> result = new ArrayList<>(patients.size())
        for (Patient patient : patients) {
            Map<String, Object> o = new HashMap<>()
            result.add(o)
            o.put('pid', patient.getPid())
            o.put('name', patient.getName())
            o.put('phone', patient.getPhone())
        }
        return result
    }

    @RequestMapping('planned/{doctorId}')
    @ResponseBody
    List<Map<String, Object>> findPlannedPatients(@PathVariable Long doctorId) {
        def doctor = userService.findById(doctorId)
        List<Patient> patients = patientService.findPlannedPatientsByDoctor(doctor)
        List<Map<String, Object>> result = new ArrayList<>(patients.size())
        for (Patient patient : patients) {
            Map<String, Object> o = new HashMap<>()
            result.add(o)
            o.put('pid', patient.getPid())
            o.put('name', patient.getName())
            o.put('phone', patient.getPhone())
        }
        return result
    }

    @RequestMapping('trained/{doctorId}')
    @ResponseBody
    def findTrainedPatients(@PathVariable Long doctorId) {
        def doctor = userService.findById(doctorId)
        def result = []
        def patients = patientService.findTrainedPatientsByDoctor(doctor)
        def dateFormat = new SimpleDateFormat('yyyy-MM-dd')
        patients.each {
            def p = [:]
            result << p
            p.pid = it.pid
            p.name = it.name
            p.surgeryDate = dateFormat.format(it.surgeryDate)
            p.surgeryName = it.surgeryType.name
            def training = patientService.sumTrainingInDays3(it)
            p.feeling = training.feelingLabel()
            p.effect = training.effectLabel()
            p.reaction = training.reactionLabel()
        }
        result
    }

    @RequestMapping('bean/{pid}')
    @ResponseBody
    PatientBean getPatientInfo(@PathVariable String pid) {
        def patient = patientService.findByPid(pid)
        return new PatientBean(patient)
    }

    @RequestMapping('save')
    @ResponseBody
    Map<String, Object> updatePatient(@RequestBody PatientBean bean) {
        def result = [errors: []]
        def patient = bean.toPatient()
        try {
            patientService.save(patient)
        } catch (Exception e) {
            result.errors << e.getMessage()
        }
        return result
    }

    @RequestMapping('edit')
    @ResponseBody
    Map<String, Object> editPatientInfo(@RequestBody PatientBean patientInfoBean) {
        Map<String, Object> result = new HashMap<>()
        List<String> errors = new LinkedList<>()
        Patient patient = patientService.findByPid(patientInfoBean.getPid())
        patientInfoBean.copyTo(patient, patientService, userService)
        patientService.save(patient)
        return result
    }

    @RequestMapping('createPlan/{pid}')
    @ResponseBody
    List<String> createPlan(@PathVariable String pid, @RequestBody List<PlanBean> beans) {
        List<String> errors = new LinkedList<>()
        Patient patient = patientService.findByPid(pid)
        if (patient.getPatientType() != PatientType.REGISTERED && patient.getPatientType() != PatientType.PLANNED) {
            errors.add('不能修改当前病人计划，当前病人状态为：' + patient.getPatientType().getViewText())
        } else {
            List<Plan> plans = new ArrayList<>(beans.size())
            for (PlanBean bean : beans) {
                plans.add(bean.toPlan())
            }
            planService.createPlan(patient, plans)
        }
        return errors
    }

    @RequestMapping('plans/{pid}')
    @ResponseBody
    List<PlanBean> findPlans(@PathVariable String pid) {
        List<Plan> plans = planService.findByPid(pid)
        List<PlanBean> planBeans = new ArrayList<>(plans.size())
        for (Plan plan : plans) {
            planBeans.add(new PlanBean(plan))
        }
        return planBeans
    }

    @RequestMapping('training/{pid}')
    @ResponseBody
    def trainings(@PathVariable String pid) {
        def patient = patientService.findByPid(pid)
        def list = patientService.findTraining(patient)
        def result = []
        def dateFormat = new SimpleDateFormat('yyyy-MM-dd HH:mm')
        list.each {
            def t = [:]
            result << t
            t.id = it.id
            t.date = dateFormat.format(it.startedAt)
            t.stage = it.plan.stage
            t.steps = it.steps
            t.planSteps = it.plan.steps
            t.averagePressure = it.averagePressure
            t.PlanPressure = it.plan.pressure
            t.feeling = it.feelingLabel()
            t.effect = it.effectLabel()
            t.reaction = it.reactionLabel()
            t.overrunRate = it.overrunRate
            t.hasMemo = it.memo ? 1 : 0
        }
        result
    }

    @RequestMapping('graph/{id}')
    @ResponseBody
    def graph(@PathVariable Long id) {
        def training = patientService.findTrainingById(id)
        def result = [:]
        if (training) {
            result.memo = training.memo
            result.pressure = training.plan.pressure
            result.points = training.points
        }
        result
    }

    @RequestMapping('chart/{id}')
    def chart(@PathVariable Long id, Model model) {
        def training = patientService.findTrainingById(id)
        model.addAttribute('bean', training)
        'user/chart'
    }
}
