package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.SingleCellAnalyticsCollectionProxy;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import java.util.Arrays;
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
    @Mock
    private ScxaExperimentTrader experimentTraderMock;
    @Mock
    private ExperimentAttributesService experimentAttributesServiceMock;

    private GeneSearchService subject;

    @Before
    public void setUp() {
        subject = new GeneSearchService(geneSearchServiceDaoMock, experimentTraderMock, experimentAttributesServiceMock);
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
    public void retrievesExperimentInformation() {
        String experimentAccession = "E-MTAB-001";
        Experiment<Cell> mockExperiment = MockExperiment.createSingleCellBaselineExperiment(experimentAccession);

        when(experimentTraderMock.getPublicExperiment(experimentAccession)).thenReturn(mockExperiment);
        when(experimentAttributesServiceMock.getAttributes(mockExperiment)).thenReturn(ImmutableMap.of(
                "experimentAccession", experimentAccession,
                "type", mockExperiment.getType().getHumanDescription()
        ));

        Map<String, Object> result = subject.getExperimentInformation(experimentAccession);

        assertThat(result)
                .isNotEmpty()
                .containsKeys("experimentAccession", "type")
                .extracting("experimentAccession", "type")
                .contains(experimentAccession, ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE.getHumanDescription());

    }

    @Test
    public void returnsMarkerGeneProfile() {
        when(geneSearchServiceDaoMock.fetchKAndClusterIds(anyString())).thenReturn(ImmutableMap.of(
                "E-MTAB-0000", ImmutableMap.of(1, Arrays.asList(1, 3)),
                "E-MTAB-0001", ImmutableMap.of(
                        2, Arrays.asList(1, 5, 8),
                        5, Arrays.asList(12))
        ));

        Map<String, Map<Integer, List<Integer>>> result = subject.getMarkerGeneProfile("ENSG00000104952");

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("E-MTAB-0000", "E-MTAB-0001");
    }

    @Test
    public void returnsFacets() {
        when(geneSearchServiceDaoMock.getFacets(anyList(), any(SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField.class)))
                .thenReturn(ImmutableMap.of(
                        "E-MTAB-0000", ImmutableMap.of("inferred_cell_type", Arrays.asList("neuron", "stem cell")),
                        "E-MTAB-0001", ImmutableMap.of(
                                "inferred_cell_type", Arrays.asList("immune cell", "liver cell", "stem cell"),
                                "organism_part", Arrays.asList("liver"))
                ));

        Map<String, Map<String, List<String>>> result = subject.getFacets(Arrays.asList("cell_id_1", "cell_id_2"));

        assertThat(result)
                .isNotEmpty()
                .containsOnlyKeys("E-MTAB-0000", "E-MTAB-0001");
    }
}
