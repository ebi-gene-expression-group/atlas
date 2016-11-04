
package uk.ac.ebi.atlas.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentTest {

    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String PUBMEDID = "PUBMEDID";

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private ExperimentRun runMock1;

    @Mock
    private ExperimentRun runMock2;

    @Mock
    private FactorGroup factorGroupMock1;

    @Mock
    private FactorGroup factorGroupMock2;

    @Mock
    private Factor factorMock;

    @Mock
    private ExperimentDesign experimentDesignMock;
    
    @Mock
    private AssayGroups assayGroupsMock;

    private BaselineExperiment subject;

    @Before
    public void setUp() throws Exception {
        when(runMock1.getFactorGroup()).thenReturn(factorGroupMock1);
        when(runMock1.getAccession()).thenReturn(RUN_ACCESSION1);
        when(runMock2.getFactorGroup()).thenReturn(factorGroupMock2);
        when(runMock2.getAccession()).thenReturn(RUN_ACCESSION2);


        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet("g1", "g2"));

        subject = mockExperiment(experimentalFactorsMock, experimentDesignMock, assayGroupsMock);

    }


    public static BaselineExperiment mockExperiment(ExperimentalFactors experimentalFactors, ExperimentDesign
            experimentDesign, AssayGroups assayGroups){
            return new BaselineExperiment(ExperimentType.RNASEQ_MRNA_BASELINE,"accession", new Date(),
                    experimentalFactors,
                    "description", "displayName", "", new Species("species","species", "kingdom",
                    "ensembl"), true, Sets.newHashSet(PUBMEDID), experimentDesign, assayGroups, Collections.<String>emptyList(),
                    Collections.<String>emptyList(), Collections.<String>emptyList(), new ArrayList<String>());

    }

    public static BaselineExperiment mockExperiment(){
        return mockExperiment(Mockito.mock(ExperimentalFactors.class), Mockito.mock(ExperimentDesign.class),
                Mockito.mock(AssayGroups.class));
    }


    @Test
    public void testGetExperimentRunAccessions() throws Exception {
        assertThat(subject.getExperimentRunAccessions(), hasItems(RUN_ACCESSION1, RUN_ACCESSION2));
    }

    @Test
    public void testGetExperimentalFactors() throws Exception {
        assertThat(subject.getExperimentalFactors(), is(experimentalFactorsMock));
    }

    @Test
    public void testGetPubMedIds() throws Exception {
        assertThat((Iterable<String>) subject.getAttributes().get("pubMedIds"), contains(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() throws Exception {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }

    @Test
    public void isTissue(){
        when(experimentalFactorsMock.getDefaultQueryFactorType()).thenReturn("ORGANISM_PART");
        assertEquals(true, subject.isTissueExperiment());

        when(experimentalFactorsMock.getDefaultQueryFactorType()).thenReturn("CELL_LINE");
        assertEquals(false, subject.isTissueExperiment());
    }

}