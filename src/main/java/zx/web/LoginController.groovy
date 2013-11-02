package zx.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.extension.Login
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import zx.domain.User
import zx.service.PatientService
import zx.service.UserService

import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping('/')
class LoginController {
    @Autowired
    private PatientService patientService
    @Autowired
    UserService userService

    @RequestMapping()
    public String login() {
        return 'login'
    }

    @RequestMapping('home')
    def loginHome(HttpServletRequest request, @Login(active = true) User user, Model model) {
        home(request, null, user, model)
    }

    @RequestMapping('home/{doctorId}')
    public String home(HttpServletRequest request, @PathVariable Long doctorId, @Login(active = true) User user, Model model) {
        if (user == null) {
            return 'redirect:/'
        }
        if (request.isUserInRole('ROLE_ADMIN')) {
            return 'admin/home'
        } else {
            List<User> employees = new LinkedList<>()
            flattenEmployees(user, employees)
            def doctor = user
            if (doctorId) {
                doctor = employees.find { doctorId == it.id }?: user
            }
            def docTree = []
            def node = doctor
            while (node) {
                docTree << node
                node = node.supervisor
            }
            model.addAttribute('docTree', docTree.reverse())
            model.addAttribute('doctor', doctor)
            model.addAttribute('user', user)
            model.addAttribute('employees', employees)
            model.addAttribute('surgeryTypes', patientService.findSurgeryTypes())
            return 'user/home'
        }
    }

    @RequestMapping('changePassword')
    @ResponseBody
    def changePassword(@Login(active = true) User user, String oldPassword, String newPassword) {
        def result = [:]
        result.errors = []
        if (oldPassword != user.password) {
            result.errors << '原密码错误'
        } else {
            user = userService.findById(user.id)
            user.password = newPassword
            userService.save(user)
        }
        result
    }

    private void flattenEmployees(User supervisor, List<User> employees) {
        for (User employee : supervisor.getEmployees()) {
            employees.add(employee)
            flattenEmployees(employee, employees)
        }
    }
}
