package zx.service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zx.domain.Patient
import zx.domain.PatientType
import zx.domain.Plan
import zx.repository.PatientRepository
import zx.repository.PlanRepository

@Service("planService")
@Transactional(readOnly = true)
class PlanServiceImpl implements PlanService {
    @Autowired
    PlanRepository planRepository
    @Autowired
    PatientRepository patientRepository

    @Override
    Plan findById(Long id) {
        id ? planRepository.findOne(id) : null
    }

    @Override
    @Transactional
    void createPlan(Patient patient, List<Plan> plans) {
        List<Plan> oldPlans = planRepository.findByPatient(patient)
        if (oldPlans.size()) {
            planRepository.delete((List<Plan>) oldPlans)
        }
        plans.each {it.patient = patient}
        planRepository.save(plans)
        patient.patientType = plans.size() ? PatientType.PLANNED : PatientType.REGISTERED
        patientRepository.save(patient)
    }

    @Override
    List<Plan> findByPid(String pid) {
        List<Patient> patients = patientRepository.findByPid(pid)
        if (patients) {
            return planRepository.findByPatient(patients[0])
        } else {
            return new LinkedList<Plan>()
        }
    }
}
