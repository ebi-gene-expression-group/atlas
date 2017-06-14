package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.IOException;

// Bean definition in test-solrContext.xml
@Named
public class EmbeddedSolrServerFactory {

    @Value("#{configuration['solr.location']}")
    private String solrConfDir;

    public EmbeddedSolrServer createEmbeddedSolrServerInstance() throws IOException {
            System.setProperty("solr.indexes.dir", System.getProperty("java.io.tmpdir"));
            CoreContainer coreContainer =  new CoreContainer(solrConfDir);
            coreContainer.load();
            return new EmbeddedSolrServer(coreContainer, "bioentities");
    }
}
