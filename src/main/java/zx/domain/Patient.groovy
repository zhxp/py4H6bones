package zx.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.Size

@Entity
class Patient {
    @Id
    @GeneratedValue
    Long id
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt
    @ManyToOne
    @CreatedBy
    User createdBy
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
    @ManyToOne
    User doctor
    @Enumerated(EnumType.STRING)
    PatientType patientType
    @Temporal(TemporalType.DATE)
    Date surgeryDate
    @Temporal(TemporalType.DATE)
    Date dischargeDate
    @ManyToOne
    SurgeryType surgeryType
    @ManyToOne
    User surgeryBy
    @Size(max = 1000)
    String surgeryMemo
    int stage
    @Temporal(TemporalType.DATE)
    Date trainingDayOne
}
