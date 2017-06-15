package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.IOException;

@Named
public class EmbeddedSolrServerFactory {
    @Value("#{configuration['solr.location']}")
    private String solrConfDir;

    public EmbeddedSolrServer createEmbeddedSolrServerInstance(String coreName) throws IOException {
        CoreContainer coreContainer =  new CoreContainer(solrConfDir);
        coreContainer.load();
        return new EmbeddedSolrServer(coreContainer, coreName);
    }
}
