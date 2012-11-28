package uk.ac.ebi.atlas.utils.score;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HistogramCounterTest {

    private BarChartIndexes subject;

    private List<Double> marks;
    List<String> organismParts;

    @Before
    public void initSubject() {
        marks = Arrays.asList(0d, 0.5, 10d);
        organismParts = Arrays.asList("op1", "op2", "op3");

        subject = new BarChartIndexes(marks, organismParts);
    }


    @Test
    public void testInitialization() throws Exception {

        Map<Double, List<BitSet>> scoreMap = subject.getScoreMap();
        assertThat(scoreMap.size(), is(3));
        assertThat(scoreMap, hasKey(0.5));

        assertThat(scoreMap.get(0.5).size(), is(3));
        assertThat(scoreMap.get(0.5).get(0).size(), greaterThanOrEqualTo(BarChartIndexes.GENE_COUNT));
        assertThat(scoreMap.get(0.5).get(0).cardinality(), is(0));
    }


    /* Generated map looks like:

key: 0      0.5      10
    1 1 0   1 1 0   0 0 0
    0 1 1   0 0 1   0 0 1
     */
    @Test
    public void testAddValues() {
        subject.addValues(Arrays.asList(1.1, 8d, 0d), 0);
        subject.addValues(Arrays.asList(0d, 0.3, 100d), 1);

        Map<Double, List<BitSet>> scoreMap = subject.getScoreMap();

        //For cutoff 0
        //for op1
        assertThat(scoreMap.get(0d).get(0).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0d).get(0).toString(), is("{0}"));

        //for op2
        assertThat(scoreMap.get(0d).get(1).cardinality(), is(2));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0d).get(1).toString(), is("{0, 1}"));

        //for op3
        assertThat(scoreMap.get(0d).get(2).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0d).get(2).toString(), is("{1}"));

        //For cutoff 0.5
        //for op1
        assertThat(scoreMap.get(0.5).get(0).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0.5).get(0).toString(), is("{0}"));

        //for op2
        assertThat(scoreMap.get(0.5).get(1).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0.5).get(1).toString(), is("{0}"));

        //for op3
        assertThat(scoreMap.get(0.5).get(2).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(0.5).get(2).toString(), is("{1}"));

        //For cutoff 10
        //for op1
        assertThat(scoreMap.get(10d).get(0).cardinality(), is(0));
        //Bit set true on the position 0
        assertThat(scoreMap.get(10d).get(0).toString(), is("{}"));

        //for op2
        assertThat(scoreMap.get(10d).get(1).cardinality(), is(0));
        //Bit set true on the position 0
        assertThat(scoreMap.get(10d).get(1).toString(), is("{}"));

        //for op3
        assertThat(scoreMap.get(10d).get(2).cardinality(), is(1));
        //Bit set true on the position 0
        assertThat(scoreMap.get(10d).get(2).toString(), is("{1}"));
    }

    public void testGetOrganismPartIndexes() throws Exception {
        List<Integer> organismPartIndexes = subject.getOrganismPartIndexes(Arrays.asList("op1", "op3"));
        assertThat(organismPartIndexes, contains(0, 3));
    }

}
