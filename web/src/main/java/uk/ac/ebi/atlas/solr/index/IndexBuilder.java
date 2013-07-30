package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class IndexBuilder {

    private SolrServer solrServer;
    private PropertyStream propertyStream;


    @Inject
    public IndexBuilder(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

    public void build() throws IOException, SolrServerException {

        PropertyDocument propertyDocument = new PropertyDocument();
        propertyDocument.setBioentityIdentifier("hello");

        solrServer.addBean(propertyDocument);

        solrServer.commit();

    }
}
