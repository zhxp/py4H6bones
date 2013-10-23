package zx.repository

import org.springframework.data.jpa.repository.JpaRepository
import zx.domain.Patient
import zx.domain.Training

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByPlanPatientAndStartedAtGreaterThan(Patient patient, Date startedAt)
    List<Training> findByPlanPatient(Patient patient)
}