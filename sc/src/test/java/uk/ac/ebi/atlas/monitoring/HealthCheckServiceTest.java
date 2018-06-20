package uk.ac.ebi.atlas.monitoring;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.solr.cloud.admin.SolrCloudAdminProxy;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HealthCheckServiceTest {

    @Mock
    private SolrCloudAdminProxy solrCloudAdminProxyMock;

    @Mock
    private ScxaExperimentDao experimentDaoMock;

    private HealthCheckService subject;

    @BeforeEach
    public void setUp() {
        subject = new HealthCheckService(solrCloudAdminProxyMock, experimentDaoMock);
    }

    @Test
    public void solrCollectionsAreUp() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenReturn(true);

        assertThat(subject.isSolrUp()).isTrue();
    }

    @Test
    public void solrCollectionsAreDown() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenReturn(false);

        assertThat(subject.isSolrUp()).isFalse();
    }

    @Test
    public void solrThrowsException() throws IOException, SolrServerException {
        when(solrCloudAdminProxyMock.areCollectionsUp(anyList(), any())).thenThrow(RuntimeException.class);

        assertThat(subject.isSolrUp()).isFalse();
    }

    @Test
    public void experimentsDatabaseIsUp() {
        when(experimentDaoMock.countExperiments()).thenReturn(9);

        assertThat(subject.isDatabaseUp()).isTrue();
    }
    @Test
    public void noExperimentsInDatabase() {
        when(experimentDaoMock.countExperiments()).thenReturn(0);

        assertThat(subject.isDatabaseUp()).isFalse();
    }
}
