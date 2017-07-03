package uk.ac.ebi.atlas.solr.admin;

import com.google.common.collect.Iterators;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.resource.BioentityPropertyFile;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Named
@Scope("prototype")
public class BioentitiesIndexer {

    private static final int BATCH_SIZE = 20000;

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentitiesIndexer.class);

    private final BioentityIndexMonitor bioentityIndexMonitor;
    private final BioentityPropertiesSource bioentityPropertiesSource;
    private final SolrClient solrClient;
    private ExecutorService executorService;

    @Inject
    public BioentitiesIndexer(BioentityIndexMonitor bioentityIndexMonitor,
                       BioentityPropertiesSource bioentityPropertiesSource,
                       @Qualifier("solrClientBioentities") SolrClient solrClient) {
        this(bioentityIndexMonitor,bioentityPropertiesSource, solrClient, Executors.newSingleThreadExecutor() );
    }

    BioentitiesIndexer(BioentityIndexMonitor bioentityIndexMonitor,
                       BioentityPropertiesSource bioentityPropertiesSource,
                       SolrClient solrClient,
                       ExecutorService executorService) {
        this.bioentityIndexMonitor = bioentityIndexMonitor;
        this.bioentityPropertiesSource = bioentityPropertiesSource;
        this.solrClient = solrClient;
        this.executorService = executorService;
    }

    public void rebuildIndex() {
        if (bioentityIndexMonitor.start()){
            executorService.execute(this::doRebuild);
            executorService.shutdown();
        }
    }

    private void addFileToIndex(BioentityPropertyFile bioentityPropertyFile){
        Iterators.partition(bioentityPropertyFile.get().iterator(), BATCH_SIZE).forEachRemaining(this::addBeans);
    }

    private void addFileAndLog(BioentityPropertyFile bioentityPropertyFile){
        LOGGER.info("<indexFile> streaming started: {}", bioentityPropertyFile.toString());
        bioentityIndexMonitor.processing(bioentityPropertyFile);
        addFileToIndex(bioentityPropertyFile);
        bioentityIndexMonitor.completed();
    }

    private void addBeans(Collection<?> x){
        try {
            solrClient.addBeans(x);
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    void doRebuild(){
        try {
            bioentityIndexMonitor.start();
            deleteAll();
            bioentityPropertiesSource.getAnnotationFiles().forEach(this::addFileAndLog);
            bioentityPropertiesSource.getArrayDesignMappingFiles().forEach(this::addFileAndLog);
            bioentityPropertiesSource.getReactomePropertyFiles().forEach(this::addFileAndLog);
            solrClient.commit();
            solrClient.optimize();
            bioentityIndexMonitor.stop();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            bioentityIndexMonitor.failed(e);
        }
    }


    void deleteAll() {
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

}
