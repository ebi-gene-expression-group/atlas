package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BarChartTraderWithEmptyFilterFactorValuesTest {

    private BarChartTrader subject;

    private NavigableMap<Double, Map<Set<FactorValue>, BitSet>> geneExpressionIndexes = new TreeMap<>();

    private final BitSetMapFactory bitSetMapFactory = new BitSetMapFactory();

    private static FactorValue factorValue1 = new FactorValue("op", "op1");
    private static FactorValue factorValue2 = new FactorValue("op", "op2");
    private static FactorValue factorValue3 = new FactorValue("op", "op3");

    @Before
    public void initSubject() {
        geneExpressionIndexes.put(0d, bitSetMapFactory.createChartSize5());
        geneExpressionIndexes.put(1d, bitSetMapFactory.createChartSize2());
        this.subject = new BarChartTrader(geneExpressionIndexes);
    }

    @Test
    public void testGetChartWithoutOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(null, null);

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(5));
        assertThat(chart.get(1d), is(2));
    }

    @Test
    public void testGetChartWithOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(null, Sets.newHashSet(factorValue1, factorValue2));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(4));
        assertThat(chart.get(1d), is(2));
    }

    @Test
    public void testCountGenesAboveCutoff() {
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, null), is(5));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, Sets.newHashSet(factorValue1)), is(3));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, Sets.newHashSet(factorValue1, factorValue3)), is(5));
    }

    private static  class BitSetMapFactory {
        public BitSetMapFactory() { }

        Map<Set<FactorValue>, BitSet> createChartSize5() {
            Map<Set<FactorValue>, BitSet> map = new HashMap();
            map.put(Sets.newHashSet(factorValue1), initBitSet(1, 3, 5));
            map.put(Sets.newHashSet(factorValue2), initBitSet(0, 3));
            map.put(Sets.newHashSet(factorValue3), initBitSet(0, 1, 4));

            return map;
        }

        Map<Set<FactorValue>, BitSet> createChartSize2() {
            Map<Set<FactorValue>, BitSet> map = new HashMap();
            map.put(Sets.newHashSet(factorValue1), initBitSet(3, 5));
            map.put(Sets.newHashSet(factorValue2), initBitSet(3));
            map.put(Sets.newHashSet(factorValue3), initBitSet(5));

            return map;
        }

        BitSet initBitSet(int... setPositions) {
            BitSet bs = new BitSet();
            for (int setPosition : setPositions) {
                bs.set(setPosition);
            }
            return bs;
        }
    }
}
