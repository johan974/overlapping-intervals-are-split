package nl.deholtmans.periodecum;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.deholtmans.periodecum.PeriodeCumulative.getIntersectingPeriodes;
import static nl.deholtmans.periodecum.PeriodeCumulative.getPeriodCumulatives;
import static nl.deholtmans.periodecum.PeriodeCumulative.getPeriodesMerged;
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

    // Step 6 - op het hoogste niveau
    @Test
    public void testBInsideA() {
        PeriodeValue a = new PeriodeValue(LocalDate.of(2014, 1, 1), LocalDate.of(2015, 6, 30), 0.31);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 0.27);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2016, 2, 1), LocalDate.of(2018, 11, 30), 0.04);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 0.47);
        List<PeriodeValue> periods = new ArrayList<>();
        //	periods.add( a);
        periods.add( b);
        periods.add( c);
        //	periods.add( d);
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

    // Groudgrijp en haantjes
    // Step 7 - op het hoogste niveau
    @Test
    public void testGoudgrijp_Gives_4cumulatedPeriods() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2014, 1, 1), LocalDate.of(2015, 6, 30), 0.31);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 0.27);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 0.04);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 0.47);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Goudgrijp result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));

        assertEquals( 4, cumulatedPeriods.size());
        assertEquals( cumulatedPeriods.get( 0).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 0).start.isEqual( a.start));
        assertTrue( cumulatedPeriods.get( 0).einde.isEqual( a.einde));
        assertEquals( cumulatedPeriods.get( 1).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 1).start.isEqual( b.start));
        assertTrue( cumulatedPeriods.get( 1).einde.isEqual( c.einde));
        assertEquals( cumulatedPeriods.get( 2).value, a.value, 0.27);
        assertTrue( cumulatedPeriods.get( 2).start.isEqual( c.einde));
        assertTrue( cumulatedPeriods.get( 2).einde.isEqual( b.einde));
        assertEquals( cumulatedPeriods.get( 3).value, a.value, 0.47);
        assertTrue( cumulatedPeriods.get( 3).start.isEqual( d.start));
        assertTrue( cumulatedPeriods.get( 3).einde.isEqual( d.einde));

        /* Periode:
				PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
				PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.27}
				PeriodeValue{start=01-01-2016, einde=31-12-2016, value=0.04}
				PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}

				Goudgrijp result: 
				PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
				PeriodeValue{start=01-01-2016, einde=31-12-2016, value=0.31}
				PeriodeValue{start=01-01-2017, einde=31-12-2018, value=0.27}
				PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}
         */
    }
    
    // Groudgrijp en haantjes
    // Step 7 - op het hoogste niveau
    @Test
    public void testGoudgrijp_Hermien() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2014, 1, 1), LocalDate.of(2015, 6, 30), 0.31);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 0.27);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2019, 12, 31), 0.04);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2018, 12, 31), LocalDate.of(2019, 12, 31), 0.47);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Goudgrijp result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));

        assertEquals( 3, cumulatedPeriods.size());
        assertEquals( cumulatedPeriods.get( 0).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 0).start.isEqual( a.start));
        assertTrue( cumulatedPeriods.get( 0).einde.isEqual( a.einde));
        assertEquals( cumulatedPeriods.get( 1).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 1).start.isEqual( b.start));
        assertTrue( cumulatedPeriods.get( 1).einde.isEqual( c.einde));
        assertEquals( cumulatedPeriods.get( 2).value, a.value, 0.27);
        assertTrue( cumulatedPeriods.get( 2).start.isEqual( c.einde));
        assertTrue( cumulatedPeriods.get( 2).einde.isEqual( b.einde));

        /* Periode:
			PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
			PeriodeValue{start=01-01-2016, einde=31-12-2019, value=0.04}
			PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.27}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}

			result: 
			PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
			PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.31}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.51}
         */
    }
    
    // Groudgrijp en haantjes
    // Step 7 - op het hoogste niveau
    @Test
    public void TestThreeDays() {

        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 4), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 5), 33);
        PeriodeValue c = new PeriodeValue( LocalDate.now().plusDays( 3), LocalDate.now().plusDays( 6), 44);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
//        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
//        assertEquals( 3, cumulatedPeriods.size());

        /* Periode:
            PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
            PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.27}
            PeriodeValue{start=01-01-2016, einde=31-12-2016, value=0.04}
            PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}
         */

        /* Waardes: cumulatief
            PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
            PeriodeValue{start=01-01-2016, einde=31-12-2016, value=0.31}
            PeriodeValue{start=31-12-2016, einde=31-12-2018, value=0.27}
            PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}
         */
    }
    
    // Step 8 - merge cumulated periods
    @Test
    public void testMergeNoSubsequentTwoPeriods() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 0.25);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 2, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(0).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.25);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(1).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(1).einde));

      /*
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.25}
         */
    }
    
    @Test
    public void testMergeNoSubsequentThreePeriods() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 0.25);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 12, 31), 0.35);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 3, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(0).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.25);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(1).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(2).einde));

      /*
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.25}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
     */
    }
    
    @Test
    public void testMergeNoSubsequentFourPeriods() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 0.25);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 12, 31), 0.35);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2021, 1, 2), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 4, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(0).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.25);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(1).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(2).einde));
      assertEquals( mergedPeriods.get( 3).value, d.value, 0.15);
      assertTrue( mergedPeriods.get( 3).start.isEqual( cumulatedPeriods.get(3).start));
      assertTrue( mergedPeriods.get( 3).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.25}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsOneAndTwoEqualAndValueUnequal() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 12, 31), 0.25);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 12, 31), 0.35);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2021, 1, 2), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 4, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(0).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.25);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(1).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(2).einde));
      assertEquals( mergedPeriods.get( 3).value, d.value, 0.15);
      assertTrue( mergedPeriods.get( 3).start.isEqual( cumulatedPeriods.get(3).start));
      assertTrue( mergedPeriods.get( 3).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.25}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsOneAndTwoEqualAndValueEqual() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 12, 31), 0.35);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2021, 1, 2), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 3, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.50);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(2).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(3).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
			
			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2019, value=0.5}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsOneAndTwoEqualAndValueEqualAndThreeAndFourDatesEqualButValueUnequal() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 2), LocalDate.of(2020, 12, 31), 0.35);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2020, 12, 31), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 3, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 1).value, b.value, 0.50);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(2).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(3).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
			
			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2019, value=0.5}
			PeriodeValue{start=02-01-2020, einde=31-12-2020, value=0.35}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsOneAndTwoUnequalAndValueEqualAndTwoAndThreeDatesEqualAndValueEqual() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 2), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 12, 31), 0.50);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2021, 1, 2), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 3, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(0).einde));
      assertEquals( mergedPeriods.get( 1).value, c.value, 0.50);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(1).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(2).einde));
      assertEquals( mergedPeriods.get( 2).value, c.value, 0.35);
      assertTrue( mergedPeriods.get( 2).start.isEqual( cumulatedPeriods.get(3).start));
      assertTrue( mergedPeriods.get( 2).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=02-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.5}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
			
			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=02-01-2018, einde=31-12-2020, value=0.5}
			PeriodeValue{start=02-01-2021, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsOneAndTwoEqualAndValueEqualAndThreeAndFourDatesEqualAndValueEqual() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 12, 31), 0.15);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2020, 12, 31), LocalDate.of(2021, 12, 31), 0.15);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 2, mergedPeriods.size());
      assertEquals( mergedPeriods.get( 0).value, a.value, 0.50);
      assertTrue( mergedPeriods.get( 0).start.isEqual( cumulatedPeriods.get(0).start));
      assertTrue( mergedPeriods.get( 0).einde.isEqual( cumulatedPeriods.get(1).einde));
      assertEquals( mergedPeriods.get( 1).value, c.value, 0.15);
      assertTrue( mergedPeriods.get( 1).start.isEqual( cumulatedPeriods.get(2).start));
      assertTrue( mergedPeriods.get( 1).einde.isEqual( cumulatedPeriods.get(3).einde));

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.15}
			PeriodeValue{start=01-01-2021, einde=31-12-2021, value=0.15}

			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2021, value=0.15}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsAllSubsequentNextDay() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31), 0.50);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31), 0.50);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
      assertEquals( 4, mergedPeriods.size());

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.5}
			PeriodeValue{start=01-01-2021, einde=31-12-2021, value=0.5}
			
			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.5}
			PeriodeValue{start=01-01-2021, einde=31-12-2021, value=0.5}
     */
    }
    
    @Test
    public void testMergeSubsequentPeriodsAllSubsequentSameDay() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2017, 12, 31), 0.50);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2019, 12, 31), 0.50);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 12, 31), 0.50);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2020, 12, 31), LocalDate.of(2021, 12, 31), 0.50);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "Cumulated result: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));
        
        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "Merged result: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
        assertEquals( 1, mergedPeriods.size());
        assertEquals( mergedPeriods.get( 0).einde, cumulatedPeriods.get(3).einde);

      /*
			Cumulated result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.5}
			PeriodeValue{start=01-01-2021, einde=31-12-2021, value=0.5}
			
			Merged result: 
			PeriodeValue{start=01-01-2015, einde=31-12-2017, value=0.5}
			PeriodeValue{start=01-01-2018, einde=31-12-2019, value=0.5}
			PeriodeValue{start=01-01-2020, einde=31-12-2020, value=0.5}
			PeriodeValue{start=01-01-2021, einde=31-12-2021, value=0.5}
     */
    }
    
    @Test
    public void testGoudgrijp_HermienMerge() {

        PeriodeValue a = new PeriodeValue(LocalDate.of(2014, 1, 1), LocalDate.of(2015, 6, 30), 0.31);
        PeriodeValue c = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 0.27);
        PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2019, 12, 31), 0.04);
        PeriodeValue d = new PeriodeValue(LocalDate.of(2018, 12, 31), LocalDate.of(2019, 12, 31), 0.27);
        List<PeriodeValue> periods = new ArrayList<>();
        periods.add( a);
        periods.add( b);
        periods.add( c);
        periods.add( d);
        System.out.println( "Original periods: ");
        periods.forEach( p -> System.out.println( p));
        List<PeriodeValue> cumulatedPeriods = getPeriodCumulatives( periods);
        System.out.println( "\nCumulated periods: ");
        cumulatedPeriods.forEach( p -> System.out.println( p));

        List<PeriodeValue> mergedPeriods = getPeriodesMerged(cumulatedPeriods);
        System.out.println( "\nMerged: ");
        mergedPeriods.forEach( p -> System.out.println( p));
        
        assertEquals( 3, cumulatedPeriods.size());
        assertEquals( cumulatedPeriods.get( 0).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 0).start.isEqual( a.start));
        assertTrue( cumulatedPeriods.get( 0).einde.isEqual( a.einde));
        assertEquals( cumulatedPeriods.get( 1).value, a.value, 0.31);
        assertTrue( cumulatedPeriods.get( 1).start.isEqual( b.start));
        assertTrue( cumulatedPeriods.get( 1).einde.isEqual( c.einde));
        assertEquals( cumulatedPeriods.get( 2).value, a.value, 0.27);
        assertTrue( cumulatedPeriods.get( 2).start.isEqual( c.einde));
        assertTrue( cumulatedPeriods.get( 2).einde.isEqual( b.einde));

        /* Periode:
			PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
			PeriodeValue{start=01-01-2016, einde=31-12-2019, value=0.04}
			PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.27}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.47}

			result: 
			PeriodeValue{start=01-01-2014, einde=30-06-2015, value=0.31}
			PeriodeValue{start=01-01-2016, einde=31-12-2018, value=0.31}
			PeriodeValue{start=01-01-2019, einde=31-12-2019, value=0.51}
         */
    }
    

}