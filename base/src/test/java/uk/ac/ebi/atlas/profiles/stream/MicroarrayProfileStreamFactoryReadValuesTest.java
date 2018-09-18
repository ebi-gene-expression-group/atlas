package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.arraydesign.ArrayDesign;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentTest;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayProfileStreamFactoryReadValuesTest {
    private static final AssayGroup G1 = AssayGroupFactory.create("g1", "run_11", "run_12", "run_13");
    private static final AssayGroup G2 = AssayGroupFactory.create("g2", "run_21", "run_22", "run_23", "run_24");
    private static final AssayGroup G3 = AssayGroupFactory.create("g3", "run_31", "run_32");

    private static final Contrast G1_G2 = new Contrast("g1_g2", "", G1, G2, "first contrast");
    private static final Contrast G1_G3 = new Contrast("g1_g3", "", G1, G3, "second contrast");

    private MockDataFileHub dataFileHub;

    private Function<String[], MicroarrayProfile> goThroughTsvLineAndPickUpExpressionsByIndex;

    private MicroarrayExperiment experiment =
            MicroarrayExperimentTest.get(
                    "accession",
                    ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                    ImmutableList.of(G1_G2, G1_G3),
                    ImmutableList.of(ArrayDesign.createForUnknownName("array-design-accession")));

    private static final String ID = "ID";
    private static final String NAME = "name";
    private static final String DESIGN_ELEMENT = "DESIGN_ELEMENT";

    private static final String P_VAL_1 = "1";
    private static final String T_VAL_1 = "0.5";
    private static final String FOLD_CHANGE_1 = "0.47";

    private static final String P_VAL_2 = "1";
    private static final String T_VAL_2 = "-0.5";
    private static final String FOLD_CHANGE_2 = "-Inf";

    private static final String[] TWO_CONTRASTS =
            new String[]{ID, NAME, DESIGN_ELEMENT, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, P_VAL_2, T_VAL_2, FOLD_CHANGE_2};

    private MicroarrayProfileStreamFactory.Impl subject;

    @Before
    public void setUp() throws Exception {
        dataFileHub = MockDataFileHub.create();
        subject = new MicroarrayProfileStreamFactory.Impl(dataFileHub);
        goThroughTsvLineAndPickUpExpressionsByIndex =
                subject
                        .howToReadLine(experiment, microarrayExpression -> true)
                        .apply(
                                ("Gene ID\tGene Name\tDesign element\tg1_g2.p-value\tg1_g2.tstat\t" +
                                        "g1_g2.log2foldchange\tg1_g3.p-value\tg1_g3.tstat\tg1_g3.log2foldchange")
                                        .split("\t"));
    }

    @Test
    public void shouldReadValuesRight() {
        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(TWO_CONTRASTS).getExpression(G1_G2),
                Matchers.is(
                        new MicroarrayExpression(Double.parseDouble(P_VAL_1),
                        Double.parseDouble(FOLD_CHANGE_1), Double.parseDouble(T_VAL_1)))
        );

        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(TWO_CONTRASTS).getExpression(G1_G3),
                Matchers.is(
                        new MicroarrayExpression(
                                Double.parseDouble(P_VAL_2),
                                Double.NEGATIVE_INFINITY,
                                Double.parseDouble(T_VAL_2))));
    }

    @Test
    public void skipNAValues() {
        assertThat(goThroughTsvLineAndPickUpExpressionsByIndex.apply(TWO_CONTRASTS).getSpecificity(), is(2L));

        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[] {
                                ID, NAME, DESIGN_ELEMENT, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", T_VAL_2,
                                FOLD_CHANGE_2})
                        .getSpecificity(),
                is(1L));

        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[]{ID, NAME, DESIGN_ELEMENT, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", "NA", "NA"})
                        .getSpecificity(),
                is(1L));
    }

}
