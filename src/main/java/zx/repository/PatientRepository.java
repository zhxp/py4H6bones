package zx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zx.domain.Patient;
import zx.domain.PatientType;
import zx.domain.User;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByPid(String pid);
    List<Patient> findByPatientTypeAndDoctor(PatientType patientType, User doctor);
}
