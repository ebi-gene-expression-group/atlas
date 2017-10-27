package uk.ac.ebi.atlas.experimentpage.qc;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class MicroarrayQCFilesIT {

    @Inject
    ExpressionAtlasExperimentTrader expressionAtlasExperimentTrader;

    @Inject
    DataFileHub dataFileHub;

    @Test
    public void allExperiments() throws Exception {
        for(String accession: expressionAtlasExperimentTrader.getMicroarrayExperimentAccessions()){
            testExperiment(accession);
        }
    }

    private void testExperiment(String accession){
        MicroarrayExperiment experiment = (MicroarrayExperiment) expressionAtlasExperimentTrader.getPublicExperiment(accession);

        MicroarrayQCFiles microarrayQCFiles = new MicroarrayQCFiles(dataFileHub.getExperimentFiles(accession).qcFolder);

        for(String arrayDesignReadOffFromFolderName: microarrayQCFiles.getArrayDesignsThatHaveQcReports()){
            assertThat(arrayDesignReadOffFromFolderName,
                    Matchers.isOneOf(new ArrayList(experiment.getArrayDesignAccessions()).toArray()));
        }

    }


}