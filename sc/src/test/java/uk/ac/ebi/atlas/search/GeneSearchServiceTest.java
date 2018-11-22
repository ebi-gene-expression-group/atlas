package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.experimentpage.TsnePlotSettingsService;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomEnsemblGeneId;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GeneSearchServiceTest {
    @Mock
    private GeneSearchDao geneSearchDaoMock;

    @Mock
    private TsnePlotSettingsService tsnePlotSettingsServiceMock;

    private GeneSearchService subject;

    @BeforeEach
    void setUp() {
        subject = new GeneSearchService(geneSearchDaoMock, tsnePlotSettingsServiceMock);
    }

    @Test
    void returnsCellIdsPerExperiment() {
        String experimentAccession1 = generateRandomExperimentAccession();
        String experimentAccession2 = generateRandomExperimentAccession();
        String experimentAccession3 = generateRandomExperimentAccession();
        String geneId = generateRandomEnsemblGeneId();

        Map<String, List<String>> ensg00000104957Cells =
                    ImmutableMap.of(
                            experimentAccession1,
                            ImmutableList.of("cell_id_1", "cell_id_2", "cell_id_3", "cell_id_4", "cell_id_5"),
                            experimentAccession2,
                            ImmutableList.of("cell_id_6", "cell_id_7", "cell_id_8"),
                            experimentAccession3 ,
                            ImmutableList.of("cell_id_9", "cell_id_10"));

        when(geneSearchDaoMock.fetchCellIds(geneId)).thenReturn(ensg00000104957Cells);

        Map<String, Map<String, List<String>>> result = subject.getCellIdsInExperiments(geneId);

        assertThat(result)
                .containsOnlyKeys(geneId)
                .containsAllEntriesOf(ImmutableMap.of(geneId, ensg00000104957Cells));
    }

    @Test
    void cellIdsPerExperimentForMultipleGeneIds() {
        String experimentAccession1 = generateRandomExperimentAccession();
        String experimentAccession2 = generateRandomExperimentAccession();
        String experimentAccession3 = generateRandomExperimentAccession();
        String experimentAccession4 = generateRandomExperimentAccession();
        String experimentAccession5 = generateRandomExperimentAccession();
        String geneId1 = generateRandomEnsemblGeneId();
        String geneId2 = generateRandomEnsemblGeneId();

        Map<String, List<String>> ensfoobar1Cells = ImmutableMap.of(
                experimentAccession1,
                ImmutableList.of("cell_id_1", "cell_id_2", "cell_id_3", "cell_id_4", "cell_id_5"),
                experimentAccession2,
                ImmutableList.of("cell_id_6", "cell_id_7", "cell_id_8"),
                experimentAccession3,
                ImmutableList.of("cell_id_9", "cell_id_10"));

        Map<String, List<String>> ensfoobar2Cells = ImmutableMap.of(
                experimentAccession4, ImmutableList.of("cell_id_11", "cell_id_12", "cell_id_13"),
                experimentAccession5, ImmutableList.of("cell_id_14", "cell_id_15"));

        when(geneSearchDaoMock.fetchCellIds(geneId1)).thenReturn(ensfoobar1Cells);
        when(geneSearchDaoMock.fetchCellIds(geneId2)).thenReturn(ensfoobar2Cells);

        assertThat(subject.getCellIdsInExperiments(geneId1, geneId2))
                .containsAllEntriesOf(ImmutableMap.of(geneId1, ensfoobar1Cells, geneId2, ensfoobar2Cells));
    }

    @Test
    void markerGeneProfilesForOneGeneId() {
        String experimentAccession1 = generateRandomExperimentAccession();
        String experimentAccession2 = generateRandomExperimentAccession();
        String geneId = generateRandomEnsemblGeneId();

        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession1)).thenReturn(Optional.of(5));
        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession2)).thenReturn(Optional.of(10));

        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId, experimentAccession1, 5))
                .thenReturn(ImmutableMap.of(5, ImmutableList.of(1)));
        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId, experimentAccession2, 10))
                .thenReturn(ImmutableMap.of(10, ImmutableList.of(1)));

        when(geneSearchDaoMock
                .fetchExperimentAccessionsWhereGeneIsMarker(geneId))
                .thenReturn(ImmutableList.of(experimentAccession1, experimentAccession2));

        Map<String, Map<String, Map<Integer, List<Integer>>>> result = subject.getMarkerGeneProfile(geneId);

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys(geneId)
                .containsAllEntriesOf(ImmutableMap.of(geneId,
                        ImmutableMap.of(
                                experimentAccession1, ImmutableMap.of(5, ImmutableList.of(1)),
                                experimentAccession2, ImmutableMap.of(10, ImmutableList.of(1)))));
    }

    @Test
    void markerGeneProfilesForMultipleGeneIds() {
        String experimentAccession1 = generateRandomExperimentAccession();
        String experimentAccession2 = generateRandomExperimentAccession();
        String experimentAccession3 = generateRandomExperimentAccession();
        String experimentAccession4 = generateRandomExperimentAccession();
        String geneId1 = generateRandomEnsemblGeneId();
        String geneId2 = generateRandomEnsemblGeneId();

        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession1)).thenReturn(Optional.of(5));
        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession2)).thenReturn(Optional.of(10));

        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId1, experimentAccession1, 5))
                .thenReturn(ImmutableMap.of(5, ImmutableList.of(1)));
        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId1, experimentAccession2, 10))
                .thenReturn(ImmutableMap.of(10, ImmutableList.of(1)));

        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession3)).thenReturn(Optional.of(2));
        when(tsnePlotSettingsServiceMock.getExpectedClusters(experimentAccession4)).thenReturn(Optional.of(4));

        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId2, experimentAccession3, 2))
                .thenReturn(ImmutableMap.of(2, ImmutableList.of(1)));
        when(geneSearchDaoMock
                .fetchClusterIdsWithPreferredKAndMinPForExperimentAccession(geneId2, experimentAccession4, 4))
                .thenReturn(ImmutableMap.of(4, ImmutableList.of(1)));

        when(geneSearchDaoMock.fetchExperimentAccessionsWhereGeneIsMarker(geneId1))
                .thenReturn(ImmutableList.of(experimentAccession1, experimentAccession2));
        when(geneSearchDaoMock.fetchExperimentAccessionsWhereGeneIsMarker(geneId2))
                .thenReturn(ImmutableList.of(experimentAccession3, experimentAccession4));

        assertThat(subject.getMarkerGeneProfile(geneId1, geneId2))
                .containsAllEntriesOf(
                        ImmutableMap.of(
                                geneId1,
                                ImmutableMap.of(
                                        experimentAccession1, ImmutableMap.of(5, ImmutableList.of(1)),
                                        experimentAccession2, ImmutableMap.of(10, ImmutableList.of(1))),
                                geneId2,
                                ImmutableMap.of(
                                        experimentAccession3, ImmutableMap.of(2, ImmutableList.of(1)),
                                        experimentAccession4, ImmutableMap.of(4, ImmutableList.of(1)))));
    }

    @Test
    void returnsFacets() {
        String experimentAccession1 = generateRandomExperimentAccession();
        String experimentAccession2 = generateRandomExperimentAccession();

        when(geneSearchDaoMock.getFacets(anyList(), any(SingleCellAnalyticsSchemaField.class)))
                .thenReturn(ImmutableMap.of(
                        experimentAccession1, ImmutableMap.of(
                                "inferred_cell_type", Arrays.asList("neuron", "stem cell"),
                                "species", Collections.singletonList("homo sapiens")),
                        experimentAccession2, ImmutableMap.of(
                                "inferred_cell_type", Arrays.asList("immune cell", "liver cell", "stem cell"),
                                "organism_part", Collections.singletonList("liver"),
                                "species", Arrays.asList("homo sapiens", "mus musculus"))
                ));

        Map<String, Map<String, List<String>>> result = subject.getFacets(Arrays.asList("cell_id_1", "cell_id_2"));

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys(experimentAccession1, experimentAccession2);

        assertThat(result.get(experimentAccession1)).containsKeys("species");
        assertThat(result.get(experimentAccession2)).containsKeys("species");
    }

    @Test
    void exceptionsThrownInParallelTasksAreWrapped() {
        doThrow(new UncheckedIOException(new IOException())).when(geneSearchDaoMock).fetchCellIds(anyString());

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(
                () -> subject.getCellIdsInExperiments(generateRandomExperimentAccession()))
                .withCauseInstanceOf(ExecutionException.class);
    }
}
