package zx.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Services {
    static Services instance

    @Autowired
    UserService userService
    @Autowired
    PlanService planService
    @Autowired
    PatientService patientService

    Services() {
        instance = this
    }

    static UserService userService() {
        instance?.userService
    }

    static PlanService planService() {
        instance?.planService
    }

    static PatientService patientService() {
        instance?.patientService
    }
}
