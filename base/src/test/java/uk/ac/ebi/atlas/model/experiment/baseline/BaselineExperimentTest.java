package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentTest {
    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";

    private static final List<AssayGroup> ASSAY_GROUPS = ImmutableList.of(
            new AssayGroup("g1", RUN_ACCESSION1),
            new AssayGroup("g2", RUN_ACCESSION2));

    @Test
    public void testGetExperimentRunAccessions() {
        BaselineExperiment subject = MockExperiment.createBaselineExperiment(ASSAY_GROUPS);

        assertThat(subject.getAnalysedAssays()).containsExactlyInAnyOrder(RUN_ACCESSION1, RUN_ACCESSION2);
    }

    @Test
    public void orderOfAssayGroupsIsPreserved() {
        int num = (int) Math.round(Math.random()*10000);
        List<AssayGroup> assayGroups = new ArrayList<>(num);
        for (int i = 0; i< num; i++) {
            assayGroups.add(new AssayGroup("id_"+i, "assay_"+i));
        }

        assertThat(MockExperiment.createBaselineExperiment(assayGroups).getDataColumnDescriptors())
                .hasSameElementsAs(assayGroups);
    }
}
