package zx.web.bean

import org.joda.time.DateTime
import zx.domain.Training
import zx.service.Services

class TrainingBean {
    transient String pid
    String startedAt
    int times
    List<Integer> pressures = []
    int feeling
    int effect
    int reaction
    String memo

    def toTraining() {
        def patientService = Services.patientService()
        def patient = patientService.findByPid(pid)
        def training = new Training()
        def plan = patientService.findPlan(patient, null)
        training.plan = plan
        training.startedAt = new DateTime(startedAt).toDate()
        training.points.addAll(pressures)
        training.feeling = feeling
        training.effect = effect
        training.reaction = reaction
        training.memo = memo
        training.steps = pressures.size()
        training.averagePressure = pressures.sum() / pressures.size()
        def planPressure = plan.pressure
        def ceiling = planPressure * 1.5
        training.overrunRate = 100 * pressures.findAll { it > ceiling }.size() / pressures.size()
        training
    }
}
