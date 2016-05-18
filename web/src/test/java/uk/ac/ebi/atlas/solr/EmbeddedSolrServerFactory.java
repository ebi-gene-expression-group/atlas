
package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

public class EmbeddedSolrServerFactory {

    public EmbeddedSolrServer createEmbeddedSolrServerInstance() {
        CoreContainer coreContainer =  new CoreContainer();

        coreContainer.load();
        return new EmbeddedSolrServer(coreContainer, "gxa");

    }
}
