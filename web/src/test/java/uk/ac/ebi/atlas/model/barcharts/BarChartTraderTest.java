package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BarChartTraderTest {

    private BarChartTrader subject;

    private NavigableMap<Double, Map<Set<FactorValue>, BitSet>> geneExpressionIndexes = new TreeMap<>();

    private final BitSetMapFactory bitSetMapFactory = new BitSetMapFactory();

    private static FactorValue organismPart1 = new FactorValue("op", "op1");
    private static FactorValue organismPart2 = new FactorValue("op", "op2");
    private static FactorValue organismPart3 = new FactorValue("op", "op3");

    private static FactorValue origin1 = new FactorValue("origin", "origin1");
    private static FactorValue origin2 = new FactorValue("origin", "origin2");

    @Before
    public void initSubject() {
        geneExpressionIndexes.put(0d, bitSetMapFactory.createChartSize5());
        geneExpressionIndexes.put(1d, bitSetMapFactory.createChartSize2());
        this.subject = new BarChartTrader(geneExpressionIndexes);
    }

    @Test
    public void testGetChartWithoutFilterAndQueryFactorValues() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(null, null);

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(5));
        assertThat(chart.get(1d), is(2));
    }


    @Test
    public void testGetChartWithOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(null, Sets.newHashSet(organismPart1, organismPart2));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(4));
        assertThat(chart.get(1d), is(2));
    }

    @Test
    public void testGetChartWithValidFilterAndOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(Sets.newHashSet(origin1), Sets.newHashSet(organismPart1, organismPart2));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(4));
        assertThat(chart.get(1d), is(2));
    }

    @Test
    public void testGetChartWithNotCoveredFilterOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(Sets.newHashSet(origin2), Sets.newHashSet(organismPart1, organismPart2));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(0));
        assertThat(chart.get(1d), is(0));
    }

    @Test
    public void testGetChartWithNotValidFilterOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(Sets.newHashSet(origin2), Sets.newHashSet(organismPart1, organismPart3));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(3));
        assertThat(chart.get(1d), is(1));
    }

    @Test
    public void testCountGenesAboveCutoffWithoutFilterValues() {
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, null), is(5));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, Sets.newHashSet(organismPart1)), is(3));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, Sets.newHashSet(organismPart1, organismPart3)), is(5));
    }

    @Test
    public void testCountGenesAboveCutoffWithFilterFactorValues() {
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet(origin1), null), is(4));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet(origin1), Sets.newHashSet(organismPart1)), is(3));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet(origin1), Sets.newHashSet(organismPart1, organismPart3)), is(3));
    }

    private static class BitSetMapFactory {
        public BitSetMapFactory() { }

        Map<Set<FactorValue>, BitSet> createChartSize5() {
            Map<Set<FactorValue>, BitSet> map = new HashMap();
            map.put(Sets.newHashSet(organismPart1, origin1), initBitSet(1, 3, 5));
            map.put(Sets.newHashSet(organismPart2, origin1), initBitSet(0, 3));
            map.put(Sets.newHashSet(organismPart3, origin2), initBitSet(0, 1, 4));

            return map;
        }

        Map<Set<FactorValue>, BitSet> createChartSize2() {
            Map<Set<FactorValue>, BitSet> map = new HashMap();
            map.put(Sets.newHashSet(organismPart1, origin1), initBitSet(3, 5));
            map.put(Sets.newHashSet(organismPart2, origin1), initBitSet(3));
            map.put(Sets.newHashSet(organismPart3, origin2), initBitSet(5));

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
