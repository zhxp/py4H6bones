package zx.util;

import zx.domain.SurgeryType;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class CmnUtils {
    public static Comparator<SurgeryType> getSurgeryTypeNameComparator() {
        return surgeryTypeNameComparator;
    }

    private static final Collator cmnCollator = Collator.getInstance(Locale.CHINA);

    private static final Comparator<SurgeryType> surgeryTypeNameComparator = new Comparator<SurgeryType>() {
        @Override
        public int compare(SurgeryType a, SurgeryType b) {
            return cmnCollator.compare(a.getName(), b.getName());
        }
    };
}
