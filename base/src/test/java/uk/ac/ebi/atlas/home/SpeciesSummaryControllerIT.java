package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.card.CardModelFactory;

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

// This test needs to be an IT with @WebAppConfiguration for UrlHelpers to have access to ServletRequestAttributes
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
class SpeciesSummaryControllerIT {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Mock
    private SpeciesSummaryDao speciesSummaryDaoMock;

    private SpeciesSummaryController subject;

    @BeforeEach
    void setUp() {
        subject = new SpeciesSummaryController(speciesSummaryDaoMock, new CardModelFactory()) {
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
