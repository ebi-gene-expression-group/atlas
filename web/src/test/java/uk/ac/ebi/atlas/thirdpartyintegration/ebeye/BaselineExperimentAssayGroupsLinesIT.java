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

        String[] result = lines.next();

        assertThat(lines.hasNext(), is(true));
        assertThat(result.length, is(5));
        assertThat(result[0], is("E-GEOD-30352"));
        assertThat(result[1], is("g37"));
        assertThat(result[2], is("characteristic"));
        assertThat(result[3], is("biosource provider"));
        assertThat(result[4], is("Opossum colonies from the Museum of Natural History, Berlin, Germany"));
    }

}
