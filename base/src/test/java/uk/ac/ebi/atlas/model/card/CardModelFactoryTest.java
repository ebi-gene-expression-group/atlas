package uk.ac.ebi.atlas.model.card;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.home.SpeciesSummary;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CardModelFactoryTest {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private UrlHelpers urlHelpersImpl = new UrlHelpers() {
        @Override
        public String getExperimentsFilteredBySpeciesUrl(String species) {
            return "http://stubbed-species-url/experiments?species=" + species;
        }
        @Override
        public String getExperimentsFilteredBySpeciesAndExperimentType(String species, String type) {
            return "http://stubbed-species-url/experiments?species=" + species + "experimentType=" + type;
        }
        @Override
        public String getExperimentUrl(Experiment experiment) {
            return "http://stubbed-experiment-url/experiments/" + experiment.getAccession();
        }
    };

    private CardModelFactory subject;

    @BeforeEach
    void setUp() {
        subject = new CardModelFactory(urlHelpersImpl);
    }

    @Test
    void createPopularSpeciesCard() {
        Species species = generateRandomSpecies();

        SpeciesSummary someWeirdSpeciesInfo =
                SpeciesSummary.create(
                        species.getName(),
                        "Dorne",
                        RNG.nextInt(1, 1000),
                        RNG.nextInt(1, 1000));

        assertThat(subject.create(someWeirdSpeciesInfo))
                .extracting("iconType", "iconSrc")
                .containsOnly(CardIconType.SPECIES, species.getName());

        assertThat(subject.create(someWeirdSpeciesInfo).content())
                .hasSize(3);
    }

    @Test
    void experimentWordIsPluralisedInSpeciesCards() {
        assertThat(
                subject.create(
                        SpeciesSummary.create(
                                randomAlphabetic(10),
                                randomAlphabetic(10),
                                0,
                                1))
                        .content().get(0).getLeft())
                .endsWith("experiment");

        assertThat(
                subject.create(
                        SpeciesSummary.create(
                                randomAlphabetic(10),
                                randomAlphabetic(10),
                                1))
                        .content().get(0).getLeft())
                .endsWith("experiment");


        assertThat(
                subject.create(
                        SpeciesSummary.create(
                                randomAlphabetic(10),
                                randomAlphabetic(10),
                                1,
                                1)).content().get(0).getLeft())
                .endsWith("experiments");

        assertThat(
                subject.create(
                        SpeciesSummary.create(
                                randomAlphabetic(10),
                                randomAlphabetic(10),
                                RNG.nextInt(2, Integer.MAX_VALUE)))
                        .content().get(0).getLeft())
                .endsWith("experiments");

    }

    @Test
    void createLandingPageSpeciesCard() {
        Collection<Experiment> experiments = mockExperiments(RNG.nextInt(1, 1000));
        Species species = experiments.stream().findAny().orElseThrow(RuntimeException::new).getSpecies();

        assertThat(subject.createLandingPageSpeciesCard(experiments))
                .extracting("iconType", "iconSrc")
                .containsOnly(CardIconType.SPECIES, species.getReferenceName());

        assertThat(subject.createLandingPageSpeciesCard(experiments).content())
                .hasSize(experiments.size());
    }

    @Test
    void createLandingPageImageCard() {
        Collection<Experiment> experiments = mockExperiments(RNG.nextInt(1, 1000));

        assertThat(subject.createLandingPageImageCard(experiments, "f", "o", "o"))
                .extracting("iconType", "iconSrc")
                .containsOnly(CardIconType.IMAGE, "f");

        assertThat(subject.createLandingPageImageCard(experiments, "b", "a", "r").content())
                .hasSize(experiments.size());
    }

    private ImmutableSet<Experiment> mockExperiments(int count) {
        Set<String> experimentAccessions = new HashSet<>(count);
        while (experimentAccessions.size() < count) {
            experimentAccessions.add(generateRandomExperimentAccession());
        }

        Species species = generateRandomSpecies();

        return experimentAccessions
                .stream()
                .map(accession -> {
                    Experiment experiment = mock(Experiment.class);
                    when(experiment.getAccession()).thenReturn(accession);
                    when(experiment.getSpecies()).thenReturn(species);
                    return experiment;
                })
                .collect(toImmutableSet());
    }
}