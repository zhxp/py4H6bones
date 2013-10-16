package zx.web.bean;

import zx.domain.Patient;
import zx.domain.PatientType;

public class RegisterBean {
    private String pid;
    private String password;
    private String name;
    private int height;
    private int weight;
    private int sex;
    private int age;
    private String phone;
    private String email;
    private String address;

    public void copyTo(Patient patient) {
        patient.setPassword(password);
        patient.setName(name);
        patient.setHeight(height);
        patient.setWeight(weight);
        patient.setSex(sex);
        patient.setAge(age);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setAddress(address);
        if (patient.getPatientType() == PatientType.NEW) {
            patient.setPatientType(PatientType.REGISTERED);
        }
        if (height != 0) {
            patient.setBmi(weight / 2 / Math.pow((height / 100.0), 2));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
