package zx.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class PatientMessage {
    @Id
    @GeneratedValue
    Long id
    @ManyToOne
    Patient patient
    String message
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt = new Date()
    @ManyToOne
    User createdBy
}
