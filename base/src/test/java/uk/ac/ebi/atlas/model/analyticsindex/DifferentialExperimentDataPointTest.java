package uk.ac.ebi.atlas.model.analyticsindex;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq.RnaSeqDifferentialAnalytics;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.testutils.MockExperiment.createDifferentialExperiment;

public class DifferentialExperimentDataPointTest {

    @Test
    public void testGetRelevantBioentityPropertyNames() {
        DifferentialExperimentDataPoint subject =
                new DifferentialExperimentDataPoint(createDifferentialExperiment(
                        "E-GEOD-54705", ImmutableList.of()), new RnaSeqDifferentialAnalytics("","",0.03, 1.23),"",5);

        assertThat(subject.getProperties())
                .containsKeys("factors", "regulation", "contrast_id", "num_replicates", "fold_change", "p_value")
                .doesNotContainKeys("expression_level", "expression_level_fpkm", "expression_levels",
                                    "expression_levels_fpkm", "assay_group_id");
    }
}