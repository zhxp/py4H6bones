package zx.web.bean

import zx.domain.Schedule

class ScheduleBean {
    PlanBean plan

    ScheduleBean() {
    }

    ScheduleBean(Schedule schedule) {
        if (schedule && schedule.plan) {
            plan = new PlanBean(
                    stage: schedule.plan.stage,
                    days: schedule.plan.days,
                    times: schedule.plan.times,
                    steps: schedule.plan.steps,
                    pressure: schedule.plan.pressure
            )
        }
    }
}
