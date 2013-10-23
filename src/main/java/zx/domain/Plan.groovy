package zx.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class Plan {
    @Id
    @GeneratedValue
    Long id
    @ManyToOne
    Patient patient
    int stage
    int days
    int times
    int steps
    int pressure
    @Temporal(TemporalType.DATE)
    Date startedOn
    @Temporal(TemporalType.DATE)
    Date endedOn
}
