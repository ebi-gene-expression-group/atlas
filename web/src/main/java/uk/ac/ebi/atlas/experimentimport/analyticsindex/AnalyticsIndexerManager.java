package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.support.IdentifierSearchTermsTrader;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentSorter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class AnalyticsIndexerManager extends Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerManager.class);

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineTsvFileTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialTsvFileTemplate;

    private AnalyticsIndexerService analyticsIndexerService;
    private final AnalyticsIndexerMonitor analyticsIndexerMonitor;
    private final ExperimentTrader experimentTrader;
    private final IdentifierSearchTermsTrader identifierSearchTermsTrader;
    private final ExperimentSorter experimentSorter;

    protected static final String DEFAULT_THREADS_8 = "8";
    protected static final String DEFAULT_SOLR_BATCH_SIZE_8192 = "8192";
    protected static final String DEFAULT_TIMEOUT_IN_HOURS_24 = "24";

    private static final int LONGER_THAN_BIGGEST_EXPERIMENT_INDEX_TIME = 60;   // in minutes

    @Inject
    public AnalyticsIndexerManager(AnalyticsIndexerService analyticsIndexerService,
                                   AnalyticsIndexerMonitor analyticsIndexerMonitor,
                                   ExperimentTrader experimentTrader,
                                   IdentifierSearchTermsTrader identifierSearchTermsTrader,
                                   ExperimentSorter experimentSorter) {
        this.analyticsIndexerService = analyticsIndexerService;
        this.analyticsIndexerMonitor = analyticsIndexerMonitor;
        this.experimentTrader = experimentTrader;
        this.identifierSearchTermsTrader = identifierSearchTermsTrader;
        this.experimentSorter = experimentSorter;
    }


    public int addToAnalyticsIndex(String experimentAccession) {
        ImmutableMap<String, String> bioentityIdToIdentifierSearch = identifierSearchTermsTrader.getBioentityIdToIdentifierSearchMap(experimentAccession);
        return addToAnalyticsIndex(experimentAccession, bioentityIdToIdentifierSearch, Integer.parseInt(DEFAULT_SOLR_BATCH_SIZE_8192));
    }


    public void deleteFromAnalyticsIndex(String experimentAccession) {
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
    }


    public void indexAllPublicExperiments(int threads, int batchSize, int timeout) throws InterruptedException {
        addObserver(analyticsIndexerMonitor);

        setChangedAndNotifyObservers("Deleting all documents from analytics index...");
        analyticsIndexerService.deleteAll();

        setChangedAndNotifyObservers("Analytics index build has started: sorting experiments by size");

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortAllExperimentsPerSize();
        setChangedAndNotifyObservers(descendingFileSizeToExperimentAccessions);

        ImmutableMap<String, String> bioentityIdToIdentifierSearch = identifierSearchTermsTrader.getBioentityIdToIdentifierSearchMap();
        setChangedAndNotifyObservers("Extracted "+bioentityIdToIdentifierSearch.size()+" bioentityIds from experiments");

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(), bioentityIdToIdentifierSearch, threads, batchSize, timeout);

        setChangedAndNotifyObservers("Optimizing index...");
        analyticsIndexerService.optimize();
        setChangedAndNotifyObservers("Index optimized");
        setChangedAndNotifyObservers("All done!");

        deleteObserver(analyticsIndexerMonitor);
    }


    public void indexPublicExperiments(ExperimentType experimentType, int threads, int batchSize, int timeout) throws InterruptedException {
        addObserver(analyticsIndexerMonitor);

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortExperimentsPerSize(experimentType);
        setChangedAndNotifyObservers(descendingFileSizeToExperimentAccessions);

        ImmutableMap<String, String> bioentityIdToIdentifierSearch = identifierSearchTermsTrader.getBioentityIdToIdentifierSearchMap(experimentType);
        setChangedAndNotifyObservers("Extracted "+bioentityIdToIdentifierSearch.size()+" bioentityIds from experiments");

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(), bioentityIdToIdentifierSearch, threads, batchSize, timeout);

        deleteObserver(analyticsIndexerMonitor);
    }


    private int addToAnalyticsIndex(String experimentAccession, ImmutableMap<String, String> bioentityIdToIdentifierSearch, int batchSize) {
        checkNotNull(experimentAccession);
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
        return analyticsIndexerService.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }


    private void indexPublicExperimentsConcurrently(Collection<String> experimentAccessions, ImmutableMap<String, String> bioentityIdToIdentifierSearch, int threads, int batchSize, int timeout) {

        LOGGER.debug("Starting ExecutorService with {} threads, {} Solr document batch size and {} hour(s) timeout", threads, batchSize, timeout);

        ExecutorService threadPool = Executors.newFixedThreadPool(threads);

        for (String experimentAccession : experimentAccessions) {
            threadPool.execute(new ReindexTask(experimentAccession, bioentityIdToIdentifierSearch, batchSize));
        }

        // From http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
        threadPool.shutdown();
        try {
            if (threadPool.awaitTermination(timeout, TimeUnit.HOURS)) {
                setChangedAndNotifyObservers("Pool shut down successfully. All threads finished within the specified timeout.\n");
            }
            else {
                setChangedAndNotifyObservers("Pool timed out. Initiating shutdown of running threads...\n");
                threadPool.shutdownNow();
                // Wait a while for tasks to respond to being cancelled
                if (threadPool.awaitTermination(LONGER_THAN_BIGGEST_EXPERIMENT_INDEX_TIME, TimeUnit.MINUTES)) {
                    setChangedAndNotifyObservers("Threads closed successfully.\n");
                } else {
                    setChangedAndNotifyObservers("Unable to close open threads. This means there is a thread leak in the current session.\n");
                }
            }
        } catch (InterruptedException e) {
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
            setChanged();
            notifyObservers("Pool main thread interrupted. Make sure there are no remaining running threads.\n");
        }

        setChangedAndNotifyObservers(null);
    }

    private void setChangedAndNotifyObservers(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    private class ReindexTask implements Runnable {
        private final String experimentAccession;
        private final ImmutableMap<String, String> bioentityIdToIdentifierSearch;
        private final int solrBatchSize;

        public ReindexTask(String experimentAccession, ImmutableMap<String, String> bioentityIdToIdentifierSearch, int solrBatchSize) {
            this.experimentAccession = experimentAccession;
            this.bioentityIdToIdentifierSearch = bioentityIdToIdentifierSearch;
            this.solrBatchSize = solrBatchSize;
        }

        public void run() {
            try {
                addToAnalyticsIndex(experimentAccession, bioentityIdToIdentifierSearch, solrBatchSize);
                AnalyticsIndexerManager.this.setChangedAndNotifyObservers(experimentAccession);
            } catch (Exception exception) {
                AnalyticsIndexerManager.this.setChangedAndNotifyObservers(
                        String.format("Failed to index %s - Cause: %s", experimentAccession, exception.getMessage()));
            }
        }
    }
}
