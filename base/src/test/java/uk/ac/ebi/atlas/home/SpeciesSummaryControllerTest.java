package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.model.card.UrlHelpers;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;

@ExtendWith(MockitoExtension.class)
class SpeciesSummaryControllerTest {
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

    @Mock
    private SpeciesSummaryDao speciesSummaryDaoMock;

    private SpeciesSummaryController subject;

    @BeforeEach
    void setUp() {
        subject = new SpeciesSummaryController(speciesSummaryDaoMock, new CardModelFactory(urlHelpersImpl)) {
            @Override
            public String getPopularExperimentsGroupedByKingdom(int limit) {
                return super.getPopularExperimentsGroupedByKingdom(limit);
            }
        };
    }

    @Test
    void animalsBeforePlantsBeforeFungiBeforeProtistsBeforeAnythingElse() {
        ImmutableList<String> prescribedKingdoms = ImmutableList.of("animals", "plants", "fungi", "protists");
        ImmutableList<String> otherKingdoms =
                IntStream.range(0, RNG.nextInt(1, 10)).boxed()
                        .map(__ -> randomAlphabetic(5, 15))
                        .collect(toImmutableList());

        ArrayList<String> allKingdoms = new ArrayList<>();
        allKingdoms.addAll(prescribedKingdoms);
        allKingdoms.addAll(otherKingdoms);
        Collections.shuffle(allKingdoms);

        ImmutableList<SpeciesSummary> daoResults =
                allKingdoms.stream()
                        .map(kingdom ->
                                SpeciesSummary.create(generateRandomSpecies().getName(), kingdom, RNG.nextInt(1, 100)))
                        .collect(toImmutableList());
        when(speciesSummaryDaoMock.getExperimentCountBySpecies()).thenReturn(daoResults);

        ReadContext ctx = JsonPath.parse(subject.getPopularExperimentsGroupedByKingdom(1));
        List<String> kingdoms = ctx.read("$.speciesSummary[*].kingdom");
        assertThat(kingdoms)
                .containsExactlyElementsOf(
                        ImmutableList.<String>builder()
                                .addAll(prescribedKingdoms)
                                .addAll(otherKingdoms.stream().sorted().collect(toImmutableList()))
                                .build());
    }
}
