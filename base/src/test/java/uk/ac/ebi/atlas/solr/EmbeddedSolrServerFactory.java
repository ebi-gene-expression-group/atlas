package uk.ac.ebi.atlas.solr;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class EmbeddedSolrServerFactory {
    private final CoreContainer coreContainer;

    public EmbeddedSolrServerFactory(String dataFilesLocation) throws IOException {
        Path solrTempDirectory = Files.createTempDirectory("");
        FileUtils.copyDirectory(Paths.get(dataFilesLocation, "solr").toFile(), solrTempDirectory.toFile());
        coreContainer =  new CoreContainer(solrTempDirectory.toString());
    }

    public EmbeddedSolrServer createEmbeddedSolrServerInstance(String coreName) {
        coreContainer.load();
        return new EmbeddedSolrServer(coreContainer, coreName);
    }
}
