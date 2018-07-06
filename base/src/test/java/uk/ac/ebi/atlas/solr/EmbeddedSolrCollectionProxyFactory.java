package uk.ac.ebi.atlas.solr;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.solr.cloud.fullanalytics.AnalyticsCollectionProxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class EmbeddedSolrCollectionProxyFactory {
    private final CoreContainer coreContainer;

    public EmbeddedSolrCollectionProxyFactory(String dataFilesLocation) throws IOException {
        Path solrTempDirectory = Files.createTempDirectory("");
        FileUtils.copyDirectory(Paths.get(dataFilesLocation, "solr").toFile(), solrTempDirectory.toFile());
        coreContainer = new CoreContainer(solrTempDirectory.toString());
        coreContainer.load();
    }

    public AnalyticsCollectionProxy createAnalyticsCollectionProxy() {
        return new AnalyticsCollectionProxy(new EmbeddedSolrServer(coreContainer, "analytics"));
    }
}
