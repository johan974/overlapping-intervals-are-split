package nl.deholtmans.periodecum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// DTO
public class PeriodeValue {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public LocalDate    start;
    public LocalDate    einde;
    public double       value;

    public PeriodeValue() {}

    public PeriodeValue( LocalDate start, LocalDate einde, double value) {
        this.start = start;
        this.einde = einde;
        this.value = value;
    }

    public boolean hasOverlap( PeriodeValue other) {
        if( start.isAfter( other.einde) || einde.isBefore( other.start)) {
            return false;
        }
        return true;
    }

    // Stap 2 - splits in periode o.b.v. een serie andere periodes
    public List<PeriodeValue> split( List<PeriodeValue> otherPeriods) {
        List<PeriodeValue> originalList = new ArrayList<>();
        originalList.add( this);
        if( otherPeriods == null || otherPeriods.isEmpty()) {
            return originalList;
        }
        for( PeriodeValue otherPeriodeValue : otherPeriods) {
            List<PeriodeValue> splitList = new ArrayList<>();
            for( PeriodeValue originalPeriod : originalList) {
                if( originalPeriod.hasOverlap( otherPeriodeValue)) {
                    splitList.addAll( originalPeriod.split( otherPeriodeValue));
                } else {
                    splitList.add( originalPeriod);
                }
            }
            originalList = splitList;
        }
        return originalList;
    }

    // Step 1 - splits 1 periode interval o.b.v. een 'andere' (other) + unit test
    public List<PeriodeValue> split( PeriodeValue other) {
        List<PeriodeValue> periods = new ArrayList<>();
        if( other.start.isAfter( start) && leftisBeforeExcluding( other.start, einde)) {
            periods.add( new PeriodeValue( start, other.start, value));
            if( other.einde.isAfter( start) && leftisBeforeExcluding( other.einde, einde)) {
                periods.add(new PeriodeValue( other.start, other.einde, value));
                periods.add(new PeriodeValue( other.einde, einde, value));
            } else {
                periods.add(new PeriodeValue( other.start, einde, value));
            }
        } else if( other.einde.isAfter( start) && leftisBeforeExcluding( other.einde, einde)) {
            periods.add( new PeriodeValue( start, other.einde, value));
            periods.add( new PeriodeValue( other.einde, einde, value));
        } else {
            periods.add( this);
        }
        return periods;
    }

    // ? beter leesbare datumvergelijkingen + unit test
    public static boolean leftisAfterIncluding( LocalDate a, LocalDate b) {
        return ! b.isAfter( a);
    }
    public static boolean leftisBeforeIncluding( LocalDate a, LocalDate b) {
        return ! b.isBefore( a);
    }
    public static boolean leftisBeforeExcluding( LocalDate a, LocalDate b) {
        return a.isBefore( b);
    }

    private String formatDate( LocalDate date) {
        return date.format( formatter);
    }

    @Override
    public String toString() {
        return "PeriodeValue{" +
                "start=" + formatDate(start) +
                ", einde=" + formatDate( einde) +
                ", value=" + value +
                '}';
    }
}
