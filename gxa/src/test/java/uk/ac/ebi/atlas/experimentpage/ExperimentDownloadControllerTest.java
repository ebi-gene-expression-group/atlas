package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import static org.junit.Assert.*;

public class ExperimentDownloadControllerTest {

    String experimentAccession = "E-MOCK-0";
    String accessKey = "";

    @Test
    public void unitsMatter() throws Exception {

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

    @Test
    public void selectedIdsMatter() throws Exception {

        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();

        preferences.setSelectedColumnIds(ImmutableSet.of());
        String url1 = ExperimentDownloadController.getUrl(experimentAccession, accessKey,
                ExperimentType.RNASEQ_MRNA_BASELINE, preferences);


        preferences.setSelectedColumnIds(ImmutableSet.of("g1"));
        String url2 = ExperimentDownloadController.getUrl(experimentAccession, accessKey,
                ExperimentType.RNASEQ_MRNA_BASELINE, preferences);


        preferences.setSelectedColumnIds(ImmutableSet.of("g1", "g2"));
        String url3 = ExperimentDownloadController.getUrl(experimentAccession, accessKey,
                ExperimentType.RNASEQ_MRNA_BASELINE, preferences);

        assertNotEquals(url1, url2);
        assertNotEquals(url1, url3);
        assertNotEquals(url2, url3);
    }

}