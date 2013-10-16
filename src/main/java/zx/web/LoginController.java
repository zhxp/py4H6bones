package zx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zx.domain.User;
import zx.service.PatientService;
import zx.web.config.Login;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping()
public class LoginController {
    @Autowired
    private PatientService patientService;

    @RequestMapping()
    public String login() {
        return "login";
    }

    @RequestMapping("home")
    public String home(HttpServletRequest request, @Login(active = true) User user, Model model) {
        if (user == null) {
            return "redirect:/";
        }
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "admin/home";
        } else {
            List<User> employees = new LinkedList<>();
            flattenEmployees(user, employees);
            model.addAttribute("employees", employees);
            model.addAttribute("surgeryTypes", patientService.findSurgeryTypes());
            return "user/home";
        }
    }

    private void flattenEmployees(User supervisor, List<User> employees) {
        for (User employee : supervisor.getEmployees()) {
            employees.add(employee);
            flattenEmployees(employee, employees);
        }
    }
}
