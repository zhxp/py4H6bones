package zx.repository

import org.springframework.data.jpa.repository.JpaRepository
import zx.domain.Patient
import zx.domain.PatientMessage

public interface PatientMessageRepository extends JpaRepository<PatientMessage, Long> {
    List<PatientMessage> findByPatient(Patient patient)
}