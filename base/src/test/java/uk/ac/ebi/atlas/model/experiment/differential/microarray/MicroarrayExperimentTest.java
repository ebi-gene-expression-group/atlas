
package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

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

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperiment subject;

    @Before
    public void setUp() throws Exception {

        Contrast contrast = mock(Contrast.class);
        when(contrast.getId()).thenReturn("contrast");

        when(contrast.getReferenceAssayGroup()).thenReturn(new AssayGroup("id", "assay 1","assay 2"));
        when(contrast.getTestAssayGroup()).thenReturn(new AssayGroup("test","assay 1"));

        subject = new MicroarrayExperiment(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "accession", new Date
                (), ImmutableList.of(contrast),
                "description", true, new Species("species", SpeciesProperties.UNKNOWN), Sets.newTreeSet(Sets
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