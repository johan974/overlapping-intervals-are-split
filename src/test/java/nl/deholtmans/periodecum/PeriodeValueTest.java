package nl.deholtmans.periodecum;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static nl.deholtmans.periodecum.PeriodeValue.leftisAfterIncluding;
import static org.junit.Assert.*;

public class PeriodeValueTest {
    @Test
    public void testAisBeforeIncludingB() {
        LocalDate a = LocalDate.now();
        LocalDate b = LocalDate.now();
        assertTrue( leftisAfterIncluding( a, b));
    }

    @Test
    public void testAisBeforeB() {
        LocalDate a = LocalDate.now();
        LocalDate b = LocalDate.now().plusDays( 1);
        assertFalse( leftisAfterIncluding( a, b));
    }

    @Test
    public void testAisNotBeforeB() {
        LocalDate a = LocalDate.now();
        LocalDate b = LocalDate.now().minusDays( 1);
        assertTrue( leftisAfterIncluding( a, b));
    }

    // Stap 2 - de 6 test scenario's
    @Test
    public void testAIsVoorB_should_AisNotSplit() {
        PeriodeValue a = new PeriodeValue( LocalDate.now(), LocalDate.now().plusDays( 3), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 4), LocalDate.now().plusDays( 5), 33);
        List<PeriodeValue> aSplitInPeriodes = a.split( b);
        assertEquals( 1, aSplitInPeriodes.size());
        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( a.einde));
    }

    // TODO - wanneer A.einde == B.start ...

    @Test
    public void testAisPartlyBeforeB_should_AisSplit() {
        PeriodeValue a = new PeriodeValue( LocalDate.now(), LocalDate.now().plusDays( 3), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 5), 33);
        List<PeriodeValue> aSplitInPeriodes = a.split( b);
        assertEquals( 2, aSplitInPeriodes.size());
        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.start));
        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.start));
        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));
    }

    @Test
    public void testBisPartlyBeforeA_should_AisSplit() {
        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 5), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 3), 33);
        List<PeriodeValue> aSplitInPeriodes = a.split( b);
        assertEquals( 2, aSplitInPeriodes.size());
        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));
    }

    @Test
    public void testAvolledigeInB_should_AisNotSplit() {
        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 4), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 5), 33);
        List<PeriodeValue> aSplitInPeriodes = a.split( b);
        assertEquals( 1, aSplitInPeriodes.size());
        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( a.einde));
    }

    @Test
    public void testBvolledigeInA_should_AisSplitInThree() {
        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 5), 22);
        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 4), 33);
        List<PeriodeValue> aSplitInPeriodes = a.split( b);
        assertEquals( 3, aSplitInPeriodes.size());
        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.start));
        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.start));
        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( b.einde));
        assertEquals( aSplitInPeriodes.get( 2).value, a.value, 0.01);
        assertTrue( aSplitInPeriodes.get( 2).start.isEqual( b.einde));
        assertTrue( aSplitInPeriodes.get( 2).einde.isEqual( a.einde));
    }

    // RANDGEVALLEN BETER CHECKEN !!! en programmeren
}