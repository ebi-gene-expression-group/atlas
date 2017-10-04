package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperimentTest;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RnaSeqProfileStreamFactoryTest {


    AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
    AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");
    AssayGroup g3 = new AssayGroup("g3", "run_31", "run_32");

    Contrast g1_g2 = new Contrast("g1_g2","", g1, g2, "first contrast");
    Contrast g1_g3 = new Contrast("g1_g3","", g1, g3, "second contrast");


    MockDataFileHub dataFileHub;

    RnaSeqProfileStreamFactory.Impl subject;

    CreatesProfilesFromTsvFiles.ProfileFromTsvLine profileFromTsvLine;

    DifferentialExperiment differentialExperiment = DifferentialExperimentTest.mockExperiment(
            "accession", ImmutableList.of(g1_g2, g1_g3)
    );

    String id = "id";
    String name = "name";
    String P_VAL_1 = "1";
    String FOLD_CHANGE_1 = "0.474360080385946";

    String P_VAL_2 = "0.5";
    String FOLD_CHANGE_2 = "0.1337";

    @Before
    public void setUp() throws Exception {
        dataFileHub = new MockDataFileHub();
        subject = new RnaSeqProfileStreamFactory.Impl(dataFileHub);
        profileFromTsvLine = subject.howToReadLineStream(differentialExperiment, Predicates.alwaysTrue()).apply(
                "Gene ID\tGene Name\tg1_g2.p-value\tg1_g2.log2foldchange\tg1_g3.p-value\tg1_g3.log2foldchange".split("\t")
        );
    }

    @Test
    public void parseRightValues(){
        assertThat(profileFromTsvLine.apply(new String[]{id, name, P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2}).getExpression(g1_g3), Matchers.is(
                new DifferentialExpression(0.5, 0.1337)
        ));
    }

    @Test
    public void handleInfinity() {
        assertThat(profileFromTsvLine.apply(new String[]{id, name, P_VAL_1, FOLD_CHANGE_1, P_VAL_2, "-Inf"}).getExpression(g1_g3), Matchers.is(
                new DifferentialExpression(0.5, Double.NEGATIVE_INFINITY)
        ));
    }

    @Test
    public void ignoreNAValues() throws Exception {

        assertThat(profileFromTsvLine.apply(new String[]{id, name,P_VAL_1, FOLD_CHANGE_1, "NA", "NA"}).getSpecificity(), is(1L));
        assertThat(profileFromTsvLine.apply(new String[]{id, name,"NA", "NA", P_VAL_1, FOLD_CHANGE_1}).getSpecificity(), is(1L));
        assertThat(profileFromTsvLine.apply(new String[]{id, name,P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2}).getSpecificity(), is(2L));
    }
}