package uk.ac.ebi.atlas.monitoring;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.experimentimport.ExperimentDao;
import uk.ac.ebi.atlas.solr.cloud.admin.SolrCloudAdminProxy;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthCheckServiceTest {

    @Mock
    private SolrCloudAdminProxy solrCloudAdminProxyMock;

    @Mock
    private ExperimentDao experimentDaoMock;

    private HealthCheckService subject;

    private static final List<String> MOCK_SOLR_COLLECTIONS = Arrays.asList("mockCollection1", "mockCollection2");
    private static final String MOCK_SOLR_COLLECTION_ALIAS = "mockCollectionAlias";

    @BeforeEach
    void setUp() {
        subject = new HealthCheckService(solrCloudAdminProxyMock);
    }

    @Test
    void solrCollectionsAreUp() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenReturn(true);

        assertThat(subject.isSolrUp(MOCK_SOLR_COLLECTIONS, MOCK_SOLR_COLLECTION_ALIAS)).isTrue();
    }

    @Test
    void solrCollectionsAreDown() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenReturn(false);

        assertThat(subject.isSolrUp(MOCK_SOLR_COLLECTIONS, MOCK_SOLR_COLLECTION_ALIAS)).isFalse();
    }

    @Test
    void solrThrowsException() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenThrow(RuntimeException.class);

        assertThat(subject.isSolrUp(MOCK_SOLR_COLLECTIONS, MOCK_SOLR_COLLECTION_ALIAS)).isFalse();
    }

    @Test
    void experimentsDatabaseIsUp() {
        when(experimentDaoMock.countExperiments()).thenReturn(9);

        assertThat(subject.isDatabaseUp(experimentDaoMock)).isTrue();
    }

    @Test
    void noExperimentsInDatabase() {
        when(experimentDaoMock.countExperiments()).thenReturn(0);

        assertThat(subject.isDatabaseUp(experimentDaoMock)).isFalse();
    }

    @Test
    void experimentDaoThrowsException() {
        when(experimentDaoMock.countExperiments()).thenThrow(RuntimeException.class);

        assertThat(subject.isDatabaseUp(experimentDaoMock)).isFalse();
    }
}
