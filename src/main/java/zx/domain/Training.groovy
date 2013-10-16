package zx.domain

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Training {
    @Id
    @GeneratedValue
    Long id
    @ManyToOne
    Schedule schedule
    @OneToMany(mappedBy = 'training', cascade = CascadeType.ALL, orphanRemoval = true)
    List<TrainingPoint> points
    int feeling
    int effect
    int reaction
    String memo
    int steps
    int averagePressure
}
