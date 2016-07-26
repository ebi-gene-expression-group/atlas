
package uk.ac.ebi.atlas.solr.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.solr.admin.index.BioentityIndex;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

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

    private BioentityIndexMonitor bioentityIndexMonitor;
    private String bioentityPropertiesDirectory;
    private ExecutorService executorService;

    private BioentityIndex bioentityIndex;

    // Injecting the ExecutorService allows us to inject a sameThreadExecutor in our unit tests,
    // so that verifications can be executed on the runnable task being started
    @Inject
    BioentityIndexAdmin(BioentityIndex bioentityIndex, BioentityIndexMonitor bioentityIndexMonitor,
                        @Value("#{configuration['bioentity.properties']}") String bioentityPropertiesDirectory,
                        @Named("singleThreadExecutorService") ExecutorService executorService){

        this.bioentityIndex = bioentityIndex;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesDirectory = bioentityPropertiesDirectory;
        this.executorService = executorService;
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
