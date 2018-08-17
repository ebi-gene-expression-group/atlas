package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeaturedSpeciesServiceTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule().silent();

    @Mock
    private FeaturedSpeciesDao featuredSpeciesDaoMock;

    @Mock
    private SpeciesFactory speciesFactoryMock;

    @Mock
    private Species mouseMock;

    @Mock
    private Species humanMock;

    @Mock
    private Species dogMock;

    private FeaturedSpeciesService subject;

    @Before
    public void setUp() {
        when(mouseMock.getReferenceName()).thenReturn("mus musculus");
        when(humanMock.getReferenceName()).thenReturn("homo sapiens");
        when(dogMock.getReferenceName()).thenReturn("canis familiaris");

        when(speciesFactoryMock.create("mus musculus")).thenReturn(mouseMock);
        when(speciesFactoryMock.create("Mus musculus")).thenReturn(mouseMock);
        when(speciesFactoryMock.create("Homo sapiens")).thenReturn(humanMock);
        when(speciesFactoryMock.create("Homo_Sapiens")).thenReturn(humanMock);
        when(speciesFactoryMock.create("canis_lupus")).thenReturn(dogMock);
        when(speciesFactoryMock.create("canis_familiaris")).thenReturn(dogMock);
        when(speciesFactoryMock.create("Canis_lupus familiaris")).thenReturn(dogMock);

        subject = new FeaturedSpeciesService(featuredSpeciesDaoMock, speciesFactoryMock);
    }

    @Test
    public void aggregatesAlternateSpellingsAndCapitalizes() {
        when(featuredSpeciesDaoMock.fetchSpeciesSortedByExperimentCount())
                .thenReturn(ImmutableList.of(
                        "mus musculus", "Mus musculus",
                        "Homo sapiens", "Homo_Sapiens",
                        "canis_lupus", "canis_familiaris", "Canis_lupus familiaris"));

        assertThat(subject.getSpeciesNamesSortedByExperimentCount())
                .containsExactly("Mus musculus", "Homo sapiens", "Canis familiaris");
    }

    @Test
    public void emptyInEmptyOut() {
        when(featuredSpeciesDaoMock.fetchSpeciesSortedByExperimentCount()).thenReturn(ImmutableList.of());
        assertThat(subject.getSpeciesNamesSortedByExperimentCount())
                .isEmpty();
    }
}
