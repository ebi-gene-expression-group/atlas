package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTest;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentTest {

    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String PUBMEDID = "PUBMEDID";

    static List<AssayGroup> assayGroups = ImmutableList.of(
            new AssayGroup("g1", RUN_ACCESSION1),
            new AssayGroup("g2", RUN_ACCESSION2));


    private BaselineExperiment subject;

    @Before
    public void setUp() throws Exception {



        subject = mockExperiment(assayGroups, "accession");

    }

    public static BaselineExperiment mockExperiment(){
        return mockExperiment("accession");
    }

    public static BaselineExperiment mockExperiment(String accession){

        return mockExperiment(assayGroups, accession);
    }

    public static BaselineExperiment mockExperiment(List<AssayGroup> assayGroups, String accession){

        return mockExperiment(ExperimentDesignTest.mockExperimentDesign(assayGroups), assayGroups,ExperimentDisplayDefaults.simpleDefaults(), accession);
    }

    public static BaselineExperiment mockExperiment(ExperimentDesign experimentDesign,
                                                    List<AssayGroup> assayGroups,
                                                    ExperimentDisplayDefaults experimentDisplayDefaults,
                                                    String accession){
        return new BaselineExperiment(ExperimentType.RNASEQ_MRNA_BASELINE,accession, new Date(),
                "description", "displayName", "",
                new Species("species",
                        SpeciesProperties.create(
                                "ensemblName", "defaulQueryFactorType",
                                "kingdom", ImmutableList.<ImmutableMap<String, String>>of())),
                Sets.newHashSet(PUBMEDID), experimentDesign, assayGroups, Collections.<String>emptyList(),
                Collections.<String>emptyList(), Collections.<String>emptyList(), new ArrayList<String>(), experimentDisplayDefaults);
    }

    @Test
    public void testGetExperimentRunAccessions() throws Exception {
        assertThat(subject.getAnalysedAssays(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void orderOfAssayGroupsIsPreserved(){
        int num = (int) Math.round(Math.random()*10000);
        List<AssayGroup> assayGroups = new ArrayList<>(num);
        for(int i = 0 ; i< num; i++){
            assayGroups.add(new AssayGroup("id_"+i, "assay_"+i));
        }
        assertThat(mockExperiment(assayGroups, "accession").getDataColumnDescriptors(), is(assayGroups));
    }
}