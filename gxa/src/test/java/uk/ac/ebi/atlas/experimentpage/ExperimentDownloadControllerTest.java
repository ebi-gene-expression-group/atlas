package uk.ac.ebi.atlas.experimentpage;

import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.junit.Assert.*;

public class ExperimentDownloadControllerTest {

    String experimentAccession = "E-MOCK-0";
    String accessKey = "";

    @Test
    public void parametersMatter() throws Exception {

        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();

        preferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        assertThat(
                ExperimentDownloadController.getUrl(experimentAccession, accessKey,
                ExperimentType.RNASEQ_MRNA_BASELINE, preferences),
                Matchers.containsString("unit=TPM")
        );

        preferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);

        assertThat(
                ExperimentDownloadController.getUrl(experimentAccession, accessKey,
                        ExperimentType.RNASEQ_MRNA_BASELINE, preferences),
                Matchers.containsString("unit=FPKM")
        );
    }

}