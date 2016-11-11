package uk.ac.ebi.atlas.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.core.io.Resource;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// Bean definition in test-solrContext.xml
@Named
public class EmbeddedSolrServerFactory {

    private Resource solrConfDir;

    public EmbeddedSolrServerFactory(Resource solrConfDir) {
        this.solrConfDir = solrConfDir;
    }

    public EmbeddedSolrServer createEmbeddedSolrServerInstance() throws IOException {
        Path solrConfDirPath = FileSystems.getDefault().getPath(solrConfDir.getURI().getPath());
        System.setProperty("solr.indexes.dir", System.getProperty("java.io.tmpdir"));
        CoreContainer coreContainer =  new CoreContainer(solrConfDirPath.toString());
        coreContainer.load();
        return new EmbeddedSolrServer(coreContainer, "gxa");
    }
}
