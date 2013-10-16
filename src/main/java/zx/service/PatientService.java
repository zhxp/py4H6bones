package zx.service;

import zx.domain.Patient;
import zx.domain.SurgeryType;
import zx.domain.User;
import zx.exception.ExistedException;

import java.util.List;

public interface PatientService {
    Patient createPatient(String pid) throws ExistedException;
    void save(Patient patient);
    Patient findByPid(String pid);
    List<Patient> findNewPatientsByDoctor(User doctor);
    List<Patient> findRegisteredPatientsByDoctor(User doctor);
    List<Patient> findPlannedPatientsByDoctor(User doctor);
    List<SurgeryType> findSurgeryTypes();
    SurgeryType findSurgeryTypeById(Long surgeryTypeId);
}
