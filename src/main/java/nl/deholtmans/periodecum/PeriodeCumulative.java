package nl.deholtmans.periodecum;

import java.util.ArrayList;
import java.util.List;

public class PeriodeCumulative {
    // Stap 4 - splits alle periodes via de andere periode
    public static List<PeriodeValue> getIntersectingPeriodes(List<PeriodeValue> periodeValues) {
        List<PeriodeValue> cumulatedValues = new ArrayList<>();
        if (periodeValues == null || periodeValues.isEmpty()) {
            return cumulatedValues;
        }
        for (int i = 0; i < periodeValues.size(); i++) {
            List<PeriodeValue> otherPeriods = new ArrayList<>();
            for (int j = 0; j < periodeValues.size(); j++) {
                if( i != j) {
                    otherPeriods.add(periodeValues.get(j));
                }
            }
            cumulatedValues.addAll(periodeValues.get(i).split(otherPeriods));
        }
        return cumulatedValues;
    }
    // Stap 5 - sorteer de periodes

    // Stap 6 - bereken de cumulatieve waarden
}
