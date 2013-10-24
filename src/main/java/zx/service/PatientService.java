package zx.service;

import zx.domain.*;

import java.util.Date;
import java.util.List;

public interface PatientService {
    Patient findById(Long id);
    void save(Patient patient);
    Patient findByPid(String pid);
    List<Patient> findRegisteredPatientsByDoctor(User doctor);
    List<Patient> findPlannedPatientsByDoctor(User doctor);
    List<Patient> findTrainedPatientsByDoctor(User doctor);
    List<Patient> findFinishedPatientsByDoctor(User doctor);
    List<SurgeryType> findSurgeryTypes();
    SurgeryType findSurgeryTypeById(Long surgeryTypeId);
    Plan findPlan(Patient patient, Date date);
    void save(Training training);
    Training sumTrainingInDays3(Patient patient);
    List<Training> findTraining(Patient patient);
    Training findTrainingById(Long id);
    void saveSurgery(SurgeryType surgeryType);
    List<PatientMessage> findMessages(Patient patient);
    void markFinished(String pid);
}
