package zx.web
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import zx.domain.SurgeryType
import zx.domain.User
import zx.service.PatientService
import zx.service.UserService
import zx.web.bean.DoctorTreeBean
import zx.web.bean.UserBean

@Controller
@RequestMapping('admin')
class AdminController {
    @Autowired
    UserService userService
    @Autowired
    PatientService patientService

    @RequestMapping('editDoctor')
    @ResponseBody
    def editDoctor(@RequestBody UserBean bean) {
        def result = [errors:[]]
        def doctor = bean.toUser()
        doctor.authority = 'ROLE_USER'
        try {
            userService.save(doctor as User)
        } catch (Exception e) {
            result.errors << e.message
        }
        result
    }

    @RequestMapping('doctors')
    @ResponseBody
    def doctors() {
        def doctors = userService.findAllDoctors()
        def beans = []
        doctors.each { beans << new UserBean(it) }
        beans
    }

    @RequestMapping('supervisors')
    @ResponseBody
    def supervisors(@RequestParam(required = false, defaultValue = '0') Long userId) {
        def doctors = userService.findAllDoctors()
        def result = []
        doctors.each {
            if (it.id != userId) {
                result << [id: it.id, username: it.username, displayName: it.displayName]
            }
        }
        result
    }

    @RequestMapping('doctor/{id}')
    @ResponseBody
    def doctor(@PathVariable Long id) {
        def doctor = userService.findById(id)
        new UserBean(doctor)
    }

    @RequestMapping('tree')
    @ResponseBody
    def treeData() {
        def doctors = userService.findTopLevelDoctors()
        def beans = []
        doctors.each { beans << new DoctorTreeBean(it) }
        beans
    }

    @RequestMapping('doctorTree')
    static def doctorTree() {
        'admin/doctorTree'
    }

    @RequestMapping('surgeries')
    @ResponseBody
    def surgeries() {
        patientService.findSurgeryTypes()
    }

    @RequestMapping('editSurgery')
    @ResponseBody
    def editSurgery(@RequestBody SurgeryType surgeryType) {
        patientService.saveSurgery(surgeryType)
    }
}
