package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExperimentPageServiceTest {
    private Experiment experiment;
    private ExperimentPageRequestPreferences preferences;
    private ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    @Before
    public void setUp() {
        experiment = mock(Experiment.class);
        when(experiment.getAccession()).thenReturn("E-MOCK-1");
        when(experiment.getType()).thenReturn(experimentType);
        preferences = new RnaSeqBaselineRequestPreferences();
    }

    @Test
    public void testGeneSpecificResultsLink() {
        preferences.setCutoff(1.234);
        preferences.setSelectedColumnIds(ImmutableSet.of("g1", "g2"));

        URI result =
                new ExperimentPageService().geneSpecificResultsLink(experiment, "ENSG0000012345", "", preferences);

        assertThat(result.getPath(), is("json/experiments/E-MOCK-1/genes/ENSG0000012345"));
        assertThat(result.getQuery(), containsString("cutoff=1.234"));
        assertThat(result.getQuery(), containsString("selectedColumnIds=g1,g2"));
        assertThat(result.getQuery(), containsString("type=" + ExperimentType.RNASEQ_MRNA_BASELINE.name()));
    }

    @Test
    public void unitsMatterForExperimentDownloadLink() {
        RnaSeqBaselineRequestPreferences rnaSeqBaselineRequestPreferences = new RnaSeqBaselineRequestPreferences();

        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        assertThat(
                new ExperimentPageService()
                        .experimentDownloadLink(experiment, "", rnaSeqBaselineRequestPreferences).toString(),
                containsString("unit=TPM"));

        rnaSeqBaselineRequestPreferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);

        assertThat(
                new ExperimentPageService()
                        .experimentDownloadLink(experiment, "", rnaSeqBaselineRequestPreferences).toString(),
                containsString("unit=FPKM"));
    }

    @Test
    public void selectedIdsMatterForExperimentDownloadLink() {
        preferences.setSelectedColumnIds(ImmutableSet.of());
        String url1 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();


        preferences.setSelectedColumnIds(ImmutableSet.of("g1"));
        String url2 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();


        preferences.setSelectedColumnIds(ImmutableSet.of("g1", "g2"));
        String url3 = new ExperimentPageService().experimentDownloadLink(experiment, "", preferences).toString();

        assertThat(url1, is(not(equalTo(url2))));
        assertThat(url1, is(not(equalTo(url3))));
        assertThat(url2, is(not(equalTo(url3))));
    }
}
