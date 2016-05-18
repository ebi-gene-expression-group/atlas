
package uk.ac.ebi.atlas.model.differential.microarray;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {

    private static final String ARRAY_DESIGN_ACCESSIONS = "arrayDesignAccessions";
    private static final String PUBMEDID = "PUBMEDID";

    @Mock
    private Contrast contrastMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperiment subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperiment(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "accession", new Date(), Sets.newHashSet(contrastMock),
                "description", false, true, Sets.newHashSet("species"), "kingdom", "ensembl", Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN_ACCESSIONS)),
                false, Sets.newHashSet(PUBMEDID), experimentDesignMock);
    }

    @Test
    public void testGetArrayDesignAccessions() throws Exception {
        assertThat(subject.getArrayDesignAccessions(), hasItem(ARRAY_DESIGN_ACCESSIONS));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat(subject.getPubMedIds(), contains(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() throws Exception {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }
}