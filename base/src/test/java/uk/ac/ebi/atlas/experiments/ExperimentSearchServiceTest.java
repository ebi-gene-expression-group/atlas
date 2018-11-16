package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExperimentSearchServiceTest {
    private static final String ACCESSION_PATTERN = randomAlphabetic(4).toUpperCase();
    private static final Species SPECIES = generateRandomSpecies();
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private static int GENERATED_EXPERIMENTS_COUNT = RNG.nextInt(100);
    private static int MATCH_EXPERIMENTS_COUNT = Math.max(1, GENERATED_EXPERIMENTS_COUNT / 10);

    @Mock
    private Species speciesMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    private ExperimentSearchService subject;

    @BeforeEach
    void setUp() {
        String speciesName = randomAlphabetic(20);
        when(speciesMock.getReferenceName()).thenReturn(speciesName);
        when(speciesMock.getName()).thenReturn(speciesName);

        // At most ten experiments with a pre-established pattern
        ImmutableSet<Experiment> mockedExperiments = IntStream
                .range(0, GENERATED_EXPERIMENTS_COUNT)
                .boxed()
                .map(i -> i < MATCH_EXPERIMENTS_COUNT ?
                        Pair.of(generateRandomExperimentAccession(ACCESSION_PATTERN), SPECIES) :
                        Pair.of(generateRandomExperimentAccession(), generateRandomSpecies()))
                .distinct()
                .map(accessionSpecies -> {
                    Experiment experiment = mock(Experiment.class);
                    when(experiment.getAccession()).thenReturn(accessionSpecies.getLeft());
                    when(experiment.getSpecies()).thenReturn(accessionSpecies.getRight());
                    return experiment;
                })
                .collect(toImmutableSet());

        when(experimentTraderMock.getPublicExperiments()).thenReturn(mockedExperiments);
        subject = new ExperimentSearchService(experimentTraderMock);
    }

    @Test
    void searchBySpecies() {
        assertThat(subject.searchPublicExperimentsBySpecies(SPECIES.getName()))
                .size().isBetween(1, MATCH_EXPERIMENTS_COUNT);
    }

    @Test
    void searchBySpeciesMatchesAnySpeciesNamingConvention() {
        assertThat(subject.searchPublicExperimentsBySpecies(SPECIES.getName()))
                .containsExactlyElementsOf(subject.searchPublicExperimentsBySpecies(SPECIES.getReferenceName()))
                .containsExactlyElementsOf(subject.searchPublicExperimentsBySpecies(SPECIES.getEnsemblName()))
                .size().isBetween(1, MATCH_EXPERIMENTS_COUNT);
    }

    @Test
    void searchBySpeciesIsCaseInsensitive() {
        assertThat(subject.searchPublicExperimentsBySpecies(SPECIES.getName()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getName().toUpperCase()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getName().toLowerCase()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getReferenceName().toUpperCase()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getReferenceName().toLowerCase()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getEnsemblName().toUpperCase()))
                .containsExactlyElementsOf(
                        subject.searchPublicExperimentsBySpecies(SPECIES.getEnsemblName().toLowerCase()))
                .size().isBetween(1, MATCH_EXPERIMENTS_COUNT);
    }

    @Test
    void returnsEmptyIfNoSpeciesMatches() {
        assertThat(subject.searchPublicExperimentsBySpecies(SPECIES.getReferenceName() + randomAlphabetic(1)))
                .isEmpty();
    }

    @Test
    void searchByAccession() {
        assertThat(subject.searchPublicExperimentsByAccession(ACCESSION_PATTERN))
                .size()
                .isBetween(1, MATCH_EXPERIMENTS_COUNT);

        // Loosening the pattern will match our prefabricated experiments, plus some others...
        String searchPattern = ACCESSION_PATTERN.substring(0, RNG.nextInt(1, ACCESSION_PATTERN.length()));
        assertThat(subject.searchPublicExperimentsByAccession(searchPattern))
                .size()
                .isGreaterThanOrEqualTo(MATCH_EXPERIMENTS_COUNT);
    }

    @Test
    void searchByAccessionIsCaseInsensitive() {
        assertThat(subject.searchPublicExperimentsByAccession(ACCESSION_PATTERN.toLowerCase()))
                .containsExactlyElementsOf(subject.searchPublicExperimentsByAccession(ACCESSION_PATTERN.toUpperCase()));

        // Loosening the pattern will match our prefabricated experiments, plus some others...
        String searchPattern = ACCESSION_PATTERN.substring(0, RNG.nextInt(1, ACCESSION_PATTERN.length()));
        assertThat(subject.searchPublicExperimentsByAccession(searchPattern.toLowerCase()))
                .containsExactlyElementsOf(subject.searchPublicExperimentsByAccession(searchPattern.toUpperCase()));
    }

    @Test
    void returnsEmptyIfNoAccessionMatches() {
        assertThat(subject.searchPublicExperimentsByAccession(ACCESSION_PATTERN + randomAlphabetic(1)))
                .isEmpty();
    }
}