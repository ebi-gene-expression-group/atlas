package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.*;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RnaSeqProfileStreamFactoryTest {

    MockDataFileHub dataFileHub;

    RnaSeqProfileStreamFactory subject;

    AssayGroup g1 = new AssayGroup("g1", "assay_1");
    AssayGroup g2 = new AssayGroup("g2", "assay_2");
    AssayGroup g3 = new AssayGroup("g3", "assay_3");

    Contrast g1_g2 = new Contrast("g1_g2", null, g1, g2, "contrast 1");
    Contrast g1_g3 = new Contrast("g1_g3", null, g1, g3, "contrast 2");

    DifferentialExperiment experiment = DifferentialExperimentTest.mockExperiment("accession", ImmutableList.of(g1_g2, g1_g3));

    String[] header = new String[]{"Gene ID", "Gene Name", "g1_g2.p-value", "g1_g2.log2foldchange", "g1_g3.p-value", "g1_g3.log2foldchange"};

    @Before
    public void setUp() throws Exception {
        dataFileHub = MockDataFileHub.create();
        subject = new RnaSeqProfileStreamFactory(dataFileHub);
    }

    void testCaseNoExpressionFilter(List<String[]> dataLines, Collection<String> getGeneIds, List<RnaSeqProfile> expected) {
        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setFoldChangeCutoff(0.0);
        differentialRequestPreferences.setCutoff(1.0);
        testCase(dataLines, getGeneIds, expected, differentialRequestPreferences);
    }

    void testCaseNoCutoff(List<String[]> dataLines, Regulation regulation, List<RnaSeqProfile> expected) {
        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setFoldChangeCutoff(0.0);
        differentialRequestPreferences.setCutoff(1.0);
        differentialRequestPreferences.setRegulation(regulation);
        testCase(dataLines, Collections.emptySet(), expected, differentialRequestPreferences);
    }
    void testCaseFoldChangeCutoff(List<String[]> dataLines, Double foldChangeCutoff, List<RnaSeqProfile> expected) {
        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setFoldChangeCutoff(foldChangeCutoff);
        differentialRequestPreferences.setCutoff(1.0);
        testCase(dataLines, Collections.emptySet(), expected, differentialRequestPreferences);
    }

    void testCasePValueCutoff(List<String[]> dataLines, Double pValueCutoff, List<RnaSeqProfile> expected) {
        DifferentialRequestPreferences differentialRequestPreferences = new DifferentialRequestPreferences();
        differentialRequestPreferences.setFoldChangeCutoff(0.0);
        differentialRequestPreferences.setCutoff(pValueCutoff);
        testCase(dataLines, Collections.emptySet(), expected, differentialRequestPreferences);
    }

    void testCase(List<String[]> dataLines, Collection<String> getGeneIds, List<RnaSeqProfile> expected, DifferentialRequestPreferences differentialRequestPreferences) {
        dataFileHub.addRnaSeqAnalyticsFile(experiment.getAccession(), dataLines);
        assertThat(
                ImmutableList.copyOf(
                        new IterableObjectInputStream<>(subject.create(experiment, new RnaSeqRequestContext(differentialRequestPreferences, experiment), getGeneIds))
                ),
                is(expected)
        );
    }

    RnaSeqProfile profile(String id, String name,
                          DifferentialExpression expressionForG1_G2,
                          DifferentialExpression expressionForG1_G3) {
        RnaSeqProfile profile = new RnaSeqProfile(id, name);
        Optional.ofNullable(expressionForG1_G2).ifPresent(e ->
                profile.add(g1_g2, e)
        );
        Optional.ofNullable(expressionForG1_G3).ifPresent(e ->
                profile.add(g1_g3, e)
        );
        return profile;
    }

    @Test
    public void nullCases() {
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header
                ),
                Collections.emptySet(),
                ImmutableList.of()
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of()
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.05", "2.0", "NA", "NA"}
                ),
                ImmutableList.of("different_id"),
                ImmutableList.of()
        );
    }

    @Test
    public void emptyProfileCases() {
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "NA", "NA", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.05", "NA", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "NA", "2.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "NA", "-2.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );
    }

    @Test
    public void getDataNoCutoff() {
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "NA", "NA"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0), null))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0), null))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "NA", "NA", "0.03", "4.0"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("id_1", "name_1", null, new DifferentialExpression(0.03, 4.0)))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                Collections.emptySet(),
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0), new DifferentialExpression(0.03, 4.0)))
        );
        testCaseNoExpressionFilter(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "-2.0", "NA", "NA"}
                ),
                ImmutableList.of("id_1"),
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, -2.0), null))
        );
    }


    @Test
    public void getDataRegulation(){
        testCaseNoCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "-2.0", "0.03", "4.0"}
                ),
                Regulation.UP_DOWN,
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, -2.0), new DifferentialExpression(0.03, 4.0)))
        );
        testCaseNoCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "-2.0", "0.03", "4.0"}
                ),
                Regulation.UP,
                ImmutableList.of(profile("id_1", "name_1", null, new DifferentialExpression(0.03, 4.0)))
        );
        testCaseNoCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "-2.0", "0.03", "4.0"}
                ),
                Regulation.DOWN,
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, -2.0), null))
        );
    }

    @Test
    public void getDataFoldChangeCutoff(){
        testCaseFoldChangeCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                0.0,
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0), new DifferentialExpression(0.03, 4.0)))
        );
        testCaseFoldChangeCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                2.5,
                ImmutableList.of(profile("id_1", "name_1", null, new DifferentialExpression(0.03, 4.0)))
        );
        testCaseFoldChangeCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                4.5,
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );
    }

    @Test
    public void getDataPValueCutoff(){
        testCasePValueCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                1.0,
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0), new DifferentialExpression(0.03, 4.0)))
        );
        testCasePValueCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                0.02,
                ImmutableList.of(profile("id_1", "name_1", new DifferentialExpression(0.01, 2.0),null))
        );
        testCasePValueCutoff(
                ImmutableList.of(
                        header,
                        new String[]{"id_1", "name_1", "0.01", "2.0", "0.03", "4.0"}
                ),
                0.005,
                ImmutableList.of(profile("id_1", "name_1", null, null))
        );

    }

}
