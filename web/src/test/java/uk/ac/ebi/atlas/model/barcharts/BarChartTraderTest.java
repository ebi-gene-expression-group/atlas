package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.BitSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BarChartTraderTest {

    private BarChartTrader subject;

    private NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = new TreeMap<>();

    private final BitSetMapFactory bitSetMapFactory = new BitSetMapFactory();

    @Before
    public void initSubject() {
        geneExpressionIndexes.put(0d, bitSetMapFactory.createChartSize5());
        geneExpressionIndexes.put(1d, bitSetMapFactory.createChartSize2());
        this.subject = new BarChartTrader(geneExpressionIndexes);
    }

    @Test
    public void testGetChartWithoutOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart();

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(5));
        assertThat(chart.get(1d), is(2));
    }

    @Test
    public void testGetChartWithOrganismParts() throws Exception {

        NavigableMap<Double, Integer> chart = subject.getChart(Sets.newHashSet("op1", "op2"));

        assertThat(chart.size(), is(2));
        assertThat(chart.get(0d), is(2));
        assertThat(chart.get(1d), is(1));
    }

    @Test
    public void testCountGenesAboveCutoff() {
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), null), is(5));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet("op1")), is(1));
        assertThat(BarChartTrader.countGenesAboveCutoff(bitSetMapFactory.createChartSize5(), Sets.newHashSet("op1", "op3")), is(3));
    }

}
