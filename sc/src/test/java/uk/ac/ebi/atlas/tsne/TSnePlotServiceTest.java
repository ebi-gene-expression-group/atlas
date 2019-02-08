package uk.ac.ebi.atlas.tsne;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math.util.MathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.experimentpage.tsne.TSnePoint;
import uk.ac.ebi.atlas.metadata.CellMetadataDao;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TSnePlotServiceTest {
    private static final int NUMBER_OF_CELLS = 10000;

    @Mock
    private TSnePlotDao tSnePlotDaoMock;
    @Mock
    private CellMetadataDao cellMetadataDaoMock;

    private TSnePlotService subject;

    @BeforeEach
    void setUp() {
        subject = new TSnePlotService(tSnePlotDaoMock, cellMetadataDaoMock);
    }

    @Test
    @DisplayName("Points retrieved by the DAO class are assigned the right cluster")
    void testFetchPlotWithClusters() {
        String experimentAccession = RandomDataTestUtils.generateRandomExperimentAccession();
        int[] perplexities = new int[] {1, 5, 10, 15, 20};
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(0, perplexities.length)];
        int k = ThreadLocalRandom.current().nextInt(5, 20);

        Set<TSnePoint.Dto> randomPointDtos = RandomDataTestUtils.generateRandomTSnePointDtosWithClusters(NUMBER_OF_CELLS, k);
        when(tSnePlotDaoMock.fetchTSnePlotWithClusters(experimentAccession, perplexity, k))
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
    @DisplayName("Points retrieved by the DAO class are correctly grouped according to metadata values")
    void testFetchPlotWithMetadata() {
        String experimentAccession = RandomDataTestUtils.generateRandomExperimentAccession();
        int[] perplexities = new int[] {1, 5, 10, 15, 20};
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(0, perplexities.length)];
        String metadataCategory = "characteristic_inferred_cell_type";
        List<String> metadataValues = Arrays.asList("neuron", "stem cell", "B cell");

        Set<TSnePoint.Dto> randomPointDtos = RandomDataTestUtils.generateRandomTSnePointDtos(NUMBER_OF_CELLS);
        when(tSnePlotDaoMock.fetchTSnePlotForPerplexity(experimentAccession, perplexity))
                .thenReturn(ImmutableList.copyOf(randomPointDtos));

        // Extract list of cell IDs from t-SNE points
        List<String> cellIds = randomPointDtos
                .stream()
                .map(TSnePoint.Dto::name)
                .collect(Collectors.toList());

        assertThat(cellIds).doesNotHaveDuplicates();

        // Assign random metadata value to each cell ID
        Map<String, String> cellMetadata = cellIds
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        value -> metadataValues.get(ThreadLocalRandom.current().nextInt(0, metadataValues.size()))));

        when(
                cellMetadataDaoMock.getMetadataValueForCellIds(
                        eq(experimentAccession), any(SingleCellAnalyticsSchemaField.class), eq(cellIds)))
                .thenReturn(cellMetadata);

        Map<String, Set<TSnePoint>> results =
                subject.fetchTSnePlotWithMetadata(experimentAccession, perplexity, metadataCategory);

        assertThat(results)
                .containsOnlyKeys(metadataValues.stream().map(StringUtils::capitalize).toArray(String[]::new));
    }

    @Test
    @DisplayName("Points DTOs retrieved by the DAO class are correctly transformed to their non-DTO counterparts")
    void testFetchPlotWithExpressionLevels() {
        String experimentAccession = RandomDataTestUtils.generateRandomExperimentAccession();
        int[] perplexities = new int[] {1, 5, 10, 15, 20};
        int perplexity = perplexities[ThreadLocalRandom.current().nextInt(0, perplexities.length)];
        String geneId = RandomDataTestUtils.generateRandomEnsemblGeneId();

        Set<TSnePoint.Dto> randomPointDtos = RandomDataTestUtils.generateRandomTSnePointDtosWithExpression(NUMBER_OF_CELLS);
        when(tSnePlotDaoMock.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId))
                .thenReturn(ImmutableList.copyOf(randomPointDtos));

        Set<TSnePoint> results = subject.fetchTSnePlotWithExpression(experimentAccession, perplexity, geneId);

        assertThat(results)
                .containsExactlyInAnyOrder(
                        randomPointDtos.stream()
                                .map(dto -> TSnePoint.create(
                                        MathUtils.round(dto.x(), 2),
                                        MathUtils.round(dto.y(), 2),
                                        dto.expressionLevel(),
                                        dto.name()))
                                .toArray(TSnePoint[]::new));
    }
}
