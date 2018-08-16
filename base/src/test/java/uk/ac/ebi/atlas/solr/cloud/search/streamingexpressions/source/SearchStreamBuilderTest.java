package uk.ac.ebi.atlas.solr.cloud.search.streamingexpressions.source;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.solr.cloud.CollectionProxy;
import uk.ac.ebi.atlas.solr.cloud.search.SolrQueryBuilder;

import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchStreamBuilderTest {
    @Mock
    private CloudSolrClient cloudSolrClientMock;

    private class DummyCollectionProxy extends CollectionProxy {
        protected DummyCollectionProxy(SolrClient solrClient, String nameOrAlias) {
            super(solrClient, nameOrAlias);
        }
    }

    @BeforeEach
    void setUp() {
        when(cloudSolrClientMock.getZkHost()).thenReturn("foobar");
    }

    @Test
    void ioExceptionsAreWrapped() {
        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() ->
            new SearchStreamBuilder<>(
                    new DummyCollectionProxy(cloudSolrClientMock, null), new SolrQueryBuilder<>()).build());
    }
}
