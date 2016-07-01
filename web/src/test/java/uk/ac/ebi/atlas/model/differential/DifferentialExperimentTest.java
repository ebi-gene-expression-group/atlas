
package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialExperimentTest {

    private static final String CONTRAST_ID1 = "a";
    private static final String CONTRAST_ID2 = "b";
    private static final String ASSAY_GROUP_1 = "assayGroup1";
    private static final String ASSAY_GROUP_2 = "assayGroup2";
    private static final String PUBMEDID = "PUBMEDID";

    private DifferentialExperiment subject;

    @Mock
    private Contrast contrastMock1;

    @Mock
    private Contrast contrastMock2;

    @Mock
    private AssayGroup assayGroupMock1;

    @Mock
    private AssayGroup assayGroupMock2;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Before
    public void setUp() throws Exception {

        when(assayGroupMock1.iterator()).thenReturn(Sets.newHashSet(ASSAY_GROUP_1).iterator());
        when(assayGroupMock2.iterator()).thenReturn(Sets.newHashSet(ASSAY_GROUP_2).iterator());

        when(contrastMock1.getDisplayName()).thenReturn(CONTRAST_ID1);
        when(contrastMock1.getId()).thenReturn(CONTRAST_ID1);
        when(contrastMock1.getReferenceAssayGroup()).thenReturn(assayGroupMock1);
        when(contrastMock1.getTestAssayGroup()).thenReturn(assayGroupMock2);

        when(contrastMock2.getDisplayName()).thenReturn(CONTRAST_ID2);
        when(contrastMock2.getId()).thenReturn(CONTRAST_ID2);
        when(contrastMock2.getReferenceAssayGroup()).thenReturn(assayGroupMock2);
        when(contrastMock2.getTestAssayGroup()).thenReturn(assayGroupMock1);

        subject = new DifferentialExperiment("accession", new Date(), Sets.newHashSet(contrastMock1, contrastMock2),
                "description", false, true, "species", "kingdom", "ensembl", Sets.newHashSet(PUBMEDID), experimentDesignMock);
    }

    @Test
    public void testGetContrasts() throws Exception {
        assertThat(subject.getContrasts(), hasItems(contrastMock1, contrastMock2));
    }

    @Test
    public void testGetContrast() throws Exception {
        assertThat(subject.getContrast(CONTRAST_ID1), is(contrastMock1));
        assertThat(subject.getContrast(CONTRAST_ID2), is(contrastMock2));
    }

    @Test
    public void testGetContrastIds() throws Exception {
        assertThat(subject.getContrastIds(), hasItems(CONTRAST_ID1, CONTRAST_ID2));
    }

    @Test
    public void testGetAssayAccessions() throws Exception {
        assertThat(subject.getAssayAccessions(), hasItems(ASSAY_GROUP_1, ASSAY_GROUP_2));
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