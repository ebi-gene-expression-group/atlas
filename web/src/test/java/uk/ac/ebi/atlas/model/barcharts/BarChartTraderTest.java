//package uk.ac.ebi.atlas.model.barcharts;
//
//import com.google.common.collect.Sets;
//import org.junit.Before;
//import org.junit.Test;
//import uk.ac.ebi.atlas.model.FactorValueSet;
//
//import java.util.*;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class BarChartTraderTest {
//
//    private static final boolean INCLUDE_GENES_EXPRESSED_ALSO_ON_NON_SELECTED_FACTOR_VALUE = false;
//    private BarChartTrader subject;
//
//    private NavigableMap<Double, Map<FactorValueSet, BitSet>> geneExpressionIndexes = new TreeMap<>();
//
//    private final BitSetMapFactory bitSetMapFactory = new BitSetMapFactory();
//
//    @Before
//    public void initSubject() {
//        geneExpressionIndexes.put(0d, bitSetMapFactory.createChartSize5());
//        geneExpressionIndexes.put(1d, bitSetMapFactory.createChartSize2());
//        this.subject = new BarChartTrader(geneExpressionIndexes);
//    }
//
//    @Test
//    public void testGetChartWithoutOrganismParts() throws Exception {
//
//        NavigableMap<Double, Integer> chart = subject.getChart(null);
//
//        assertThat(chart.size(), is(2));
//        assertThat(chart.get(0d), is(5));
//        assertThat(chart.get(1d), is(2));
//    }
//
//    @Test
//    public void testGetChartWithOrganismParts() throws Exception {
//
//        NavigableMap<Double, Integer> chart = subject.getChart(Sets.newHashSet("op1", "op2"), INCLUDE_GENES_EXPRESSED_ALSO_ON_NON_SELECTED_FACTOR_VALUE);
//
//        assertThat(chart.size(), is(2));
//        assertThat(chart.get(0d), is(2));
//        assertThat(chart.get(1d), is(1));
//    }
//
//    @Test
//    public void testCountGenesAboveCutoff() {
//        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null, INCLUDE_GENES_EXPRESSED_ALSO_ON_NON_SELECTED_FACTOR_VALUE), is(5));
//        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet("op1"), INCLUDE_GENES_EXPRESSED_ALSO_ON_NON_SELECTED_FACTOR_VALUE), is(1));
//        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet("op1", "op3"), INCLUDE_GENES_EXPRESSED_ALSO_ON_NON_SELECTED_FACTOR_VALUE), is(3));
//    }
//
//    private static  class BitSetMapFactory {
//        public BitSetMapFactory() { }
//
//        FactorValueSet
//        Map<String, BitSet> createChartSize5() {
//            Map<String, BitSet> map = new HashMap<String, BitSet>();
//            map.put("op1", initBitSet(1, 3, 5));
//            map.put("op2", initBitSet(0, 3));
//            map.put("op3", initBitSet(1, 0, 4));
//
//            return map;
//        }
//
//        Map<String, BitSet> createChartSize2() {
//            Map<String, BitSet> map = new HashMap<String, BitSet>();
//            map.put("op1", initBitSet(3, 5));
//            map.put("op2", initBitSet(3));
//            map.put("op3", initBitSet(5));
//
//            return map;
//        }
//
//        BitSet initBitSet(int... setPositions) {
//            BitSet bs = new BitSet();
//            for (int setPosition : setPositions) {
//                bs.set(setPosition);
//            }
//            return bs;
//        }
//    }
//}
