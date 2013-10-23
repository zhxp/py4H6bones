package zx.web.bean

import zx.domain.Patient
import zx.domain.PatientType
import zx.service.Services

import java.text.DateFormat
import java.text.SimpleDateFormat

class PatientBean {
    Long id
    String pid
    String phone
    String password
    String name
    Integer height
    Integer weight
    Double bmi
    Integer sex
    Integer age
    String email
    String address
    PatientType patientType
    Date surgeryDate
    Date dischargeDate
    Long surgeryTypeId
    Long doctorId
    String surgeryMemo

    String doctorName
    String surgeryName
    String surgeryDateString
    String dischargeDateString

    List<PlanBean> plans = []

    PatientBean() {
    }

    PatientBean(Patient patient) {
        if (patient) {
            id = patient.id
            pid = patient.pid
            phone = patient.phone
            password = patient.password
            name = patient.name
            height = patient.height
            weight = patient.weight
            bmi = patient.bmi
            sex = patient.sex
            age = patient.age
            email = patient.email
            address = patient.address
            patientType = patient.patientType
            surgeryDate = patient.surgeryDate
            dischargeDate = patient.dischargeDate
            surgeryTypeId = patient.surgeryType?.id
            doctorId = patient.doctor.id
            surgeryMemo = patient.surgeryMemo

            doctorName = patient.doctor.displayName
            surgeryName = patient.surgeryType?.name
            DateFormat dateFormat = new SimpleDateFormat('yyyy-MM-dd')
            surgeryDateString = surgeryDate ? dateFormat.format(surgeryDate) : null
            dischargeDateString = dischargeDate ? dateFormat.format(dischargeDate) : null

            patient.plans.each { plans << new PlanBean(it) }
        }
    }

    Patient toPatient() {
        def patientService = Services.patientService()
        def patient = patientService.findById(id)?: new Patient()
        patient.pid = pid
        patient.phone = phone
        patient.password = password
        patient.name = name
        patient.height = height
        patient.weight = weight
        patient.bmi = bmi
        patient.sex = sex
        patient.age = age
        patient.email = email
        patient.address = address
        patient.surgeryDate = surgeryDate
        patient.dischargeDate = dischargeDate
        patient.surgeryType = patientService.findSurgeryTypeById(surgeryTypeId)
        patient.doctor = Services.userService().findById(doctorId)
        patient.surgeryMemo = surgeryMemo
        patient.plans.clear()
        plans.each {
            def plan = it.toPlan()
            plan.patient = patient
            patient.plans << plan
        }

        patient
    }
}
