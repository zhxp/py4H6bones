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
    @ElementCollection
    List<Integer> reaction
    String memo
    int steps
    int averagePressure
    double overrunRate

    String feelingLabel() {
        switch (feeling) {
//            case 0: return '舒适'
            case 1: return '轻松'
            case 2: return '适量'
            case 3: return '过度'
//            case 4: return '紧急情况'
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

    String[] reactionLabels() {
        def result = []
        reaction.each {
            switch (it) {
//                case 0: break;
                case 1: result << '肿胀'; break;
                case 2: result << '酸痛'; break;
                case 3: result << '弹响'; break;
                case 4: result << '麻木'; break;
                case 5: result << '淤青'; break;
            }
        }
        result
    }

    static String reactionLabelString(def val) {
        switch (val) {
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
