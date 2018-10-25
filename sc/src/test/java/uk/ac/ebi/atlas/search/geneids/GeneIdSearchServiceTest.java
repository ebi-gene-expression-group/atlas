package uk.ac.ebi.atlas.search.geneids;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.ID_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.solr.cloud.collections.BioentitiesCollectionProxy.SPECIES_OVERRIDE_PROPERTY_NAMES;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomKnownBioentityPropertyName;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GeneIdSearchServiceTest {
    private static final Species HUMAN =
            new Species(
                    "Homo sapiens",
                    SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of()));

    @Mock
    private GeneIdSearchDao geneIdSearchDao;

    private InOrder inOrder;

    private GeneIdSearchService subject;

    @BeforeEach
    void setUp() {
        subject = new GeneIdSearchService(geneIdSearchDao);
        inOrder = inOrder(geneIdSearchDao);
    }

    @Test
    void geneQueryWithoutCategoryIsSearchedInIdProperties() {
        subject.search(GeneQuery.create("foobar"));
        subject.search(GeneQuery.create("foobar", HUMAN));

        ID_PROPERTY_NAMES.forEach(propertyName ->
                inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name));

        ID_PROPERTY_NAMES.forEach(propertyName ->
                inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()));
    }

    @Test
    void speciesSpecificCategoriesIgnoreSpecies() {
        SPECIES_OVERRIDE_PROPERTY_NAMES.forEach(propertyName -> {
            subject.search(GeneQuery.create("foobar", propertyName, HUMAN));
            inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name);
        });

        SPECIES_OVERRIDE_PROPERTY_NAMES.forEach(propertyName ->
            verify(geneIdSearchDao, never()).searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()));
    }

    @Test
    void multiSpeciesCategoriesHonourSpecies() {
        BioentityPropertyName propertyName = generateRandomKnownBioentityPropertyName();
        while (SPECIES_OVERRIDE_PROPERTY_NAMES.contains(propertyName)) {
            propertyName = generateRandomKnownBioentityPropertyName();
        }

        subject.search(GeneQuery.create("foobar", propertyName, HUMAN));
        verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName());
    }

    @Test
    void ifNoIdMatchesWeGetEmptyOptional() {
        assertThat(subject.search(GeneQuery.create("foobar", HUMAN)))
                .isEqualTo(subject.search(GeneQuery.create("foobar")))
                .isEmpty();
    }

    @Test
    void ifAtLeastOneIdMatchesWeGetNonEmptyOptional() {
        BioentityPropertyName randomIdPropertyName = generateRandomKnownBioentityPropertyName();
        while (!SPECIES_OVERRIDE_PROPERTY_NAMES.contains(randomIdPropertyName)) {
            randomIdPropertyName = generateRandomKnownBioentityPropertyName();
        }

        ID_PROPERTY_NAMES.forEach(propertyName -> {
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()))
                    .thenReturn(Optional.empty());
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name))
                    .thenReturn(Optional.empty());
        });

        when(geneIdSearchDao.searchGeneIds("foobar", randomIdPropertyName.name, HUMAN.getEnsemblName()))
                .thenReturn(Optional.of(ImmutableSet.of()));
        when(geneIdSearchDao.searchGeneIds("foobar", randomIdPropertyName.name))
                .thenReturn(Optional.of(ImmutableSet.of()));

        assertThat(subject.search(GeneQuery.create("foobar", HUMAN)))
                .hasValue(ImmutableSet.of());
        assertThat(subject.search(GeneQuery.create("foobar")))
                .hasValue(ImmutableSet.of());
    }

    @Test
    void resultsOfFirstIdThatMatchesAreReturned() {
        BioentityPropertyName randomIdPropertyName = generateRandomKnownBioentityPropertyName();
        while (!ID_PROPERTY_NAMES.contains(randomIdPropertyName)) {
            randomIdPropertyName = generateRandomKnownBioentityPropertyName();
        }

        ImmutableList<BioentityPropertyName> idPropertyNamesBefore =
                ID_PROPERTY_NAMES.subList(0, ID_PROPERTY_NAMES.indexOf(randomIdPropertyName));

        ImmutableList<BioentityPropertyName> idPropertyNamesAfter =
                ID_PROPERTY_NAMES.subList(
                        ID_PROPERTY_NAMES.indexOf(randomIdPropertyName) + 1, ID_PROPERTY_NAMES.size());

        idPropertyNamesBefore.forEach(propertyName -> {
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()))
                    .thenReturn(Optional.of(ImmutableSet.of()));
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name))
                    .thenReturn(Optional.of(ImmutableSet.of()));
        });

        idPropertyNamesAfter.forEach(propertyName -> {
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()))
                    .thenReturn(Optional.of(ImmutableSet.of("ENSFOOBAR0000002")));
            when(geneIdSearchDao.searchGeneIds("foobar", propertyName.name))
                    .thenReturn(Optional.of(ImmutableSet.of("ENSFOOBAR0000002")));
        });

        when(geneIdSearchDao.searchGeneIds("foobar", randomIdPropertyName.name, HUMAN.getEnsemblName()))
                .thenReturn(Optional.of(ImmutableSet.of("ENSFOOBAR0000001")));
        when(geneIdSearchDao.searchGeneIds("foobar", randomIdPropertyName.name))
                .thenReturn(Optional.of(ImmutableSet.of("ENSFOOBAR0000001")));

        assertThat(subject.search(GeneQuery.create("foobar", HUMAN)))
                .isEqualTo(subject.search(GeneQuery.create("foobar")))
                .hasValue(ImmutableSet.of("ENSFOOBAR0000001"));

        idPropertyNamesBefore.forEach(propertyName ->
                inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName()));
        inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", randomIdPropertyName.name, HUMAN.getEnsemblName());

        idPropertyNamesBefore.forEach(propertyName ->
                inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", propertyName.name));
        inOrder.verify(geneIdSearchDao).searchGeneIds("foobar", randomIdPropertyName.name);

        idPropertyNamesAfter.forEach(propertyName -> {
            verify(geneIdSearchDao, never()).searchGeneIds("foobar", propertyName.name, HUMAN.getEnsemblName());
            verify(geneIdSearchDao, never()).searchGeneIds("foobar", propertyName.name);
        });
    }
}
