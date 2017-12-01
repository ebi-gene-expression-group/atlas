package uk.ac.ebi.atlas.experimentpage;

import org.junit.Test;
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

    @Test
    public void testGeneSpecificResultsLink() throws Exception {
        Experiment experiment = mock(Experiment.class);
        when(experiment.getAccession()).thenReturn("E-MOCK-1");
        when(experiment.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);
        ExperimentPageRequestPreferences preferences = new RnaSeqBaselineRequestPreferences();
        preferences.setCutoff(1.234);

        URI result = new ExperimentPageService().geneSpecificResultsLink(experiment, "ENSG0000012345", "", preferences);

        assertThat(result.getPath(), is("json/experiments/E-MOCK-1/genes/ENSG0000012345"));
        assertThat(result.getQuery(), containsString("cutoff=1.234"));
        assertThat(result.getQuery(), containsString("type="+ExperimentType.RNASEQ_MRNA_BASELINE.name()));
    }
}