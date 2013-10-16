package zx.service

import org.joda.time.DateTime
import org.joda.time.Days
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zx.domain.PatientType
import zx.domain.Schedule
import zx.repository.PatientRepository
import zx.repository.PlanRepository
import zx.repository.ScheduleRepository

@Service
@Transactional(readOnly = true)
class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository
    @Autowired
    PatientRepository patientRepository
    @Autowired
    PlanRepository planRepository

    @Override
    @Transactional
    Schedule getSchedule(String pid, Date when) {
        def schedule = scheduleRepository.findByPlanPatientPidAndDay(pid, when)[0]
        if (schedule) {
            return schedule
        } else {
            def patient = patientRepository.findByPid(pid)[0]
            if (patient) {
                if (patient.patientType == PatientType.PLANNED) {
                    def today = new DateTime().withTimeAtStartOfDay()
                    def plan = planRepository.findByPatientAndStage(patient, 1)[0]
                    return new Schedule(day: today.toDate(), plan: plan)
                } else if (patient.patientType == PatientType.TRAINED) {
                    def today = new DateTime().withTimeAtStartOfDay()
                    def offsetDays = Days.daysBetween(new DateTime(patient.trainingDayOne), today).days
                    def plans = planRepository.findByPatient(patient)
                    def planDays = 0
                    def plan = plans.find { offsetDays <= (planDays += it.days) }
                    patient.stage = plan.stage
                    patientRepository.save(patient)
                    def planStartDate = today.minusDays(plan.days - (planDays - offsetDays))
                    def schedules = []
                    plan.days.times {
                        schedules << new Schedule(plan: plan, day: planStartDate.plusDays(it).toDate())
                    }
                    scheduleRepository.save(schedule)
                    return scheduleRepository.findByPlanPatientPidAndDay(pid, when)[0]
                }
            }
        }
        return null
    }
}
