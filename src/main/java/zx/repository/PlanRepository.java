package zx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zx.domain.Patient;
import zx.domain.Plan;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByPatient(Patient patient);
    List<Plan> findByPatientAndStage(Patient patient, int stage);
}
