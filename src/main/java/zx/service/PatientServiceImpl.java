package zx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.domain.Patient;
import zx.domain.PatientType;
import zx.domain.SurgeryType;
import zx.domain.User;
import zx.exception.ExistedException;
import zx.repository.PatientRepository;
import zx.repository.SurgeryTypeRepository;
import zx.util.CmnUtils;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SurgeryTypeRepository surgeryTypeRepository;

    @Override
    @Transactional
    public Patient createPatient(String pid) throws ExistedException {
        Patient patient = findByPid(pid);
        if (patient != null) {
            throw new ExistedException(pid);
        }
        patient = new Patient();
        patient.setPid(pid);
        patient.setPatientType(PatientType.NEW);
        patient.setDoctor(loginService.getCurrentUser());
        save(patient);
        return patient;
    }

    @Override
    @Transactional
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Patient findByPid(String pid) {
        List<Patient> patients = patientRepository.findByPid(pid);
        return patients.isEmpty() ? null : patients.get(0);
    }

    @Override
    public List<Patient> findNewPatientsByDoctor(User doctor) {
        return patientRepository.findByPatientTypeAndDoctor(PatientType.NEW, doctor);
    }

    @Override
    public List<Patient> findRegisteredPatientsByDoctor(User doctor) {
        return patientRepository.findByPatientTypeAndDoctor(PatientType.REGISTERED, doctor);
    }

    @Override
    public List<Patient> findPlannedPatientsByDoctor(User doctor) {
        return patientRepository.findByPatientTypeAndDoctor(PatientType.PLANNED, doctor);
    }

    @Override
    public List<SurgeryType> findSurgeryTypes() {
        List<SurgeryType> surgeryTypes = surgeryTypeRepository.findAll();
        Collections.sort(surgeryTypes, CmnUtils.getSurgeryTypeNameComparator());
        return surgeryTypes;
    }

    @Override
    public SurgeryType findSurgeryTypeById(Long surgeryTypeId) {
        return surgeryTypeId == null ? null : surgeryTypeRepository.findOne(surgeryTypeId);
    }
}
