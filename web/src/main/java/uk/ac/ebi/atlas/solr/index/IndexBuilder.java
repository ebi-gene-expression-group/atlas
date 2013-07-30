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
    public IndexBuilder(SolrServer solrServer, PropertyStream propertyStream) {
        this.solrServer = solrServer;
        this.propertyStream = propertyStream;
    }

    public void build() throws IOException, SolrServerException {

        PropertyDocument document;
        while ((document = propertyStream.next()) != null) {
            solrServer.addBean(document);

        }

        solrServer.commit();

    }
}
