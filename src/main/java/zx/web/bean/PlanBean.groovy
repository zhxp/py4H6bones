package zx.web.bean

import zx.domain.Plan

class PlanBean {
    int stage
    int days
    int times
    int steps
    int pressure

    PlanBean() {
    }

    PlanBean(Plan plan) {
        stage = plan.stage
        days = plan.days
        times = plan.times
        steps = plan.steps
        pressure = plan.pressure
    }

    void copyTo(Plan plan) {
        plan.stage = stage
        plan.days = days
        plan.times = times
        plan.steps = steps
        plan.pressure = pressure
    }
}
