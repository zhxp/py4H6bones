package zx.domain
import javax.persistence.*

@Entity
class Training {
    @Id
    @GeneratedValue
    Long id
    @ManyToOne
    Plan plan
    @Temporal(TemporalType.TIMESTAMP)
    Date startedAt
    @Temporal(TemporalType.TIMESTAMP)
    Date endedAt
    int duration
    @ElementCollection
    List<Double> points = []
    int feeling
    int effect
    int reaction
    String memo
    int steps
    int averagePressure
    double overrunRate

    String feelingLabel() {
        switch (feeling) {
            case 0: return '舒适'
            case 1: return '轻度不适'
            case 2: return '中度不适'
            case 3: return '重度不适'
            case 4: return '紧急情况'
            default: return ''
        }
    }

    String effectLabel() {
        switch (effect) {
            case 0: return '效果不明显'
            case 1: return '轻松'
            case 2: return '适量'
            case 3: return '可忍受'
            case 4: return '过度'
            default: return ''
        }
    }

    String reactionLabel() {
        switch (reaction) {
            case 0: return '无'
            case 1: return '肿胀'
            case 2: return '酸痛'
            case 3: return '弹响'
            case 4: return '麻木'
            case 5: return '淤青'
            default: return ''
        }
    }
}
