package uk.ac.ebi.atlas.experimentpage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TsnePlotSettingsServiceTest {

    @Mock
    private IdfParser idfParserMock;

    private static MockDataFileHub dataFileHubMock;

    private TsnePlotSettingsService subject;

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-5061";
    private static final String IDF_PREFERRED_K = "6";
    private static final String CLUSTERS_TSV_PREFERRED_K = "4";

    private static final String[][] CLUSTERS_TSV_WITH_PREFFERED_K_WITHOUT_IDF_K = {
            {"SEL.K", "K", "CELL_ID1", "CELL_ID2"},
            {"FALSE", "3", "abc123", "abc456"},
            {"TRUE", CLUSTERS_TSV_PREFERRED_K, "xyz111", "xyz222"},
            {"FALSE", "7", "def888", "def999"}};

    private static final String[][] CLUSTERS_TSV_WITHOUT_PREFFERED_K_WITHOUT_IDF_K = {
            {"SEL.K", "K", "CELL_ID1", "CELL_ID2"},
            {"FALSE", "3", "abc123", "abc456"},
            {"FALSE", "1", "xyz111", "xyz222"},
            {"FALSE", "7", "def888", "def999"}};

    private static final String[][] CLUSTERS_TSV_WITH_PREFFERED_K_WITH_IDF_K = {
            {"SEL.K", "K", "CELL_ID1", "CELL_ID2"},
            {"FALSE", "3", "abc123", "abc456"},
            {"TRUE", CLUSTERS_TSV_PREFERRED_K, "xyz111", "xyz222"},
            {"FALSE", IDF_PREFERRED_K, "abc000", "def000"},
            {"FALSE", "7", "def888", "def999"}};

    private static final String[][] CLUSTERS_TSV_WITHOUT_PREFFERED_K_WITH_IDF_K = {
            {"SEL.K", "K", "CELL_ID1", "CELL_ID2"},
            {"FALSE", "3", "abc123", "abc456"},
            {"FALSE", IDF_PREFERRED_K, "xyz111", "xyz222"},
            {"FALSE", "7", "def888", "def999"}};

    @BeforeEach
    public void setUp() {
        dataFileHubMock = MockDataFileHub.create();
        subject = new TsnePlotSettingsService(dataFileHubMock, idfParserMock);
    }

    @Test
    @DisplayName("Expected clusters value in idf is a valid cluster")
    public void expectedClustersOnlyInIdf() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITHOUT_PREFFERED_K_WITH_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title", "Description", Collections.emptyList(), Integer.parseInt(IDF_PREFERRED_K), Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isPresent();
        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION).get()).isEqualTo(Integer.parseInt(IDF_PREFERRED_K));
    }

    @Test
    @DisplayName("Valid expected clusters in idf and clusters.tsv")
    public void expectedClustersInIdfAndClustersTsv() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITH_PREFFERED_K_WITH_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title", "Description", Collections.emptyList(), Integer.parseInt(IDF_PREFERRED_K), Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isPresent();
        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION).get()).isEqualTo(Integer.parseInt(IDF_PREFERRED_K));
    }

    @Test
    @DisplayName("Expected clusters in idf is invalid and no value in clusters.tsv")
    public void invalidExpectedClustersInIdf() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITHOUT_PREFFERED_K_WITHOUT_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title",  "Description", Collections.emptyList(), Integer.parseInt(IDF_PREFERRED_K), Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isNotPresent();
    }

    @Test
    @DisplayName("Expected clusters in idf is invalid and expected clusters in clusters.tsv")
    public void invalidIdfValueValidClustersTsv() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITH_PREFFERED_K_WITHOUT_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title", "Description", Collections.emptyList(), Integer.parseInt(IDF_PREFERRED_K), Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isPresent();
        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION).get()).isEqualTo(Integer.parseInt(CLUSTERS_TSV_PREFERRED_K));
    }

    @Test
    @DisplayName("No value in idf, expected clusters in clusters.tsv")
    public void noIdfValueValidClustersTsv() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITH_PREFFERED_K_WITHOUT_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title", "Description", Collections.emptyList(), 0, Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isPresent();
        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION).get()).isEqualTo(Integer.parseInt(CLUSTERS_TSV_PREFERRED_K));
    }

    @Test
    @DisplayName("No value in idf or clusters.tsv")
    public void noExpectedClusters() {
        dataFileHubMock.addClustersFile(EXPERIMENT_ACCESSION, Arrays.asList(CLUSTERS_TSV_WITHOUT_PREFFERED_K_WITHOUT_IDF_K));
        when(idfParserMock.parse(EXPERIMENT_ACCESSION))
                .thenReturn(new IdfParserOutput("Title", "Description", Collections.emptyList(), 0, Collections.emptyList()));

        assertThat(subject.getExpectedClusters(EXPERIMENT_ACCESSION)).isNotPresent();
    }
}
