
package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Date;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {

    private static final String ARRAY_DESIGN_ACCESSIONS = "arrayDesignAccessions";
    private static final String PUBMEDID = "PUBMEDID";

    private Contrast contrast;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperiment subject;

    @Before
    public void setUp() throws Exception {

        contrast = mock(Contrast.class);
        when(contrast.getId()).thenReturn("contrast");

        when(contrast.getReferenceAssayGroup()).thenReturn(new AssayGroup("id", "assay 1","assay 2"));
        when(contrast.getTestAssayGroup()).thenReturn(new AssayGroup("test","assay 1"));

        subject = new MicroarrayExperiment(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "accession", new Date(), Sets.newHashSet(contrast),
                "description", true, new Species("uk/ac/ebi/atlas/species", "uk/ac/ebi/atlas/species", "kingdom", "ensembl"), Sets.newTreeSet(Sets
                .newHashSet
                (ARRAY_DESIGN_ACCESSIONS)),
                new TreeSet<String>(), experimentDesignMock, Sets.newHashSet(PUBMEDID));
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAY_DESIGN_ACCESSIONS));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() throws Exception {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }
}