package uk.ac.ebi.atlas.search;

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
import static org.mockito.ArgumentMatchers.anyString;
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
    public void returnsCellIdsPerExperiment() {
        when(geneSearchServiceDaoMock.fetchCellIds(anyString())).thenReturn(ImmutableMap.of(
                "E-MTAB-0000", Arrays.asList("cell_id_1", "cell_id_2", "cell_id_3", "cell_id_4", "cell_id_5"),
                "E-MTAB-0001", Arrays.asList("cell_id_6", "cell_id_7", "cell_id_8"),
                "E-MTAB-0002", Arrays.asList("cell_id_9", "cell_id_10")
        ));

        Map<String, List<String>> result = subject.getCellIdsInExperiments("ENSG00000104957");

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("E-MTAB-0000", "E-MTAB-0001", "E-MTAB-0002");
    }

    @Test
    public void returnsMarkerGeneProfile() {
        when(geneSearchServiceDaoMock.fetchKAndClusterIds(anyString())).thenReturn(ImmutableMap.of(
                "E-MTAB-0000", ImmutableMap.of(1, Arrays.asList(1, 3)),
                "E-MTAB-0001", ImmutableMap.of(
                        2, Arrays.asList(1, 5, 8),
                        5, Collections.singletonList(12))
        ));

        Map<String, Map<Integer, List<Integer>>> result = subject.getMarkerGeneProfile("ENSG00000104952");

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("E-MTAB-0000", "E-MTAB-0001");
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
