import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testTimeSeriesCopy() {
        TimeSeries original = new TimeSeries();
        original.put(1400, 10.0);
        original.put(1500, 20.0);
        original.put(1600, 30.0);
        original.put(1700, 40.0);
        original.put(1800, 50.0);
        original.put(1900, 60.0);
        
        TimeSeries copied = new TimeSeries(original, 1500, 1800);
        assertThat(copied.size()).isEqualTo(4);
        assertThat(copied.get(1400)).isNull();
        assertThat(copied.get(1500)).isEqualTo(20);
        assertThat(copied.get(1600)).isEqualTo(30);
        assertThat(copied.get(1700)).isEqualTo(40);
        assertThat(copied.get(1800)).isEqualTo(50);
        assertThat(copied.get(1900)).isNull();
        assertThat(copied.get(2000)).isNull();
    }

    @Test
    public void testTimeSeriesCopyWithNullValues() {
        TimeSeries original = new TimeSeries();
        original.put(1400, null);
        original.put(1500, 20.0);
        original.put(1600, null);

        TimeSeries copied = new TimeSeries(original, 1400, 1600);

        assertThat(copied.size()).isEqualTo(3);
        assertThat(copied.get(1400)).isNull();
        assertThat(copied.get(1500)).isEqualTo(20.0);
        assertThat(copied.get(1600)).isNull();
    }

    @Test   
    public void testTimeSeriesCopyEmptyRange() {
        TimeSeries original = new TimeSeries();
        original.put(1400, 10.0);
        original.put(1500, 20.0);
        original.put(1600, 30.0);

        TimeSeries copied = new TimeSeries(original, 1700, 1800);

        assertThat(copied.size()).isEqualTo(0);
    }

    @Test
    public void testDividedByBasic() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1400, 10.0);
        ts1.put(1500, 20.0);
        ts1.put(1600, 30.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1400, 2.0);
        ts2.put(1500, 4.0);
        ts2.put(1600, 6.0);

        TimeSeries result = ts1.dividedBy(ts2);

        assertThat(result.get(1400)).isWithin(1E-10).of(5.0);
        assertThat(result.get(1500)).isWithin(1E-10).of(5.0);
        assertThat(result.get(1600)).isWithin(1E-10).of(5.0);
    }

    @Test 
    public void testDividedByMissingValue() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1400, 10.0);
        ts1.put(1500, 20.0);
        ts1.put(1600, 30.0);
        
        TimeSeries ts2 = new TimeSeries();
        ts2.put(1400, 2.0);
        ts2.put(1600, 6.0);

        try {
            ts1.dividedBy(ts2);
        } catch (IllegalArgumentException e){
        }
    }

    @Test
    public void testDividedByZeroValue() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1400, 10.0);
        ts1.put(1600, 30.0);
        
        TimeSeries ts2 = new TimeSeries();
        ts2.put(1400, 2.0);
        ts2.put(1600, 0.0);
    }

    @Test 
    public void testDividedBy() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(1400, 10.0);
        ts1.put(1600, 30.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(1400, 2.0);
        ts2.put(1500, 4.0);
        ts2.put(1600, 6.0);

        TimeSeries result = ts1.dividedBy(ts2);

        assertThat(result.get(1400)).isWithin(1E-10).of(5.0);
        assertThat(result.get(1500)).isNull();
        assertThat(result.get(1600)).isWithin(1E-10).of(5.0);
    }
}