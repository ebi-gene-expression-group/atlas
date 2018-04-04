package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonBaselineExperimentControllerTest {

    @Test
    public void columnHeadersShowUp() {

        BaselineExperiment experiment = mock(BaselineExperiment.class);

        when(experiment.getDisplayDefaults()).thenReturn(ExperimentDisplayDefaults.simpleDefaults());

        AssayGroup assayGroup = new AssayGroup("assay_group_id", "run_1");

        when(experiment.getFactors(assayGroup)).thenReturn(new FactorSet().add(new Factor("organism_part", "bladder")));

        when(experiment.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroup));

    }
}