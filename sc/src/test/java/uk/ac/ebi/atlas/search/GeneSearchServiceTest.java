package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneSearchServiceTest {
    @Mock
    private GeneSearchServiceDao geneSearchServiceDaoMock;

    private GeneSearchService subject;

    @Before
    public void setUp() {
        subject = new GeneSearchService(geneSearchServiceDaoMock);
    }

    @Test
    public void cellIdsPerExperimentForOneGeneId() {
        Map<String, List<String>> ensg00000104957Cells =
                ImmutableMap.of(
                        "E-MTAB-0000", ImmutableList.of("cell_id_1", "cell_id_2", "cell_id_3", "cell_id_4", "cell_id_5"),
                        "E-MTAB-0001", ImmutableList.of("cell_id_6", "cell_id_7", "cell_id_8"),
                        "E-MTAB-0002", ImmutableList.of("cell_id_9", "cell_id_10"));

        when(geneSearchServiceDaoMock.fetchCellIds("ENSG00000104957")).thenReturn(ensg00000104957Cells);

        Map<String, Map<String, List<String>>> result = subject.getCellIdsInExperiments("ENSG00000104957");

        assertThat(result)
                .containsOnlyKeys("ENSG00000104957")
                .containsAllEntriesOf(ImmutableMap.of("ENSG00000104957", ensg00000104957Cells));
    }

    @Test
    public void cellIdsPerExperimentForMultipleGeneIds() {
        Map<String, List<String>> ensfoobar1Cells = ImmutableMap.of(
                "E-MTAB-0000", ImmutableList.of("cell_id_1", "cell_id_2", "cell_id_3", "cell_id_4", "cell_id_5"),
                "E-MTAB-0001", ImmutableList.of("cell_id_6", "cell_id_7", "cell_id_8"),
                "E-MTAB-0002", ImmutableList.of("cell_id_9", "cell_id_10"));

        Map<String, List<String>> ensfoobar2Cells = ImmutableMap.of(
                "E-MTAB-0003", ImmutableList.of("cell_id_11", "cell_id_12", "cell_id_13"),
                "E-MTAB-0004", ImmutableList.of("cell_id_14", "cell_id_15"));

        when(geneSearchServiceDaoMock.fetchCellIds("ENSFOOBAR1")).thenReturn(ensfoobar1Cells);
        when(geneSearchServiceDaoMock.fetchCellIds("ENSFOOBAR2")).thenReturn(ensfoobar2Cells);

        assertThat(subject.getCellIdsInExperiments("ENSFOOBAR1", "ENSFOOBAR2"))
                .containsAllEntriesOf(ImmutableMap.of("ENSFOOBAR1", ensfoobar1Cells, "ENSFOOBAR2", ensfoobar2Cells));
    }

    @Test
    public void markerGeneProfilesForOneGeneId() {
        Map<String, Map<Integer, List<Integer>>> ensg00000104957Profiles =
                ImmutableMap.of(
                        "E-MTAB-0000",
                        ImmutableMap.of(
                                5, ImmutableList.of(1, 3)),
                        "E-MTAB-0001",
                        ImmutableMap.of(
                                10, ImmutableList.of(1, 5, 8),
                                12, ImmutableList.of(12)));

        when(geneSearchServiceDaoMock.fetchKAndClusterIds("ENSG00000104952")).thenReturn(ensg00000104957Profiles);

        Map<String, Map<String, Map<Integer, List<Integer>>>> result =
                subject.getMarkerGeneProfile("ENSG00000104952");

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("ENSG00000104952")
                .containsAllEntriesOf(ImmutableMap.of("ENSG00000104952", ensg00000104957Profiles));
    }

    @Test
    public void markerGeneProfilesForMultipleGeneIds() {
        Map<String, Map<Integer, List<Integer>>> ensfoobar1Profiles =
                ImmutableMap.of(
                        "E-MTAB-0000",
                        ImmutableMap.of(
                                5, ImmutableList.of(1, 3)),
                        "E-MTAB-0001",
                        ImmutableMap.of(
                                10, ImmutableList.of(1, 5, 8),
                                12, ImmutableList.of(12)));

        Map<String, Map<Integer, List<Integer>>> ensfoobar2Profiles =
                ImmutableMap.of(
                        "E-MTAB-0002",
                        ImmutableMap.of(
                                2, ImmutableList.of(1),
                                6, ImmutableList.of(5, 6)),
                        "E-MTAB-0003",
                        ImmutableMap.of(
                                4, ImmutableList.of(1, 2, 3)));


        when(geneSearchServiceDaoMock.fetchKAndClusterIds("ENSFOOBAR1")).thenReturn(ensfoobar1Profiles);
        when(geneSearchServiceDaoMock.fetchKAndClusterIds("ENSFOOBAR2")).thenReturn(ensfoobar2Profiles);

        assertThat(subject.getMarkerGeneProfile("ENSFOOBAR1", "ENSFOOBAR2"))
                .containsAllEntriesOf(ImmutableMap.of("ENSFOOBAR1", ensfoobar1Profiles, "ENSFOOBAR2", ensfoobar2Profiles));
    }

    @Test
    public void returnsFacets() {
        when(geneSearchServiceDaoMock.getFacets(anyList(), any(SingleCellAnalyticsSchemaField.class)))
                .thenReturn(ImmutableMap.of(
                        "E-MTAB-0000", ImmutableMap.of(
                                "inferred_cell_type", Arrays.asList("neuron", "stem cell"),
                                "species", Collections.singletonList("homo sapiens")),
                        "E-MTAB-0001", ImmutableMap.of(
                                "inferred_cell_type", Arrays.asList("immune cell", "liver cell", "stem cell"),
                                "organism_part", Collections.singletonList("liver"),
                                "species", Arrays.asList("homo sapiens", "mus musculus"))
                ));

        Map<String, Map<String, List<String>>> result = subject.getFacets(Arrays.asList("cell_id_1", "cell_id_2"));

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("E-MTAB-0000", "E-MTAB-0001");

        assertThat(result.get("E-MTAB-0000")).containsKeys("species");
        assertThat(result.get("E-MTAB-0001")).containsKeys("species");
    }
}
