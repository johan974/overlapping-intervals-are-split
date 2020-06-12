package nl.deholtmans.periodecum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeriodeCumulative {

    // Stap 6 - Alles samenvoegen
    public static List<PeriodeValue> getPeriodCumulatives(List<PeriodeValue> periodeValues) {
        List<PeriodeValue> cumulatedPeriodValues = new ArrayList<>();
        if (periodeValues == null || periodeValues.isEmpty()) {
            return cumulatedPeriodValues;
        }
        List<PeriodeValue> splitPeriodsList = getIntersectingPeriodes( periodeValues);
        sortIntervals( splitPeriodsList);
        return cumulateIntervals( splitPeriodsList);
    }

    // Step 3 - split all periodes via all other periods
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
    // Stap 4 - sorteer de periodes
    public static void sortIntervals( List<PeriodeValue> aListToBeSorted) {
        Collections.sort(aListToBeSorted, (o1, o2) -> {
            if( ((PeriodeValue) o1).start.isBefore( ((PeriodeValue) o2).start)) {
                return -1;
            } else if( ((PeriodeValue) o1).start.isEqual( ((PeriodeValue) o2).start)) {
                return ((PeriodeValue) o1).einde.compareTo( ((PeriodeValue) o2).einde);
            } else {
                return 1;
            }
        });
    }

    // Stap 5 - bereken de cumulatieve waarden
    public static List<PeriodeValue> cumulateIntervals( List<PeriodeValue> sortedPeriodsToBeCumulated) {
        List<PeriodeValue> cumulatedValuesPerPeriod = new ArrayList<>();
        for( PeriodeValue periodeValue : sortedPeriodsToBeCumulated) {
            PeriodeValue existingPeriod = findExistingInterval( cumulatedValuesPerPeriod, periodeValue);
            if( existingPeriod == null) {
                existingPeriod = new PeriodeValue( periodeValue.start, periodeValue.einde, periodeValue.value);
                cumulatedValuesPerPeriod.add( existingPeriod);
            } else {
                existingPeriod.value += periodeValue.value;
            }
        }
        return cumulatedValuesPerPeriod;
    }

    private static PeriodeValue findExistingInterval( List<PeriodeValue> existingIntervals, PeriodeValue newPeriod) {
        if( existingIntervals.isEmpty()) {
            return null;
        }
        boolean found = false;
        for( PeriodeValue periodeValue : existingIntervals) {
            if( periodeValue.start.equals( newPeriod.start) && periodeValue.einde.equals( newPeriod.einde)) {
                return periodeValue;
            }
        }
        return null;
    }
    
    // Step 3 - split all periodes via all other periods
    public static List<PeriodeValue> getPeriodesMerged(List<PeriodeValue> periodeValues) {
        List<PeriodeValue> mergedValues = new ArrayList<>();
        if (periodeValues == null || periodeValues.isEmpty()) {
            return mergedValues;
        }
        PeriodeValue leftPeriod = periodeValues.get(0);
        PeriodeValue rightPeriod = null;
        for (int i = 0; i < periodeValues.size() - 1; i++) {
        	rightPeriod  = periodeValues.get(i + 1);
        	
        	if (leftPeriod.hasSubsequentPeriod(rightPeriod)) {
        		leftPeriod = leftPeriod.merge(rightPeriod);
        	} else {
        		mergedValues.add(leftPeriod);
        		leftPeriod = rightPeriod;
        		
        		if (i == periodeValues.size() - 2) {
            		mergedValues.add(leftPeriod);
        		}
        	}
        }
        return mergedValues;
    }
    
    public static void print() {
    	System.out.println("Hello Johan");
    	
    }
}
