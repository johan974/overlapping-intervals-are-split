package nl.deholtmans.periodecum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// DTO
public class PeriodeValue {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static DateTimeFormatter staticFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public LocalDate start;
	public LocalDate einde;
	public double value;

	public PeriodeValue() {
	}

	public PeriodeValue(LocalDate start, LocalDate einde, double value) {
		this.start = start;
		this.einde = einde;
		this.value = value;
	}

	public boolean hasOverlap(PeriodeValue other) {
		if (start.isAfter(other.einde) || einde.isBefore(other.start)) {
			return false;
		}
		return true;
	}

	// Stap 2 - splits in periode o.b.v. een serie andere periodes
	public List<PeriodeValue> split(List<PeriodeValue> otherPeriods) {
		List<PeriodeValue> originalList = new ArrayList<>();
		originalList.add(this);
		if (otherPeriods == null || otherPeriods.isEmpty()) {
			return originalList;
		}
		for (PeriodeValue otherPeriodeValue : otherPeriods) {
			List<PeriodeValue> splitList = new ArrayList<>();
			for (PeriodeValue originalPeriod : originalList) {
				if (originalPeriod.hasOverlap(otherPeriodeValue)) {
					splitList.addAll(originalPeriod.split(otherPeriodeValue));
				} else {
					splitList.add(originalPeriod);
				}
			}
			originalList = splitList;
		}
		return originalList;
	}

	// Step 1 - splits 1 periode interval o.b.v. een 'andere' (other) + unit test
	public List<PeriodeValue> split(PeriodeValue other) {
		List<PeriodeValue> periods = new ArrayList<>();
		if (other.start.isAfter(start) && leftisBeforeExcluding(other.start, einde)) {
			periods.add(new PeriodeValue(start, other.start.minusDays(1), value));
			if (other.einde.isAfter(start) && leftisBeforeExcluding(other.einde, einde)) {
				periods.add(new PeriodeValue(other.start, other.einde, value));
				periods.add(new PeriodeValue(other.einde.plusDays(1), einde, value));
			} else {
				periods.add(new PeriodeValue(other.start, einde, value));
			}
		} else if (other.einde.isAfter(start) && leftisBeforeExcluding(other.einde, einde)) {
			periods.add(new PeriodeValue(start, other.einde, value));
			// periods.add( new PeriodeValue( other.einde, einde, value));
			periods.add(new PeriodeValue(other.einde.plusDays(1), einde, value));
		} else {
			periods.add(this);
		}
		return periods;
	}

	// Merge two periodic values when they are subsequent
	public PeriodeValue merge(PeriodeValue other) {
		if (this.hasSubsequentPeriod(other)) {
			PeriodeValue mergedPeriodeValue = new PeriodeValue(this.start, other.einde, this.value);
			return mergedPeriodeValue;
		}
		return this;
	}

	// ? beter leesbare datumvergelijkingen + unit test
	public static boolean leftisAfterIncluding(LocalDate a, LocalDate b) {
//    	System.out.println(String.format("leftisAfterIncluding: date a: %s, date b: %s compare: %s", a.format(staticFormatter), b.format(staticFormatter), ! b.isAfter( a)));
		return !b.isAfter(a);
	}

	public static boolean leftisAfterExcluding(LocalDate a, LocalDate b) {
//    	System.out.println(String.format("leftisAfterExcluding: date a: %s, date b: %s compare: %s", a.format(staticFormatter), b.format(staticFormatter), a.isAfter( b)));
		return a.isAfter(b);
	}

	public static boolean leftisBeforeIncluding(LocalDate a, LocalDate b) {
//    	System.out.println(String.format("leftisBeforeIncluding: date a: %s, date b: %s compare: %s", a.format(staticFormatter), b.format(staticFormatter), ! b.isBefore( a)));
		return !b.isBefore(a);
	}

	public static boolean leftisBeforeExcluding(LocalDate a, LocalDate b) {
//    	System.out.println(String.format("leftisBeforeExcluding: date a: %s, date b: %s compare: %s", a.format(staticFormatter), b.format(staticFormatter), a.isBefore( b)));
		return a.isBefore(b);
	}

	public boolean hasSubsequentPeriod(PeriodeValue other) {
//		System.out.println(String.format("Subsequent dates: %s %s %f %f; days are equal %s; all is equal: %s", this.einde,
//				other.start, this.value, other.value, (this.einde.plusDays(1).isEqual(other.start)),
//				((this.einde.plusDays(1).isEqual(other.start)) && (Math.abs(this.value - other.value) < 0.001))));
		if ((this.einde.plusDays(1).isEqual(other.start)) && (Math.abs(this.value - other.value) < 0.001)) {
			return true;
		}

		return false;
	}

	private String formatDate(LocalDate date) {
		return date.format(formatter);
	}

	@Override
	public String toString() {
		return "PeriodeValue{" + "start=" + formatDate(start) + ", einde=" + formatDate(einde) + ", value=" + value
				+ '}';
	}
}
