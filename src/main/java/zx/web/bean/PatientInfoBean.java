package zx.web.bean;

import zx.domain.Patient;
import zx.domain.PatientType;
import zx.service.PatientService;
import zx.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientInfoBean {
    private String pid;
    private String phone;
    private String password;
    private String name;
    private Integer height;
    private Integer weight;
    private Double bmi;
    private Integer sex;
    private Integer age;
    private String email;
    private String address;
    private PatientType patientType;
    private Date surgeryDate;
    private Date dischargeDate;
    private Long surgeryTypeId;
    private Long surgeonId;
    private String surgeryMemo;

    private String surgeonName;
    private String surgeryName;
    private String surgeryDateString;
    private String dischargeDateString;

    public PatientInfoBean() {
    }

    public void assignFrom(Patient patient) {
        pid = patient.getPid();
        phone = patient.getPhone();
        password = patient.getPassword();
        name = patient.getName();
        height = patient.getHeight();
        weight = patient.getWeight();
        bmi = patient.getBmi();
        sex = patient.getSex();
        age = patient.getAge();
        email = patient.getEmail();
        address = patient.getAddress();
        patientType = patient.getPatientType();
        surgeryDate = patient.getSurgeryDate();
        dischargeDate = patient.getDischargeDate();
        surgeryTypeId = patient.getSurgeryType() != null ? patient.getSurgeryType().getId() : null;
        surgeonId = patient.getSurgeryBy() != null ? patient.getSurgeryBy().getId() : null;
        surgeryMemo = patient.getSurgeryMemo();

        surgeonName = surgeonId != null ? patient.getSurgeryBy().getDisplayName() : null;
        surgeryName = surgeryTypeId != null ? patient.getSurgeryType().getName() : null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        surgeryDateString = surgeryDate != null ? dateFormat.format(surgeryDate) : null;
        dischargeDateString = dischargeDate != null ? dateFormat.format(dischargeDate) : null;
    }

    public void copyTo(Patient patient, PatientService patientService, UserService userService) {
        patient.setPhone(phone);
        patient.setPassword(password);
        patient.setName(name);
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setBmi(bmi);
        patient.setSex(sex);
        patient.setAge(age);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setPatientType(patientType);
        patient.setSurgeryDate(surgeryDate);
        patient.setDischargeDate(dischargeDate);
        patient.setSurgeryType(patientService.findSurgeryTypeById(surgeryTypeId));
        patient.setSurgeryBy(userService.findById(surgeonId));
        patient.setSurgeryMemo(surgeryMemo);
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PatientType getPatientType() {
        return patientType;
    }

    public void setPatientType(PatientType patientType) {
        this.patientType = patientType;
    }

    public Date getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(Date surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public Date getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Date dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Long getSurgeryTypeId() {
        return surgeryTypeId;
    }

    public void setSurgeryTypeId(Long surgeryTypeId) {
        this.surgeryTypeId = surgeryTypeId;
    }

    public Long getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(Long surgeonId) {
        this.surgeonId = surgeonId;
    }

    public String getSurgeryMemo() {
        return surgeryMemo;
    }

    public void setSurgeryMemo(String surgeryMemo) {
        this.surgeryMemo = surgeryMemo;
    }

    public String getSurgeonName() {
        return surgeonName;
    }

    public void setSurgeonName(String surgeonName) {
        this.surgeonName = surgeonName;
    }

    public String getSurgeryName() {
        return surgeryName;
    }

    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
    }

    public String getSurgeryDateString() {
        return surgeryDateString;
    }

    public void setSurgeryDateString(String surgeryDateString) {
        this.surgeryDateString = surgeryDateString;
    }

    public String getDischargeDateString() {
        return dischargeDateString;
    }

    public void setDischargeDateString(String dischargeDateString) {
        this.dischargeDateString = dischargeDateString;
    }
}
