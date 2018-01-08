package uk.ac.ebi.atlas.solr.util;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test(expected = IllegalStateException.class)
    public void queryIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.query(anyString(), any(SolrQuery.class))).thenThrow(new IOException());
        subject.query(new SolrQuery("*:*"));
    }

    @Test(expected = IllegalStateException.class)
    public void querySolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.query(anyString(), any(SolrQuery.class))).thenThrow(new SolrServerException(""));
        subject.query(new SolrQuery("*:*"));
    }

    @Test(expected = IllegalStateException.class)
    public void addIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.add(anyString(), anyCollection())).thenThrow(new IOException());
        subject.add(ImmutableSet.of());
    }

    @Test(expected = IllegalStateException.class)
    public void addSolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.add(anyString(), anyCollection())).thenThrow(new SolrServerException(""));
        subject.add(ImmutableSet.of());
    }

    @Test(expected = IllegalStateException.class)
    public void commitIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.add(anyString(), anyCollection())).thenReturn(new UpdateResponse());
        when(solrClientMock.commit()).thenThrow(new IOException());
        subject.addAndCommit(ImmutableSet.of());
    }

    @Test(expected = IllegalStateException.class)
    public void commitSolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.add(anyString(), anyCollection())).thenReturn(new UpdateResponse());
        when(solrClientMock.commit()).thenThrow(new SolrServerException(""));
        subject.addAndCommit(ImmutableSet.of());
    }

    @Test(expected = IllegalStateException.class)
    public void deleteIOExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.deleteByQuery("*:*")).thenThrow(new IOException());
        subject.deleteAllAndCommit();
    }

    @Test(expected = IllegalStateException.class)
    public void deleteSolrServerExceptionIsWrapped() throws IOException, SolrServerException {
        when(solrClientMock.deleteByQuery("*:*")).thenThrow(new SolrServerException(""));
        subject.deleteAllAndCommit();
    }

}