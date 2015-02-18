package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.Factor;

import javax.inject.Inject;
import java.util.SortedMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

/**
* Created by barrera on 27/10/2014.
*
* Testing barchart data related to proteomics experiments
*/

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ProteomicsBarChartTraderIT {

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final String DEVELOPMENTAL_STAGE = "DEVELOPMENTAL_STAGE";
    private static final String PROTEOMICS_EXPERIMENT_ACCESSION = "E-PROT-1";

    @Inject
    private BarChartTradersCache barChartTradersCache;

    private BarChartTrader subject;


    @Before
    public void setUp() throws Exception {
        this.subject = barChartTradersCache.getBarchartTrader(PROTEOMICS_EXPERIMENT_ACCESSION);
    }


    @Test
    public void proteomicsChartDataAllExperimentalFactorTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, null);
        //returns
        assertThat(chartData, hasEntry(0.0, 300));
        assertThat(chartData, hasEntry(0.1, 208));
        assertThat(chartData, hasEntry(0.2, 208));

    }

    @Test
    public void proteomicsChartDataForSomeExperimentalFactorsTest() {
        SortedMap<Double, Integer> chartData = subject.getChart(null, Sets.newHashSet(new Factor(ORGANISM_PART, "testis")
                , new Factor(DEVELOPMENTAL_STAGE, "adult")));
        //returns
        assertThat(chartData, hasEntry(0.0, 290));
        assertThat(chartData, hasEntry(50.0, 194));
        assertThat(chartData, hasEntry(6000000.0, 142));

    }
}
