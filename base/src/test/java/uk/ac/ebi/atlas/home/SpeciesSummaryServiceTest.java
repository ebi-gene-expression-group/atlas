package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.species.Species;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;

@ExtendWith(MockitoExtension.class)
class SpeciesSummaryServiceTest {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Mock
    private SpeciesSummaryDao speciesSummaryDaoMock;

    private SpeciesSummaryService subject;

    @BeforeEach
    void setUp() {
        subject = new SpeciesSummaryService(speciesSummaryDaoMock);
    }

    @Test
    void mapsSpeciesSummariesToKingdom() {
        Species species = generateRandomSpecies();
        int baselineExperimentsCount = RNG.nextInt(0, 100);
        int differentialExperimentsCount = RNG.nextInt(baselineExperimentsCount == 0 ? 1 : 0, 100);
        SpeciesSummary speciesSummary = SpeciesSummary.create(
                species.getName(),
                species.getKingdom(),
                baselineExperimentsCount,
                differentialExperimentsCount);
        when(speciesSummaryDaoMock.getExperimentCountBySpecies())
                .thenReturn(ImmutableList.of(speciesSummary));

        assertThat(subject.getSpeciesSummaryGroupedByKingdom(10))
                .containsAllEntriesOf(
                        ImmutableMap.of(species.getKingdom(), ImmutableList.of(speciesSummary)));
    }
}
