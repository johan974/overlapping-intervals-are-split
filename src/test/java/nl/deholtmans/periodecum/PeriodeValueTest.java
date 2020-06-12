package nl.deholtmans.periodecum;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.deholtmans.periodecum.PeriodeValue.leftisAfterIncluding;
import static nl.deholtmans.periodecum.PeriodeValue.leftisAfterExcluding;
import static nl.deholtmans.periodecum.PeriodeValue.leftisBeforeIncluding;
import static nl.deholtmans.periodecum.PeriodeValue.leftisBeforeExcluding;
import static org.junit.Assert.*;

public class PeriodeValueTest {
	@Test
	public void TestleftisAfterIncludingSameDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now();
		assertTrue(leftisAfterIncluding(a, b));
	}

	@Test
	public void testleftisAfterIncludingPlusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().plusDays(1);
		assertFalse(leftisAfterIncluding(a, b));
	}

	@Test
	public void testleftisAfterIncludingMinusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().minusDays(1);
		assertTrue(leftisAfterIncluding(a, b));
	}

	@Test
	public void testleftisBeforeIncludingSameDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now();
		assertTrue(leftisBeforeIncluding(a, b));
	}

	@Test
	public void testleftisBeforeIncludingPlusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().plusDays(1);
		assertTrue(leftisBeforeIncluding(a, b));
	}

	@Test
	public void testleftisBeforeIncludingMinusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().minusDays(1);
		assertFalse(leftisBeforeIncluding(a, b));
	}

	@Test
	public void testleftisBeforeExcludingSameDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now();
		assertFalse(leftisBeforeExcluding(a, b));
	}

	@Test
	public void testleftisBeforeExcludingPlusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().plusDays(1);
		assertTrue(leftisBeforeExcluding(a, b));
	}

	@Test
	public void testleftisBeforeExcludingMinusOneDay() {
		LocalDate a = LocalDate.now();
		LocalDate b = LocalDate.now().minusDays(1);
		assertFalse(leftisBeforeExcluding(a, b));
	}

	@Test
	public void testSameDay() {
		LocalDate a = LocalDate.of(2019, 1, 1);
		LocalDate b = LocalDate.of(2019, 1, 1);
		assertTrue(leftisBeforeIncluding(a, b));
		assertFalse(leftisBeforeExcluding(a, b));
		assertTrue(leftisAfterIncluding(a, b));
		assertFalse(leftisAfterExcluding(a, b));
	}

	@Test
	public void testSameDayNextDay() {
		LocalDate a = LocalDate.of(2018, 12, 31);
		LocalDate b = a.plusDays(1);
		assertTrue(leftisBeforeIncluding(a, b));
		assertTrue(leftisBeforeExcluding(a, b));
		assertFalse(leftisAfterIncluding(a, b));
		assertFalse(leftisAfterExcluding(a, b));
	}

	@Test
	public void testSameDayPrevDay() {
		LocalDate a = LocalDate.of(2019, 1, 1);
		LocalDate b = a.minusDays(1);
		assertFalse(leftisBeforeIncluding(a, b));
		assertFalse(leftisBeforeExcluding(a, b));
		assertTrue(leftisAfterIncluding(a, b));
		assertTrue(leftisAfterExcluding(a, b));
	}

	// Stap 2 - de 6 test scenario's
	@Test
	public void testAIsVoorB_should_AisNotSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(3), LocalDate.now().plusDays(5), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(a.einde));
	}

	@Test
	public void testJaarOvergangExludeSameDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 12, 31), LocalDate.of(2019, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());
	}

	@Test
	public void testJaarOvergangExludeNextDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());
	}

	@Test
	public void testJaarOvergangExludePrevDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31), 22);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());

		System.out.println("\nReverse");
		List<PeriodeValue> bSplitInPeriodes = b.split(a);
		bSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(2, bSplitInPeriodes.size());

	}

	@Test
	public void testAstartAsBAndLonger_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(2), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(2, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(b.einde));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 0.01);

		assertTrue(aSplitInPeriodes.get(1).start.isEqual(b.einde.plusDays(1)));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(a.einde));
	}

	@Test
	public void testTwoYears_OneYear() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(2, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 22);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(b.einde));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 22);
		assertFalse(aSplitInPeriodes.get(1).start.isEqual(b.einde));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(a.einde));
	}

	@Test
	public void testAenBgelijk() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());

//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));

	}

	@Test
	public void testBStartAfterAAndEndsInside() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 2, 1), LocalDate.of(2016, 10, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(3, aSplitInPeriodes.size());

//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));

	}

	@Test
	public void testBStartAfterAAndEndsOutside() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2016, 2, 1), LocalDate.of(2017, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(2, aSplitInPeriodes.size());

//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));

	}

	@Test
	public void testBStartBeforeAAndEndsOutside() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2015, 2, 1), LocalDate.of(2017, 10, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(1, aSplitInPeriodes.size());

//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));

	}

	@Test
	public void testBStartBeforeAAndEndsInside() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2015, 1, 1), LocalDate.of(2016, 12, 31), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));
		assertEquals(2, aSplitInPeriodes.size());

//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.einde));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.einde));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( a.einde));

	}

	@Test
	public void testAstartAsBAndShorter_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(2), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		assertEquals(1, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(a.einde));
	}

	@Test
	public void testAendsAsBAndLonger_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 33);

		System.out.println(String.format("a waarde: %s\nb waarde: %s", a.toString(), b.toString()));
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));

		assertEquals(2, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(b.start.minusDays(1)));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(1).start.isEqual(b.start));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(a.einde));
	}

	@Test
	public void testAendsAsBAndShorter_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		assertEquals(1, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(a.einde));
	}

	@Test
	public void testAisPartlyBeforeB_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(3), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);

		System.out.println(String.format("a waarde: %s\nb waarde: %s", a.toString(), b.toString()));
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));

		assertEquals(2, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.plusDays(1).isEqual(b.start));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(1).start.isEqual(b.start));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(a.einde));
	}

	@Test
	public void testAstartAtB_should_AisNotSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now(), LocalDate.now().plusDays(2), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		assertEquals(1, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(a.einde));
	}

	@Test
	public void testBisPartlyBeforeA_should_AisSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 33);

		System.out.println(String.format("a waarde: %s\nb waarde: %s", a.toString(), b.toString()));
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));

		assertEquals(2, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(b.einde));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(1).start.minusDays(1).isEqual(b.einde));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(a.einde));
	}

	@Test
	public void testAvolledigeInB_should_AisNotSplit() {
		PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 33);
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		assertEquals(1, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.isEqual(a.einde));
	}

	@Test
	public void testBvolledigeInA_should_AisSplitInThree() {
		PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 33);

		System.out.println(String.format("a waarde: %s\nb waarde: %s", a.toString(), b.toString()));
		List<PeriodeValue> aSplitInPeriodes = a.split(b);
		aSplitInPeriodes.forEach(p -> System.out.println(p));

		assertEquals(3, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.plusDays(1).isEqual(b.start));

		assertEquals(aSplitInPeriodes.get(1).value, a.value, 0.01);
		assertTrue(aSplitInPeriodes.get(1).start.isEqual(b.start));
		assertTrue(aSplitInPeriodes.get(1).einde.isEqual(b.einde));
	}

	// RANDGEVALLEN BETER CHECKEN !!! en programmeren

	// Stap 3 - Splits interval tegen een reeks van andere intervallen
	@Test
	public void testAoverlapsBoverlapsC_should_AisSplitInThree() {
		PeriodeValue a = new PeriodeValue(LocalDate.now().plusDays(1), LocalDate.now().plusDays(4), 22);
		PeriodeValue b = new PeriodeValue(LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), 33);
		PeriodeValue c = new PeriodeValue(LocalDate.now().plusDays(3), LocalDate.now().plusDays(6), 44);
		System.out.println(
				String.format("a waarde: %s\nb waarde: %s\nc waarde: %s", a.toString(), b.toString(), c.toString()));

		List<PeriodeValue> otherList = Arrays.asList(b, c);
		List<PeriodeValue> aSplitInPeriodes = a.split(otherList);
		aSplitInPeriodes.forEach(p -> System.out.println(p));

		assertEquals(3, aSplitInPeriodes.size());
		assertEquals(aSplitInPeriodes.get(0).value, a.value, 22);
		assertTrue(aSplitInPeriodes.get(0).start.isEqual(a.start));
		assertTrue(aSplitInPeriodes.get(0).einde.plusDays(1).isEqual(b.start));
		assertEquals(aSplitInPeriodes.get(1).value, a.value, 33);
		assertTrue(aSplitInPeriodes.get(1).start.isEqual(b.start));
		assertTrue(aSplitInPeriodes.get(1).einde.plusDays(1).isEqual(c.start));
		assertEquals(aSplitInPeriodes.get(2).value, a.value, 22);
		assertTrue(aSplitInPeriodes.get(2).start.isEqual(c.start));
		assertTrue(aSplitInPeriodes.get(2).einde.isEqual(a.einde));
	}

//    @Test
//    public void testAoverlapsBoverlapsC_should_AisSplitInThree() {
//        PeriodeValue a = new PeriodeValue( LocalDate.now().plusDays( 1), LocalDate.now().plusDays( 4), 22);
//        PeriodeValue b = new PeriodeValue( LocalDate.now().plusDays( 2), LocalDate.now().plusDays( 4), 33);
//        List<PeriodeValue> otherList = Arrays.asList( b);
//        List<PeriodeValue> aSplitInPeriodes = a.split( otherList);
//        assertEquals( 3, aSplitInPeriodes.size());
//        assertEquals( aSplitInPeriodes.get( 0).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 0).start.isEqual( a.start));
//        assertTrue( aSplitInPeriodes.get( 0).einde.isEqual( b.start));
//        assertEquals( aSplitInPeriodes.get( 1).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 1).start.isEqual( b.start));
//        assertTrue( aSplitInPeriodes.get( 1).einde.isEqual( c.start));
//        assertEquals( aSplitInPeriodes.get( 2).value, a.value, 0.01);
//        assertTrue( aSplitInPeriodes.get( 2).start.isEqual( c.start));
//        assertTrue( aSplitInPeriodes.get( 2).einde.isEqual( a.einde));
//    }

	@Test
	public void testIsSubsequentPeriodNextDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31), 33);
		assertTrue(a.hasSubsequentPeriod(b));
	}

	@Test
	public void testIsSubsequentPeriodSameDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2018, 12, 31), 33);
		assertFalse(a.hasSubsequentPeriod(b));
	}

	@Test
	public void testIsSubsequentPeriodPrevDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 30), LocalDate.of(2018, 12, 31), 33);
		assertFalse(a.hasSubsequentPeriod(b));
	}

	@Test
	public void testMergePeriodNextDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31), 33);

		System.out.println("Before: " + a);
		PeriodeValue c = a.merge(b);
		System.out.println("After: " + c);

		assertTrue(c.einde.isEqual(b.einde));

	}

	@Test
	public void testMergePeriodSameDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 31), LocalDate.of(2018, 12, 31), 33);

		System.out.println("Before: " + a);
		PeriodeValue c = a.merge(b);
		System.out.println("After: " + c);

		assertFalse(c.einde.isEqual(b.einde));
	}

	@Test
	public void testMergePeriodPrevDay() {
		PeriodeValue a = new PeriodeValue(LocalDate.of(2016, 1, 1), LocalDate.of(2017, 12, 31), 33);
		PeriodeValue b = new PeriodeValue(LocalDate.of(2017, 12, 30), LocalDate.of(2018, 12, 31), 33);

		System.out.println("Before: " + a);
		PeriodeValue c = a.merge(b);
		System.out.println("After: " + c);

		assertFalse(c.einde.isEqual(b.einde));
	}

}