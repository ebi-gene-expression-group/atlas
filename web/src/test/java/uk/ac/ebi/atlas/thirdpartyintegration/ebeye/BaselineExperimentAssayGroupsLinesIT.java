package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentAssayGroupsLinesIT {

    private static final String EXPERIMENT_ACCESSION = "E-GEOD-30352";

    private BaselineExperimentAssayGroupsLines subject;

    @Inject
    private ExperimentTrader experimentTrader;

    @Test
    public void testExtractExperimentDesign() throws IOException {
        BaselineExperiment baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(EXPERIMENT_ACCESSION);

        subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        Iterator<String[]> lines = subject.iterator();

        String[] line1 = lines.next();

        assertThat(lines.hasNext(), is(true));
        assertThat(line1.length, is(6));
        assertThat(line1[0], is("E-GEOD-30352"));
        assertThat(line1[1], is("g1"));
        assertThat(line1[2], is("characteristic"));
        assertThat(line1[3], is("biosource provider"));
        assertThat(line1[4], is("National Disease Research Interchange, USA (NDRI)"));
        assertThat(line1[5], is(""));

        String[] line2 = lines.next();

        assertThat(lines.hasNext(), is(true));
        assertThat(line2.length, is(6));
        assertThat(line2[0], is("E-GEOD-30352"));
        assertThat(line2[1], is("g1"));
        assertThat(line2[2], is("characteristic"));
        assertThat(line2[3], is("sex"));
        assertThat(line2[4], is("male"));
        assertThat(line2[5], is("http://www.ebi.ac.uk/efo/EFO_0001266"));
    }

}
