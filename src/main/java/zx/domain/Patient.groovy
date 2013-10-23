package zx.domain

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class Patient {
    @Id
    @GeneratedValue
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
    @Size(max = 1000)
    String surgeryMemo
    @Transient
    List<Plan> plans = []
    @Temporal(TemporalType.DATE)
    Date startedOn
}
