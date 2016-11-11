package uk.ac.ebi.atlas.solr.admin;

import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.BioentityIndex;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Named
@Scope("prototype")
public class BioentityIndexAdmin {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIndexAdmin.class);

    private BioentityIndex bioentityIndex;
    private BioentityIndexMonitor bioentityIndexMonitor;
    private String bioentityPropertiesDirectory;
    private ExecutorService executorService;

    // Injecting the ExecutorService allows us to inject a sameThreadExecutor in our unit tests,
    // so that verifications can be executed on the runnable task being started
    @Inject
    BioentityIndexAdmin(BioentityIndexMonitor bioentityIndexMonitor,
                        @Value("#{configuration['bioentity.properties']}") String bioentityPropertiesDirectory,
                        @Named("singleThreadExecutorService") ExecutorService executorService) {
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesDirectory = bioentityPropertiesDirectory;
        this.executorService = executorService;
    }

    @Inject
    public void setBioentityIndex(BioentityIndex bioentityIndex) {
        this.bioentityIndex = bioentityIndex;
    }

    public void rebuildIndex() {
        final Path bioentityPropertiesPath = Paths.get(bioentityPropertiesDirectory);

        if (bioentityIndexMonitor.start()){

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        bioentityIndex.deleteAll();
                        bioentityIndex.indexAll(Files.newDirectoryStream(bioentityPropertiesPath));
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        bioentityIndexMonitor.failed(e);
                    }
                }
            });
            executorService.shutdown();
        }
    }

}
