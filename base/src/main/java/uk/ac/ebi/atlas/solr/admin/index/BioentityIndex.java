package uk.ac.ebi.atlas.solr.admin.index;

import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.BioentityProperty;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@Named
@Scope("prototype")
public class BioentityIndex {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIndex.class);

    private static final String REACTOME_DIR = "reactome";

    private BioentityIndexMonitor bioentityIndexMonitor;
    private final BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder;

    private SolrClient solrClient;

    @Inject
    public BioentityIndex(BioentityIndexMonitor bioentityIndexMonitor, SolrClient solrClient, BioentityPropertiesStreamBuilder bioentityPropertiesStreamBuilder) {
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesStreamBuilder = bioentityPropertiesStreamBuilder;
        this.solrClient = solrClient;
    }

    public void indexAll(final DirectoryStream<Path> directoryStream) {
        try {
            indexDirectory(directoryStream, false);

            solrClient.commit();

            optimize();

            bioentityIndexMonitor.stop();

        } catch (IOException | SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void indexDirectory(DirectoryStream<Path> bioentityPropertiesDirectoryStream, boolean isReactomeDirectory) throws IOException, SolrServerException {
        try (DirectoryStream<Path> directoryStream = bioentityPropertiesDirectoryStream) {

            for (Path path : directoryStream) {

                if (Files.isDirectory(path)) {
                    isReactomeDirectory = path.toString().contains(REACTOME_DIR);
                    indexDirectory(Files.newDirectoryStream(path), isReactomeDirectory);
                } else if (Files.isRegularFile(path)) {
                    indexFile(path, isReactomeDirectory);
                }
            }
        }
    }

    void indexFile(Path filePath, boolean isReactome) throws IOException, SolrServerException {

        if (filePath.toString().endsWith(".tsv") && !filePath.toString().endsWith("goIDToTerm.tsv") && !filePath.toString().endsWith("interproIDToTypeTerm.tsv")) {

            bioentityPropertiesStreamBuilder.forPath(filePath).isForReactome(isReactome);

            try (BioentityPropertiesStream bioentityBioentityPropertiesStream =
                         bioentityPropertiesStreamBuilder.build()) {

                LOGGER.info("<indexFile> streaming started for file: {}", filePath);

                bioentityIndexMonitor.processing(filePath);

                Collection<BioentityProperty> documents;

                while ((documents = bioentityBioentityPropertiesStream.next()) != null) {

                    solrClient.addBeans(documents);

                }

                bioentityIndexMonitor.completed(filePath);

            }

        }

    }


    public void deleteAll() {
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void optimize() {
        try {

            solrClient.optimize();

        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }

    }
}
