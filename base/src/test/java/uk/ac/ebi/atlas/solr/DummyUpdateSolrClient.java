package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.util.Collection;

import static org.mockito.Mockito.mock;

public class DummyUpdateSolrClient {

    private final HttpSolrClient httpSolrClient;

    public DummyUpdateSolrClient(String baseUrl) {
        httpSolrClient = new HttpSolrClient.Builder(baseUrl).build();
    }

    // Do nothing! This is required because update methods do not work on lime from a remote server

    public UpdateResponse deleteByQuery(String query) {
        return mock(UpdateResponse.class);
    }

    public UpdateResponse commit() {
        return mock(UpdateResponse.class);
    }

    public UpdateResponse addBeans(Collection<?> documentBeans) {
        return mock(UpdateResponse.class);
    }

    public UpdateResponse optimize() {
        return mock(UpdateResponse.class);
    }

}
