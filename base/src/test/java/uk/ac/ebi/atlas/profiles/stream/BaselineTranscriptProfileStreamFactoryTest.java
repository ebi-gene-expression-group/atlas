package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerBiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerReplicateProfile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineTranscriptProfileStreamFactoryTest {
    private static final String[] HEADER =
            new String[] {"gene id", "gene name", "transcript name", "assay_1", "assay_2", "assay_3"};
    private static final BiologicalReplicate ASSAY_1 = new BiologicalReplicate("assay_1");
    private static final BiologicalReplicate ASSAY_2 = new BiologicalReplicate("assay_2");
    private static final BiologicalReplicate ASSAY_3 = new BiologicalReplicate("assay_3");
    private static final AssayGroup G1 = new AssayGroup("g1", ImmutableSet.of(ASSAY_1));
    private static final AssayGroup G2 = new AssayGroup("g2", ImmutableSet.of(ASSAY_2, ASSAY_3));

    private static final BaselineExperiment EXPERIMENT =
            MockExperiment.createBaselineExperiment(ImmutableList.of(G1, G2));

    private MockDataFileHub dataFileHub;

    private BaselineTranscriptProfileStreamFactory subject;

    @Before
    public void setUp() throws Exception {
        dataFileHub = MockDataFileHub.create();
        subject = new BaselineTranscriptProfileStreamFactory(dataFileHub);
    }

    @Test
    public void whenTheFileIsNotPresentReturnEmptyStream() {
        assertThat(
                ImmutableList.copyOf(
                        new IterableObjectInputStream<>(
                                subject.create(
                                        EXPERIMENT,
                                        new BaselineRequestContext<>(
                                                new RnaSeqBaselineRequestPreferences(), EXPERIMENT),
                                        Collections.emptySet()))),
                is(empty()));
    }


    private void testCase(List<String[]> dataLines,
                          Collection<String> getGeneIds,
                          List<BaselineExpressionPerReplicateProfile> expected) {
        dataFileHub.addTranscriptsTpmsExpressionFile(EXPERIMENT.getAccession(), dataLines);
        assertThat(
                ImmutableList.copyOf(
                        new IterableObjectInputStream<>(
                                subject.create(
                                        EXPERIMENT,
                                        new BaselineRequestContext<>(
                                                new RnaSeqBaselineRequestPreferences(), EXPERIMENT),
                                        getGeneIds))),
                is(expected));
    }

    private BaselineExpressionPerReplicateProfile profile(String id,
                                                          BaselineExpressionPerBiologicalReplicate expressionForG1,
                                                          BaselineExpressionPerBiologicalReplicate expressionForG2) {
        BaselineExpressionPerReplicateProfile profile = new BaselineExpressionPerReplicateProfile(id, id);
        Optional.ofNullable(expressionForG1).ifPresent(e -> profile.add(G1, e));
        Optional.ofNullable(expressionForG2).ifPresent(e -> profile.add(G2, e));
        return profile;
    }

    @Test
    public void nullCases() {
        testCase(ImmutableList.of(HEADER), Collections.emptySet(), ImmutableList.of());
        testCase(ImmutableList.of(HEADER), ImmutableList.of("id_1"), ImmutableList.of());

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}),
                ImmutableList.of("different_id"),
                ImmutableList.of());
    }

    @Test
    public void oneId() {
        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "NA", "NA", "NA"}),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("transcript_1_1", null, null)));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}),
                ImmutableList.of("id_1"),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                null)));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}),
                Collections.emptySet(),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                null)));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "NA"}),
                ImmutableList.of("id_1"),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_2, new BaselineExpression(2.0))))));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "NA"}),
                Collections.emptySet(),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_2, new BaselineExpression(2.0))))));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"}),
                ImmutableList.of("id_1"),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(
                                                ASSAY_2, new BaselineExpression(2.0),
                                                ASSAY_3, new BaselineExpression(3.0))))));

        testCase(
                ImmutableList.of(HEADER, new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"}),
                Collections.emptySet(),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(
                                                ASSAY_2, new BaselineExpression(2.0),
                                                ASSAY_3, new BaselineExpression(3.0))))));
    }

    @Test
    public void largerCase() {
        testCase(
                ImmutableList.of(
                        HEADER,
                        new String[] {"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"},
                        new String[] {"id_1", "name_1", "transcript_1_2", "4.0", "NA", "NA"},
                        new String[] {"id_2", "name_2", "transcript_2_1", "5.0", "NA", "NA"}),
                ImmutableList.of("id_1"),
                ImmutableList.of(
                        profile(
                                "transcript_1_1",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(1.0))),
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(
                                                ASSAY_2, new BaselineExpression(2.0),
                                                ASSAY_3, new BaselineExpression(3.0)))),
                        profile(
                                "transcript_1_2",
                                new BaselineExpressionPerBiologicalReplicate(
                                        ImmutableMap.of(ASSAY_1, new BaselineExpression(4.0))), null)));
    }
}
