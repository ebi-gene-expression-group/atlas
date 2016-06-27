
package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentBuilderTest2 {

    private static final String DISPLAY_NAME = "DISPLAY NAME";
    private static final String PUBMEDID = "PUBMEDID";
    private static final String SPECIES = "homo sapiens";
    private static final String RUN_ACCESSION1 = "ENS0";
    private static final String RUN_ACCESSION2 = "ENS1";
    private static final String DESCRIPTION = "aDescription";

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private FactorGroup factorGroupMock1;
    @Mock
    private FactorGroup factorGroupMock2;

    @Mock
    private Factor factorMock1;
    @Mock
    private Factor factorMock2;

    @Mock
    private ExperimentalFactorsBuilder experimentalFactorsBuilderMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private BaselineExperiment subject;

    @Mock
    private AssayGroups assayGroupsMock;

    @Before
    public void initializeSubject() {

        Map<String, FactorGroup> orderedFactorGroupsByAssayGroup = Maps.newLinkedHashMap();
        orderedFactorGroupsByAssayGroup.put("g1", factorGroupMock1);
        orderedFactorGroupsByAssayGroup.put("g2", factorGroupMock2);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet("g1", "g2"));

        subject = new BaselineExperimentBuilder()
                .forSpecies(SPECIES)
                .withDescription(DESCRIPTION)
                .withSpeciesMapping(Collections.EMPTY_MAP)
                .withDisplayName(DISPLAY_NAME)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
                .withAssayGroups(assayGroupsMock)
                .withExperimentalFactors(experimentalFactorsMock)
                .create();

    }

    @Test
    public void testExperimentRunAccessions() {
        assertThat(subject.getExperimentRunAccessions(), containsInAnyOrder(RUN_ACCESSION1, RUN_ACCESSION2));
    }

    @Test
    public void testDescription() {
        assertThat(subject.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testSpecies() {
        assertThat(subject.getSpecies(), is(SPECIES));
    }

    @Test
    public void testPubMedIds() {
        assertThat(subject.getPubMedIds(), hasItem(PUBMEDID));
    }

    @Test
    public void testGetExperimentDesign() {
        assertThat(subject.getExperimentDesign(), is(experimentDesignMock));
    }

    @Test
    public void testSpeciesMapping() {
        assertThat(subject.getSpeciesToEnsemblMapping(), is(Collections.EMPTY_MAP));
    }

    @Test
    public void testFilteredFactorValues() {
        //given
        Set<Factor> filteredFactors = Sets.newHashSet(factorMock1, factorMock2);
        //when
        ExperimentalFactors experimentalFactors = subject.getExperimentalFactors();
        experimentalFactors.getComplementFactors(filteredFactors);

        //then
        verify(experimentalFactorsMock).getComplementFactors(filteredFactors);
        assertThat(subject.getExperimentalFactors().getMenuFilterFactorNames(), is(Collections.EMPTY_SET));
    }

    @Test
    public void testGetDisplayName() {
        assertThat(subject.getDisplayName(), is(DISPLAY_NAME));
    }

}
