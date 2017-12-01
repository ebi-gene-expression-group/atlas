package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.LinkToGene;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BaselineTranscriptProfileStreamFactoryTest {

    MockDataFileHub dataFileHub;

    BaselineTranscriptProfileStreamFactory subject;

    String[] header = new String[]{"gene id", "gene name", "transcript name", "assay_1", "assay_2", "assay_3"};

    BiologicalReplicate assay_1 = new BiologicalReplicate("assay_1");
    BiologicalReplicate assay_2 = new BiologicalReplicate("assay_2");
    BiologicalReplicate assay_3 = new BiologicalReplicate("assay_3");
    AssayGroup g1 = new AssayGroup("g1", ImmutableSet.of(assay_1));
    AssayGroup g2 = new AssayGroup("g2", ImmutableSet.of(assay_2, assay_3));

    BaselineExperiment experiment = BaselineExperimentTest.mockExperiment(ImmutableList.of(g1, g2), "accession");


    @Before
    public void setUp() throws Exception {
        dataFileHub = MockDataFileHub.create();
        subject = new BaselineTranscriptProfileStreamFactory(dataFileHub);
    }

    @Test
    public void whenTheFileIsNotPresentReturnEmptyStream(){
        assertThat(
                ImmutableList.copyOf(
                        new IterableObjectInputStream<>(subject.create(experiment, new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment), Collections.emptySet()))
                ),
                empty()
        );
    }


    void testCase(List<String[]> dataLines, Collection<String> getGeneIds, List<BaselineExpressionPerReplicateProfile> expected) {
        dataFileHub.addTranscriptsTpmsExpressionFile(experiment.getAccession(), dataLines);
        assertThat(
                ImmutableList.copyOf(
                        new IterableObjectInputStream<>(subject.create(experiment, new BaselineRequestContext<>(new RnaSeqBaselineRequestPreferences(), experiment), getGeneIds))
                ),
                is(expected)
        );
    }

    BaselineExpressionPerReplicateProfile profile(String id,
                                                  BaselineExpressionPerBiologicalReplicate expressionForG1,
                                                  BaselineExpressionPerBiologicalReplicate expressionForG2) {
        BaselineExpressionPerReplicateProfile profile = new BaselineExpressionPerReplicateProfile(id, id);
        Optional.ofNullable(expressionForG1).ifPresent(e ->
                profile.add(g1, e)
        );
        Optional.ofNullable(expressionForG2).ifPresent(e ->
                profile.add(g2, e)
        );
        return profile;
    }

    @Test
    public void nullCases() {
        testCase(
                ImmutableList.of(
                        header
                ),
                Collections.emptySet(),
                ImmutableList.of()
        );
        testCase(
                ImmutableList.of(
                        header
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of()
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}
                ),
                ImmutableList.of("different_id"),
                ImmutableList.of()
        );
    }

    @Test
    public void oneId() {
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "NA", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("transcript_1_1", null, null))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), null))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "NA", "NA"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), null))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_2, new BaselineExpression(2.0)
                ))))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "NA"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_2, new BaselineExpression(2.0)
                ))))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_2, new BaselineExpression(2.0),
                        assay_3, new BaselineExpression(3.0)
                ))))
        );
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_1, new BaselineExpression(1.0)
                )), new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                        assay_2, new BaselineExpression(2.0),
                        assay_3, new BaselineExpression(3.0)
                ))))
        );
    }

    @Test
    public void largerCase() {
        testCase(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "transcript_1_1", "1.0", "2.0", "3.0"},
                        new String[]{"id_1", "name_1", "transcript_1_2", "4.0", "NA", "NA"},
                        new String[]{"id_2", "name_2", "transcript_2_1", "5.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(
                        profile("transcript_1_1", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                                assay_1, new BaselineExpression(1.0)
                        )), new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                                assay_2, new BaselineExpression(2.0),
                                assay_3, new BaselineExpression(3.0)
                        )))
                        ,
                        profile("transcript_1_2", new BaselineExpressionPerBiologicalReplicate(ImmutableMap.of(
                                assay_1, new BaselineExpression(4.0)
                        )), null)
                )
        );
    }

}