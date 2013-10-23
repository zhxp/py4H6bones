package zx.web.bean

import org.joda.time.DateTime
import zx.domain.Plan
import zx.service.Services

class PlanBean {
    Long id
    int stage
    int days
    int times
    int steps
    int pressure
    int started

    PlanBean() {
    }

    PlanBean(Plan plan) {
        id = plan.id
        stage = plan.stage
        days = plan.days
        times = plan.times
        steps = plan.steps
        pressure = plan.pressure
        started = (plan.startedOn && new DateTime().isAfter(plan.startedOn.time)) ? 1 : 0
    }

    Plan toPlan() {
        def planService = Services.planService()
        def plan = planService.findById(id)?: new Plan()
        plan.stage = stage
        plan.days = days
        plan.times = times
        plan.steps = steps
        plan.pressure = pressure
        plan
    }
}
