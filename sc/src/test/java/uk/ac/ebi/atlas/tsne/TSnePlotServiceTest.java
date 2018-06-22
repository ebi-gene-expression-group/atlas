package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TSnePlotServiceTest {
    private static final int NUMBER_OF_CELLS = 10000;

    @Mock
    private TSnePlotServiceDao tSnePlotServiceDaoMock;

    private TSnePlotService subject;

    @BeforeEach
    void setUp() {
        subject = new TSnePlotService(tSnePlotServiceDaoMock);
    }

    @Test
    @DisplayName("Points retrieved by the DAO class are assigned the right cluster")
    void testFetchPlotWithClusters() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        int[] perplexities = new int[] {1, 5, 10, 15, 20};
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(0, perplexities.length)];
        int k = ThreadLocalRandom.current().nextInt(5, 20);

        Set<TSnePoint.Dto> randomPointDtos = RandomDataTestUtils.randomTSnePointDtosWithClusters(NUMBER_OF_CELLS, k);
        when(tSnePlotServiceDaoMock.fetchTSnePlotWithClusters(experimentAccession, perplexity, k))
                .thenReturn(ImmutableList.copyOf(randomPointDtos));

        Map<Integer, Set<TSnePoint>> results = subject.fetchTSnePlotWithClusters(experimentAccession, perplexity, k);

        for (TSnePoint.Dto tSnePointDto : randomPointDtos) {
            assertThat(results.get(tSnePointDto.clusterId()))
                    .contains(TSnePoint.create(tSnePointDto.x(), tSnePointDto.y(), tSnePointDto.name()));
        }

        assertThat(results)
                .containsOnlyKeys(
                        randomPointDtos.stream()
                                .collect(groupingBy(TSnePoint.Dto::clusterId))
                                .keySet()
                                .toArray(new Integer[0]));

        assertThat(results.values().stream().flatMap(Set::stream).collect(toSet()))
                .containsExactlyInAnyOrder(
                        randomPointDtos.stream()
                                .map(dto -> TSnePoint.create(dto.x(), dto.y(), dto.name()))
                                .toArray(TSnePoint[]::new))
                .extracting("expressionLevel")
                .allMatch(expressionLevel -> expressionLevel == Optional.empty());


    }

    @Test
    @DisplayName("Points DTOs retrieved by the DAO class are correctly transformed to their non-DTO counterparts")
    void testFetchPlotWithExpressionLevels() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        int[] perplexities = new int[] {1, 5, 10, 15, 20};
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(0, perplexities.length)];
        String geneId = RandomDataTestUtils.getRandomEnsemblGeneId();

        Set<TSnePoint.Dto> randomPointDtos = RandomDataTestUtils.randomTSnePointDtosWithExpression(NUMBER_OF_CELLS);
        when(tSnePlotServiceDaoMock.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId))
                .thenReturn(ImmutableList.copyOf(randomPointDtos));

        Set<TSnePoint> results = subject.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId);

        assertThat(results)
                .containsExactlyInAnyOrder(
                        randomPointDtos.stream()
                                .map(dto -> TSnePoint.create(dto.x(), dto.y(), dto.expressionLevel(), dto.name()))
                                .toArray(TSnePoint[]::new));
    }
}