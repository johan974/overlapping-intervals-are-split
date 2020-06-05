# Cumulate values based on intersecting intervals

An example: 

<pre>
     T1     T2  T3  T4
A 33 |------+---|
B 44        |---+--|

Result: 

T1-T2 -> 33
T2-T3 -> 77
T3-T4 -> 44
</pre>

Example: 

<pre>
Periods: 

Periode: start=06-06-2020, einde=09-06-2020, value=22.0
Periode: start=07-06-2020, einde=10-06-2020, value=33.0
Periode: start=08-06-2020, einde=12-06-2020, value=44.0
Periode: start=10-06-2020, einde=11-06-2020, value=55.0

Final result: 
Periode: start=06-06-2020, einde=07-06-2020, value=22.0
Periode: start=07-06-2020, einde=08-06-2020, value=55.0
Periode: start=08-06-2020, einde=09-06-2020, value=99.0
Periode: start=09-06-2020, einde=10-06-2020, value=77.0
Periode: start=10-06-2020, einde=11-06-2020, value=99.0
Periode: start=11-06-2020, einde=12-06-2020, value=44.0}
</pre>

# Step 1

What are the interval split scenarios? In all cases, we have a period A. How do we split that interval based on a second interval? 
The intervals are marked with '|' or '+'. In the source code this is PeriodeValue.split( PeriodeValue other)

Base class is PeriodeValue: startDate, endDate, value

<pre>
A  |------|
B         |----|
=> No overlap

A  |-----+-|
B        |----|
=> Overlap: 2 parts 

A  |---+---|
B      |---|
=> Overlap: 2 parts 

Etc. There are at least 8 cases. 

</pre>

# Step 2

One PeriodeValue is split using a list of other PeriodValues. See source code PeriodeValue.split( List<PeriodeValue> others)

# Step 3

All PeriodValues are compared to all others. See PeriodeCumulative.getIntersectingPeriodes(List<PeriodeValue> periodeValues)

# Step 4 

Sort all Split PeriodValue, see PeriodeCumulative.sortIntervals( List<PeriodeValue> aListToBeSorted)

# Step 5

Cumulate all sorted periods, see PeriodeCumulative.cumulateIntervals( List<PeriodeValue> sortedPeriodsToBeCumulated)

# Step 6

Final result: step 4 + step 5 + step 6, see PeriodeCumulative.getPeriodCumulatives(List<PeriodeValue> periodeValues)