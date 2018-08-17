package uk.ac.ebi.atlas.experimentpage.qc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class MicroarrayQCFilesIT {
    @Inject
    private ExpressionAtlasExperimentTrader expressionAtlasExperimentTrader;

    @Inject
    private DataFileHub dataFileHub;

    @Test
    public void allExperiments() {
        for (String accession: expressionAtlasExperimentTrader.getMicroarrayExperimentAccessions()) {
            testExperiment(accession);
        }
    }

    private void testExperiment(String accession) {
        MicroarrayExperiment experiment =
                (MicroarrayExperiment) expressionAtlasExperimentTrader.getPublicExperiment(accession);
        MicroarrayQCFiles microarrayQCFiles =
                new MicroarrayQCFiles(dataFileHub.getExperimentFiles(accession).qcFolder);

        for (String arrayDesignReadOffFromFolderName : microarrayQCFiles.getArrayDesignsThatHaveQcReports()) {
            assertThat(arrayDesignReadOffFromFolderName,
                    is(oneOf(new ArrayList<>(experiment.getArrayDesignAccessions()).toArray())));
        }

    }
}
