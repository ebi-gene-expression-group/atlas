
package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {

    private static final String ARRAY_DESIGN_ACCESSIONS = "arrayDesignAccessions";
    private static final String PUBMEDID = "PUBMEDID";

    AssayGroup g1 = new AssayGroup("id", "assay 1","assay 2");
    AssayGroup g2 = new AssayGroup("test","assay 1");
    Contrast contrast = new Contrast("g1_g2", ARRAY_DESIGN_ACCESSIONS, g1, g2, "contrast");


    private MicroarrayExperiment subject;


    public static MicroarrayExperiment get(String accession, List<Contrast> contrasts,
                                    SortedSet<String> arrayDesignAccessions){
        return new MicroarrayExperiment(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, accession,
                new Date(), contrasts,
                "description", true, new Species("species", SpeciesProperties.UNKNOWN), arrayDesignAccessions,
                new TreeSet<String>(), mock(ExperimentDesign.class), Sets.newHashSet(PUBMEDID));
    }
    @Before
    public void setUp() throws Exception {
        subject = get("accession", ImmutableList.of(contrast), Sets.newTreeSet(Sets
                .newHashSet
                        (ARRAY_DESIGN_ACCESSIONS)));
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAY_DESIGN_ACCESSIONS));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }
}