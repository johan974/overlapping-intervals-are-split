package nl.deholtmans.periodecum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DTO
public class PeriodeValue {
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

    // Stap 2 - splits 1 periode interval o.b.v. een 'andere' (other) + unit test
    public List<PeriodeValue> split( PeriodeValue other) {
        List<PeriodeValue> periods = new ArrayList<>();
        if( leftisAfterIncluding( other.start, start) && leftisBeforeIncluding( other.start, einde)) {
            periods.add( new PeriodeValue( start, other.start, value));
            if( leftisAfterIncluding( other.einde, start) && leftisBeforeExcluding( other.einde, einde)) {
                periods.add(new PeriodeValue( other.start, other.einde, value));
                periods.add(new PeriodeValue( other.einde, einde, value));
            } else {
                periods.add(new PeriodeValue( other.start, einde, value));
            }
        } else if( leftisAfterIncluding( other.einde, start) && leftisBeforeExcluding( other.einde, einde)) {
            periods.add( new PeriodeValue( start, other.einde, value));
            periods.add( new PeriodeValue( other.einde, einde, value));
        } else {
            periods.add( this);
        }
        return periods;
    }

    // Stap 1 - beter leesbare datumvergelijkingen + unit test
    public static boolean leftisAfterIncluding( LocalDate a, LocalDate b) {
        return ! b.isAfter( a);
    }
    public static boolean leftisBeforeIncluding( LocalDate a, LocalDate b) {
        return ! b.isBefore( a);
    }
    public static boolean leftisBeforeExcluding( LocalDate a, LocalDate b) {
        return a.isBefore( b);
    }
}
