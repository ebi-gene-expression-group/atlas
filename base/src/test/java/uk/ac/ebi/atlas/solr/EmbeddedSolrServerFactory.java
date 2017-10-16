package uk.ac.ebi.atlas.solr;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
public class EmbeddedSolrServerFactory {
    @Value("#{configuration['solr.location']}")
    private String solrConfDir;

    public EmbeddedSolrServer createEmbeddedSolrServerInstance(String coreName) throws IOException {
        Path solrTempDirectory = Files.createTempDirectory("");
        FileUtils.copyDirectory(new File(solrConfDir), solrTempDirectory.toFile());
        CoreContainer coreContainer =  new CoreContainer(solrTempDirectory.toString());
        coreContainer.load();
        return new EmbeddedSolrServer(coreContainer, coreName);
    }
}
