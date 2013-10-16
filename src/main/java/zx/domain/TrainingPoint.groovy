package zx.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class TrainingPoint {
    @Id
    @GeneratedValue
    Long id
    @ManyToOne
    Training training
    @Temporal(TemporalType.TIMESTAMP)
    Date sampleTime
    Double value
}
