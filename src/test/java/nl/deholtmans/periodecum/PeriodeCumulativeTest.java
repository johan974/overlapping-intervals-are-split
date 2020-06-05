package nl.deholtmans.periodecum;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.deholtmans.periodecum.PeriodeCumulative.getIntersectingPeriodes;
import static nl.deholtmans.periodecum.PeriodeCumulative.getPeriodCumulatives;
import static nl.deholtmans.periodecum.PeriodeCumulative.sortIntervals;
import static org.junit.Assert.*;

public class PeriodeCumulativeTest {
    // Step 3
    @Test
    public void testPeriodsHavingIntersections() {
        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 4), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 5), 33);
        PeriodeValue c = new PeriodeValue( LocalDate.now().plusDays( 3), LocalDate.now().plusDays( 7), 44);
        PeriodeValue d = new PeriodeValue( LocalDate.now().plusDays( 5), LocalDate.now().plusDays( 6), 55);
        List<PeriodeValue> periodeValueList = Arrays.asList( a, b, c, d);
        List<PeriodeValue> splitPeriods = getIntersectingPeriodes( periodeValueList);
        splitPeriods.forEach( p -> System.out.println( p));
        assertEquals( 11, splitPeriods.size());
       /*
        PeriodeValue{start=06-06-2020, einde=07-06-2020, value=22.0}
        PeriodeValue{start=07-06-2020, einde=08-06-2020, value=22.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=22.0}
        PeriodeValue{start=07-06-2020, einde=08-06-2020, value=33.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=33.0}
        PeriodeValue{start=09-06-2020, einde=10-06-2020, value=33.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=44.0}
        PeriodeValue{start=09-06-2020, einde=10-06-2020, value=44.0}
        PeriodeValue{start=10-06-2020, einde=11-06-2020, value=44.0}
        PeriodeValue{start=11-06-2020, einde=12-06-2020, value=44.0}
        PeriodeValue{start=10-06-2020, einde=11-06-2020, value=55.0}
        */
    }

    @Test
    public void testPeriodsHavingIntersectionsReversed() {
        PeriodeValue d = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 4), 22);
        PeriodeValue c = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 5), 33);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 3), LocalDate.now().plusDays( 7), 44);
        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 5), LocalDate.now().plusDays( 6), 55);
        List<PeriodeValue> periodeValueList = Arrays.asList( a, b, c, d);
        List<PeriodeValue> splitPeriods = getIntersectingPeriodes( periodeValueList);
        splitPeriods.forEach( p -> System.out.println( p));
        assertEquals( 11, splitPeriods.size());
       /*
        PeriodeValue{start=06-06-2020, einde=07-06-2020, value=22.0}
        PeriodeValue{start=07-06-2020, einde=08-06-2020, value=22.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=22.0}
        PeriodeValue{start=07-06-2020, einde=08-06-2020, value=33.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=33.0}
        PeriodeValue{start=09-06-2020, einde=10-06-2020, value=33.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=44.0}
        PeriodeValue{start=09-06-2020, einde=10-06-2020, value=44.0}
        PeriodeValue{start=10-06-2020, einde=11-06-2020, value=44.0}
        PeriodeValue{start=11-06-2020, einde=12-06-2020, value=44.0}
        PeriodeValue{start=10-06-2020, einde=11-06-2020, value=55.0}
        */
    }

    // Stap 4 - sort intervals
    @Test
    public void testSortIntervals() {
        PeriodeValue d = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(4), 22);
        PeriodeValue c = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);
        PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(3), LocalDate.now().plusDays(7), 44);
        PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6), 55);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        sortIntervals( periods);
        periods.forEach( p -> System.out.println( p));
    }

    @Test
    public void testSortIntervalsReversed() {
        PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(4), 22);
        PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);
        PeriodeValue c = new PeriodeValue(LocalDate.now().plusDays(3), LocalDate.now().plusDays(7), 44);
        PeriodeValue d = new PeriodeValue(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6), 55);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        sortIntervals( periods);
        periods.forEach( p -> System.out.println( p));
    }

    // Stap 5 - nog te testen

    // Step 6 - op het hoogste niveau
    @Test
    public void testCumulatedPeriodValues() {
        PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(4), 22);
        PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);
        PeriodeValue c = new PeriodeValue(LocalDate.now().plusDays(3), LocalDate.now().plusDays(7), 44);
        PeriodeValue d = new PeriodeValue(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6), 55);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Final result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        /*
        PeriodeValue{start=06-06-2020, einde=07-06-2020, value=22.0}
        PeriodeValue{start=07-06-2020, einde=08-06-2020, value=55.0}
        PeriodeValue{start=08-06-2020, einde=09-06-2020, value=99.0}
        PeriodeValue{start=09-06-2020, einde=10-06-2020, value=77.0}
        PeriodeValue{start=10-06-2020, einde=11-06-2020, value=99.0}
        PeriodeValue{start=11-06-2020, einde=12-06-2020, value=44.0}
         */
    }
}