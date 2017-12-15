package uk.ac.ebi.atlas.solr.util;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Named
public class EmbeddedSolrCollectionProxyFactory {
    private final CoreContainer coreContainer;

    public EmbeddedSolrCollectionProxyFactory(@Value("#{configuration['solr.location']}") String solrConfDir) {
        try {
            Path solrTempDirectory = Files.createTempDirectory("");
            FileUtils.copyDirectory(new File(solrConfDir), solrTempDirectory.toFile());
            coreContainer = new CoreContainer(solrTempDirectory.toString());
            coreContainer.load();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public CollectionProxy createAnalyticsCollectionProxy() {
        return new AnalyticsCollectionProxy(new EmbeddedSolrServer(coreContainer, "analytics"));
    }
}
