package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalytics;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperimentTest;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class DifferentialExperimentDataPointTest {

    @Test
    public void testGetRelevantBioentityPropertyNames() throws Exception {
        List<BioentityPropertyName> propertyNames = new DifferentialExperimentDataPoint(DifferentialExperimentTest.mockExperiment("E-GEOD-54705", ImmutableList.<Contrast>of()),new RnaSeqDifferentialAnalytics("","",0.03, 1.23),"",5)
                .getRelevantBioentityPropertyNames();

        assertThat(propertyNames.contains(BioentityPropertyName.INTERPRO), is(true));
        assertThat(propertyNames.contains(BioentityPropertyName.UNIPROT), is(true));
        assertThat(propertyNames.contains(BioentityPropertyName.DESIGN_ELEMENT), is(false));
    }
}