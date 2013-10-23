package zx.service

import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import zx.domain.*
import zx.exception.ExistedException
import zx.repository.PatientMessageRepository
import zx.repository.PatientRepository
import zx.repository.PlanRepository
import zx.repository.SurgeryTypeRepository
import zx.repository.TrainingRepository
import zx.util.CmnUtils

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository
    @Autowired
    LoginService loginService
    @Autowired
    SurgeryTypeRepository surgeryTypeRepository
    @Autowired
    PlanRepository planRepository
    @Autowired
    TrainingRepository trainingRepository
    @Autowired
    PatientMessageRepository patientMessageRepository

    @Override
    Patient findById(Long id) {
        def patient = id ? patientRepository.findOne(id) : null
        setPlans(patient)
    }

    @Override
    @Transactional
    void save(Patient patient) {
        def patients = patientRepository.findByPid(patient.pid)
        if (patients.size()) {
            if (!patient.id || patients.find({ it.id != patient.id })) {
                throw new ExistedException('病人标识已存在: ' + patient.pid)
            }
        }
        patient.doctor = patient.doctor ?: loginService.currentUser
        if (!patient.patientType) {
            patient.patientType = PatientType.REGISTERED
        } else if (patient.patientType == PatientType.REGISTERED && patient.plans.size()) {
            patient.patientType = PatientType.PLANNED
        } else if (patient.patientType == PatientType.PLANNED && !patient.plans.size()) {
            patient.patientType = PatientType.REGISTERED
        }
        patientRepository.save(patient)

        def oldPlans = planRepository.findByPatient(patient)
        def invalidPlans = oldPlans.findAll { o ->
            !(patient.plans.find { it.id && it.id == o.id})
        }
        planRepository.delete(invalidPlans as List)
        if (patient.startedOn) {
            def today = new DateTime().withTimeAtStartOfDay()
            def day = new DateTime(patient.startedOn).withTimeAtStartOfDay().minusDays(1)
            patient.plans.each {
                day = day.plusDays(1)
                if (!it.startedOn || today.isBefore(day)) {
                    it.startedOn = day.toDate()
                    it.endedOn = null
                }
                day = day.plusDays(it.days)
                if (!it.endedOn) {
                    it.endedOn = day.toDate()
                }
            }
        }
        planRepository.save(patient.plans)
    }

    @Override
    Patient findByPid(String pid) {
        List<Patient> patients = patientRepository.findByPid(pid)
        def patient = patients[0]
        setPlans(patient)
    }

    @Override
    List<Patient> findRegisteredPatientsByDoctor(User doctor) {
        def patients = patientRepository.findByPatientTypeAndDoctor(PatientType.REGISTERED, doctor)
        patients.each { setPlans(it) }
        patients
    }

    @Override
    List<Patient> findPlannedPatientsByDoctor(User doctor) {
        def patients = patientRepository.findByPatientTypeAndDoctor(PatientType.PLANNED, doctor)
        patients.each { setPlans(it) }
        patients
    }

    @Override
    List<Patient> findTrainedPatientsByDoctor(User doctor) {
        def patients = patientRepository.findByPatientTypeAndDoctor(PatientType.TRAINED, doctor)
        patients.each { setPlans(it) }
        patients
    }

    @Override
    List<SurgeryType> findSurgeryTypes() {
        List<SurgeryType> surgeryTypes = surgeryTypeRepository.findAll()
        Collections.sort(surgeryTypes, CmnUtils.getSurgeryTypeNameComparator())
        return surgeryTypes
    }

    @Override
    SurgeryType findSurgeryTypeById(Long surgeryTypeId) {
        return surgeryTypeId == null ? null : surgeryTypeRepository.findOne(surgeryTypeId)
    }

    private def setPlans(Patient patient) {
        if (patient) {
            patient.plans.clear()
            patient.plans.addAll(planRepository.findByPatient(patient))
        }
        patient
    }

    @Override
    Plan findPlan(Patient patient, Date date) {
        if (patient.patientType == PatientType.FINISHED) {
            return null
        }
        def plans = planRepository.findByPatient(patient)
        def plan
        if (!patient.startedOn) {
            plan = plans[0]
        } else {
            def day = new DateTime(date?: new Date()).withTimeAtStartOfDay().millis
            plan = plans.find { it.startedOn.time <= day && day <= it.endedOn.time }
        }
        plan
    }

    @Override
    @Transactional
    void save(Training training) {
        def patient = training.plan.patient
        if (!patient.startedOn) {
            if (patient.patientType != PatientType.FINISHED) {
                patient.patientType = PatientType.TRAINED
                patientRepository.save(patient)
            }

            def today = new DateTime().withTimeAtStartOfDay()
            patient.startedOn = today.toDate()
            def plans = planRepository.findByPatient(patient)
            def day = today.minusDays(1)
            plans.each {
                day = day.plusDays(1)
                it.startedOn = day.toDate()
                day = day.plusDays(it.days - 1)
                it.endedOn = day.toDate()
            }
            planRepository.save(plans as List)
        }
        trainingRepository.save(training)
    }

    @Override
    Training sumTrainingInDays3(Patient patient) {
        def training = new Training(feeling: -1, effect: -1, reaction: -1)
        def today = new DateTime().withTimeAtStartOfDay()
        def trainings = trainingRepository.findByPlanPatientAndStartedAtGreaterThan(patient,
                today.minusDays(2).toDate())
        if (trainings) {
            training.feeling = trainings.max { it.feeling }.feeling
            training.effect = trainings.max { it.effect }.effect
            training.reaction = trainings.max { it.reaction }.reaction
        }

        training
    }

    @Override
    List<Training> findTraining(Patient patient) {
        def list = trainingRepository.findByPlanPatient(patient)
        list.reverse(true)
        list
    }

    @Override
    Training findTrainingById(Long id) {
        trainingRepository.findOne(id)
    }

    @Override
    @Transactional
    void saveSurgery(SurgeryType surgeryType) {
        def surgery = surgeryType
        if (surgeryType.id) {
            surgery = new SurgeryType(name: surgeryType.name, id: surgeryType.id)
        }
        surgeryTypeRepository.save(surgery)
    }

    @Override
    List<PatientMessage> findMessages(Patient patient) {
        def list = patientMessageRepository.findByPatient(patient)
        list.reverse(true)
    }
}
