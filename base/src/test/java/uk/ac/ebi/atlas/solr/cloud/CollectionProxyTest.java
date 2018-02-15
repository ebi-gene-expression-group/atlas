package uk.ac.ebi.atlas.solr.cloud;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CollectionProxyTest {

    @Mock
    private SolrClient solrClientMock;

    private CollectionProxy subject;

    @Before
    public void setUp() {
        subject = new CollectionProxy(solrClientMock, "mocked_collection") {};
    }

    @Test
    public void queryIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.query(anyString(), any(SolrQuery.class)))
                .thenThrow(new IOException());

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.query(new SolrQuery("*:*")));
    }

    @Test
    public void querySolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.query(anyString(), any(SolrQuery.class)))
                .thenThrow(new SolrServerException(""));

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.query(new SolrQuery("*:*")));
    }

    @Test
    public void addAndCommitIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.request(any(UpdateRequest.class), eq(subject.nameOrAlias)))
                .thenThrow(new IOException());

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.addAndCommit(ImmutableSet.of()));
    }

    @Test
    public void addAndCommitSolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.request(any(UpdateRequest.class), eq(subject.nameOrAlias)))
                .thenThrow(new SolrServerException(""));

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.addAndCommit(ImmutableSet.of()));
    }

    @Test
    public void deleteIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.request(any(UpdateRequest.class), eq(subject.nameOrAlias)))
                .thenThrow(new IOException());

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.deleteAllAndCommit());
    }

    @Test
    public void deleteSolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.request(any(UpdateRequest.class), eq(subject.nameOrAlias)))
                .thenThrow(new SolrServerException(""));

        assertThatExceptionOfType(UncheckedIOException.class)
                .isThrownBy(() -> subject.deleteAllAndCommit());
    }

}