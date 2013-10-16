package zx.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class Schedule {
    @Id
    @GeneratedValue
    Long id
    @Temporal(TemporalType.DATE)
    Date day
    @ManyToOne
    Plan plan
}
