
package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.util.Collection;

import static org.mockito.Mockito.mock;

public class DummyUpdateSolrClient extends HttpSolrClient {

    public DummyUpdateSolrClient(String baseUrl) {
        super(baseUrl);
    }

    // Do nothing! This is required because update methods do not work on lime from a remote server

    @Override
    public UpdateResponse deleteByQuery(String query) {
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse commit() {
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse addBeans(Collection<?> documentBeans) {
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse optimize() {
        return mock(UpdateResponse.class);
    }

}
