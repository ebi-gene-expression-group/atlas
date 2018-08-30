package uk.ac.ebi.atlas.ebeye;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class BaselineExperimentAssayGroupsLinesIT {
    private BaselineExperimentAssayGroupsLines subject;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Test
    public void testSomeRnaSeqExperiments() {
        Object[] a = experimentTrader.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE).toArray();
        for (int i = 0; i < 10 && i < a.length; i++) {
            testExtractExperimentDesignFromRnaSeqExperiment((String) a[i]);
        }
    }

    private void testExtractExperimentDesignFromRnaSeqExperiment(String experimentAccession) {
        BaselineExperiment baselineExperiment =
                (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        assertTrue(baselineExperiment.getType().isRnaSeqBaseline());
        subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        assertAbout(subject, experimentAccession);
    }

    private void assertAbout(BaselineExperimentAssayGroupsLines subject, String experimentAccession) {
        assertTrue(subject.iterator().hasNext());
        for (String[] line : subject) {
            String str = "This line: " + Arrays.asList(line).toString();

            assertThat(line.length, is(6));
            assertThat(line[0], is(experimentAccession));
            assertTrue("First column is a group id:" + line[1], Pattern.matches("g.+", line[1]));
            assertThat(str, line[2], is(not(emptyString())));
            assertThat(str, line[2], is(not(emptyString())));
            assertThat(str, line[3], is(not(emptyString())));
            //4th line is sometimes empty
            assertTrue(
                    "Sixth column is empty or a link to ontology term:" + line[5],
                    line[5].isEmpty() || Pattern.matches("http.+", line[5]));
        }
    }

    @Test
    public void testSomeProteomicsExperiments() {
        for (String accession : experimentTrader.getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE)) {
            testExtractExperimentDesignFromProteomicsExperiment(accession);
        }
    }

    private void testExtractExperimentDesignFromProteomicsExperiment(String experimentAccession) {
        BaselineExperiment baselineExperiment =
                (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        assertTrue(baselineExperiment.getType().isProteomicsBaseline());
        subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        assertAbout(subject, experimentAccession);
    }
}
