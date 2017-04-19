package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class BaselineExperimentAssayGroupsLinesIT {


    private BaselineExperimentAssayGroupsLines subject;

    @Inject
    private ExpressionAtlasExperimentTrader experimentTrader;

    @Test
    public void testSomeRnaSeqExperiments() throws Exception{
        Object[] a = experimentTrader.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE).toArray();
        for(int i = 0 ; i <10 && i < a.length ; i++) {
            testExtractExperimentDesignFromRnaSeqExperiment((String) a[i]);
        }
    }

    public void testExtractExperimentDesignFromRnaSeqExperiment(String experimentAccession) throws IOException {
        BaselineExperiment baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        assertTrue(baselineExperiment.getType().isRnaSeqBaseline());
        subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        assertAbout(subject, experimentAccession);
    }

    private void assertAbout(BaselineExperimentAssayGroupsLines subject, String experimentAccession){
        assertTrue(subject.iterator().hasNext());
        Iterator<String[]> lines = subject.iterator();
        while(lines.hasNext()) {
            String[] line = lines.next();
            String str = "This line: "+ Arrays.asList(line).toString();

            assertThat(line.length, is(6));
            assertThat(line[0], is(experimentAccession));
            assertTrue("First column is a group id:"+line[1], Pattern.matches("g.+", line[1]));
            assertThat(str, line[2], not(isEmptyString()));
            assertThat(str, line[3], not(isEmptyString()));
            //4th line is sometimes empty
            assertTrue("Sixth column is empty or a link to ontology term:"+line[5],
                    line[5].isEmpty() || Pattern.matches("http.+", line[5]));

        }
    }
    @Test
    public void testSomeProteomicsExperiments() throws Exception{
        for(String accession : experimentTrader.getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE)){
            testExtractExperimentDesignFromProteomicsExperiment(accession);
        }
    }

    public void testExtractExperimentDesignFromProteomicsExperiment(String experimentAccession) throws IOException {
        BaselineExperiment baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        assertTrue(baselineExperiment.getType().isProteomicsBaseline());
        subject = new BaselineExperimentAssayGroupsLines(baselineExperiment);

        assertAbout(subject, experimentAccession);
    }

}
