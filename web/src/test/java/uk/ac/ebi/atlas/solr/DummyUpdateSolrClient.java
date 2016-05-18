
package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.util.Collection;

import static org.mockito.Mockito.mock;

public class DummyUpdateSolrClient extends HttpSolrClient {

    public DummyUpdateSolrClient(String baseUrl){
        super(baseUrl);
    }

    @Override
    public UpdateResponse deleteByQuery(String query){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse commit(){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse addBeans(Collection<?> documentBeans){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }

    @Override
    public UpdateResponse optimize(){
        //do nothing! This is required because update methods do not work on lime from a remote server
        return mock(UpdateResponse.class);
    }


}
