package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableSet;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExperimentPageServiceTest {

    Experiment experiment;
    ExperimentPageRequestPreferences preferences;
    ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;
    @Before
    public void setUp(){
        experiment = mock(Experiment.class);
        when(experiment.getAccession()).thenReturn("E-MOCK-1");
        when(experiment.getType()).thenReturn(experimentType);
        preferences = new RnaSeqBaselineRequestPreferences();
    }
    @Test
    public void testGeneSpecificResultsLink() throws Exception {

        preferences.setCutoff(1.234);
        preferences.setSelectedColumnIds(ImmutableSet.of("g1", "g2"));

        URI result = new ExperimentPageService().geneSpecificResultsLink(experiment, "ENSG0000012345", "", preferences);

        assertThat(result.getPath(), is("json/experiments/E-MOCK-1/genes/ENSG0000012345"));
        assertThat(result.getQuery(), containsString("cutoff=1.234"));
        assertThat(result.getQuery(), containsString("selectedColumnIds=g1,g2"));
        assertThat(result.getQuery(), containsString("type="+ExperimentType.RNASEQ_MRNA_BASELINE.name()));
    }


    @Test
    public void unitsMatterForExperimentDownloadLink() throws Exception {

        RnaSeqBaselineRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();

        preferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        assertThat(
                new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString(),
                Matchers.containsString("unit=TPM")
        );

        preferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);

        assertThat(
                new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString(),
                Matchers.containsString("unit=FPKM")
        );
    }

    @Test
    public void selectedIdsMatterForExperimentDownloadLink() throws Exception {


        preferences.setSelectedColumnIds(ImmutableSet.of());
        String url1 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();


        preferences.setSelectedColumnIds(ImmutableSet.of("g1"));
        String url2 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();


        preferences.setSelectedColumnIds(ImmutableSet.of("g1", "g2"));
        String url3 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();

        assertNotEquals(url1, url2);
        assertNotEquals(url1, url3);
        assertNotEquals(url2, url3);
    }


}