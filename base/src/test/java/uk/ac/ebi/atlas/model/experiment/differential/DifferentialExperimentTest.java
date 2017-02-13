package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentTest {

    private static final String PUBMEDID = "PUBMEDID";

    private DifferentialExperiment subject;

    AssayGroup referenceAssay1 = new AssayGroup("g1", "assay1");
    AssayGroup testAssay1 = new AssayGroup("g2", "assay2");
    AssayGroup referenceAssay2 = new AssayGroup("g3", "assay3");
    AssayGroup testAssay2 = new AssayGroup("g4", "assay41", "assay42");

    Contrast c1 = new Contrast("g1_g2", null, referenceAssay1, testAssay1, "first contrast");
    Contrast c2 = new Contrast("g3_g4", null, referenceAssay2, testAssay2, "second contrast");

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Before
    public void setUp() throws Exception {


        subject = new DifferentialExperiment("accession", new Date(), ImmutableList.of(c1, c2),
                "description", true, new Species("species", SpeciesProperties.UNKNOWN), Sets.newHashSet(PUBMEDID), experimentDesignMock);
    }

    @Test
    public void testGetContrasts() throws Exception {
        assertThat(subject.getDataColumnDescriptors(), hasItems(c1, c2));
    }

    @Test
    public void testGetContrast() throws Exception {
        assertThat(subject.getDataColumnDescriptor("g1_g2"), is(c1));
        assertThat(subject.getDataColumnDescriptor("g3_g4"), is(c2));
    }

    @Test
    public void testGetAssayAccessions() throws Exception {
        assertThat(subject.getAnalysedRowsAccessions(), hasItems("assay1", "assay2", "assay3","assay41", "assay42"));
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