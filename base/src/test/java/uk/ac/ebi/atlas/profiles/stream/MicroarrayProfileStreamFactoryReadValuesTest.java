package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperimentTest;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayProfileStreamFactoryReadValuesTest {

    AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
    AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");
    AssayGroup g3 = new AssayGroup("g3", "run_31", "run_32");

    Contrast g1_g2 = new Contrast("g1_g2","", g1, g2, "first contrast");
    Contrast g1_g3 = new Contrast("g1_g3","", g1, g3, "second contrast");


    MockDataFileHub dataFileHub;

    CreatesProfilesFromTsvFiles.ProfileFromTsvLine profileFromTsvLine;

    MicroarrayExperiment experiment = MicroarrayExperimentTest.get(
            "accession", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, ImmutableList.of(g1_g2, g1_g3),
            ImmutableSortedSet.of("A-AFFY-1")
    );

    String id = "id";
    String name = "name";
    String designElement = "designElement";

    String P_VAL_1 = "1";
    String T_VAL_1 = "0.5";
    String FOLD_CHANGE_1 = "0.47";

    String P_VAL_2 = "1";
    String T_VAL_2 = "-0.5";
    String FOLD_CHANGE_2 = "-Inf";

    String[] TWO_CONTRASTS = new String[]{id, name, designElement, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, P_VAL_2, T_VAL_2, FOLD_CHANGE_2};

    MicroarrayProfileStreamFactory.Impl subject;

    @Before
    public void setUp() throws Exception {
        dataFileHub = new MockDataFileHub();
        subject = new MicroarrayProfileStreamFactory.Impl(dataFileHub);
        profileFromTsvLine = subject.howToReadLineStream(experiment, microarrayExpression -> true).apply(
                ("Gene ID\tGene Name\tDesign element\tg1_g2.p-value\tg1_g2.tstat\tg1_g2.log2foldchange" +
                        "\tg1_g3.p-value\tg1_g3.tstat\tg1_g3.log2foldchange")
                        .split("\t"));
    }

    @Test
    public void shouldReadValuesRight() {
        assertThat(
                profileFromTsvLine.apply(TWO_CONTRASTS).getExpression(g1_g2),
                Matchers.is(
                        new MicroarrayExpression(Double.parseDouble(P_VAL_1),
                        Double.parseDouble(FOLD_CHANGE_1), Double.parseDouble(T_VAL_1), g1_g2))
        );

        assertThat(
                profileFromTsvLine.apply(TWO_CONTRASTS).getExpression(g1_g3),
                Matchers.is(
                        new MicroarrayExpression(
                                Double.parseDouble(P_VAL_2),
                                Double.NEGATIVE_INFINITY,
                                Double.parseDouble(T_VAL_2), g1_g3)));
    }

    @Test
    public void skipNAValues(){
        assertThat(profileFromTsvLine.apply(TWO_CONTRASTS).getSpecificity(), is(2L));

        assertThat(
                profileFromTsvLine.apply(
                        new String[]{
                                id, name, designElement, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", T_VAL_2, FOLD_CHANGE_2})
                        .getSpecificity(),
                is(1L));

        assertThat(
                profileFromTsvLine.apply(
                        new String[]{id, name, designElement, P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", "NA", "NA"})
                        .getSpecificity(),
                is(1L));
    }

}
