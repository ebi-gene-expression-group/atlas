package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.testutils.MockExperiment.createDifferentialExperiment;

public class RnaSeqProfileStreamFactoryPickUpExpressionsByIndexTest {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String P_VAL_1 = "1";
    private static final String FOLD_CHANGE_1 = "0.474360080385946";

    private static final String P_VAL_2 = "0.5";
    private static final String FOLD_CHANGE_2 = "0.1337";

    private static final AssayGroup G1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
    private static final AssayGroup G2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");
    private static final AssayGroup G3 = new AssayGroup("g3", "run_31", "run_32");

    private static final Contrast G1_G2 = new Contrast("g1_g2", "", G1, G2, "first contrast");
    private static final Contrast G1_G3 = new Contrast("g1_g3", "", G1, G3, "second contrast");

    private Function<String[], RnaSeqProfile> goThroughTsvLineAndPickUpExpressionsByIndex;

    private DifferentialExperiment differentialExperiment =
            createDifferentialExperiment("accession", ImmutableList.of(G1_G2, G1_G3));

    @Before
    public void setUp() throws Exception {
        MockDataFileHub dataFileHub = MockDataFileHub.create();
        RnaSeqProfileStreamFactory.Impl subject = new RnaSeqProfileStreamFactory.Impl(dataFileHub);
        goThroughTsvLineAndPickUpExpressionsByIndex =
                subject.howToReadLine(differentialExperiment, x -> true).apply(
                "Gene ID\tGene Name\tg1_g2.p-value\tg1_g2.log2foldchange\tg1_g3.p-value\tg1_g3.log2foldchange"
                        .split("\t"));
    }

    @Test
    public void parseRightValues() {
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {ID, NAME, P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2})
                        .getExpression(G1_G3),
                is(new DifferentialExpression(0.5, 0.1337)));
    }

    @Test
    public void handleInfinity() {
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {ID, NAME, P_VAL_1, FOLD_CHANGE_1, P_VAL_2, "-Inf"})
                        .getExpression(G1_G3),
                is(new DifferentialExpression(0.5, Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void ignoreNAValues() {
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {ID, NAME, P_VAL_1, FOLD_CHANGE_1, "NA", "NA"}).getSpecificity(),
                is(1L));
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {ID, NAME, "NA", "NA", P_VAL_1, FOLD_CHANGE_1}).getSpecificity(),
                is(1L));
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {ID, NAME, P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2}).getSpecificity(),
                is(2L));
    }
}
