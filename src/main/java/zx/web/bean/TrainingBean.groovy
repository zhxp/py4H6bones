package zx.web.bean

import org.joda.time.DateTime
import zx.domain.Training
import zx.service.Services

import java.text.SimpleDateFormat

class TrainingBean {
    transient String pid
    String startedAt
    int duration
    List<Double> pressures = []
    int feeling
//    int effect
    int[] reaction
    String memo

    TrainingBean() {
    }

    TrainingBean(Training training) {
        startedAt = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(training.startedAt)
        duration = training.duration
        pressures = training.points
        feeling = training.feeling
//        effect = training.effect
        reaction = training.reaction
        memo = training.memo
    }

    def toTraining() {
        def patientService = Services.patientService()
        def patient = patientService.findByPid(pid)
        def startedAt = new DateTime(new SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(startedAt))
        def plan = patientService.findPlan(patient, startedAt.toDate())
        def training = new Training()
        training.plan = plan
        training.startedAt = startedAt.toDate()
        training.duration = duration
        training.endedAt = startedAt.plusSeconds(duration).toDate()
        training.points.addAll(pressures)
        training.feeling = feeling
//        training.effect = effect
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
