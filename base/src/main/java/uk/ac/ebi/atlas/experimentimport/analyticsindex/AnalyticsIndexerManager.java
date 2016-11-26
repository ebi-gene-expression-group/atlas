package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.BioentityIdentifiersReader;
import uk.ac.ebi.atlas.utils.ExperimentSorter;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;

public class AnalyticsIndexerManager extends Observable {
    protected static final String DEFAULT_THREADS_8 = "8";
    protected static final String DEFAULT_SOLR_BATCH_SIZE_8192 = "8192";
    protected static final String DEFAULT_TIMEOUT_IN_HOURS_24 = "24";
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsIndexerManager.class);
    private static final int LONGER_THAN_BIGGEST_EXPERIMENT_INDEX_TIME = 60;   // in minutes
    private final AnalyticsIndexerMonitor analyticsIndexerMonitor;
    private final BioentityIdentifiersReader bioentityIdentifiersReader;
    private final BioentityPropertiesDao bioentityPropertiesDao;
    private final ExperimentSorter experimentSorter;
    private AnalyticsIndexerService analyticsIndexerService;
    protected final ExperimentTrader experimentTrader;

    public AnalyticsIndexerManager(ExperimentSorter experimentSorter, AnalyticsIndexerMonitor analyticsIndexerMonitor, BioentityIdentifiersReader bioentityIdentifiersReader, AnalyticsIndexerService analyticsIndexerService, ExperimentTrader experimentTrader, BioentityPropertiesDao bioentityPropertiesDao) {
        this.experimentSorter = experimentSorter;
        this.analyticsIndexerMonitor = analyticsIndexerMonitor;
        this.bioentityIdentifiersReader = bioentityIdentifiersReader;
        this.analyticsIndexerService = analyticsIndexerService;
        this.experimentTrader = experimentTrader;
        this.bioentityPropertiesDao = bioentityPropertiesDao;
    }

    public int addToAnalyticsIndex(String experimentAccession) {
        return addToAnalyticsIndex(experimentAccession, bioentityPropertiesDao.getMap
                (bioentityIdentifiersReader.getBioentityIdsFromExperiment(experimentAccession)), 8000);
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

        ImmutableMap<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch =
                bioentityPropertiesDao.getMap(bioentityIdentifiersReader
                        .getBioentityIdsFromExperiments(ExperimentType.values()));
        setChangedAndNotifyObservers("Extracted "+bioentityIdToIdentifierSearch.size()+" bioentityIds from experiments");

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(),
                bioentityIdToIdentifierSearch, threads, batchSize, timeout);

        setChangedAndNotifyObservers("Optimizing index...");
        analyticsIndexerService.optimize();
        setChangedAndNotifyObservers("Index optimized");
        setChangedAndNotifyObservers("All done!");

        deleteObserver(analyticsIndexerMonitor);
    }

    public void indexPublicExperiments(ExperimentType experimentType, int threads, int batchSize, int timeout) throws
            InterruptedException {
        addObserver(analyticsIndexerMonitor);

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortExperimentsPerSize(experimentType);
        setChangedAndNotifyObservers(descendingFileSizeToExperimentAccessions);

        ImmutableMap<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch =
                bioentityPropertiesDao.getMap(bioentityIdentifiersReader.getBioentityIdsFromExperiments(experimentType));
        setChangedAndNotifyObservers("Extracted "+bioentityIdToIdentifierSearch.size()+" bioentityIds from experiments");

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(),
                bioentityIdToIdentifierSearch, threads, batchSize, timeout);

        deleteObserver(analyticsIndexerMonitor);
    }

    private int addToAnalyticsIndex(String experimentAccession,
                                    ImmutableMap<String, Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch, int batchSize) {
        checkNotNull(experimentAccession);
        LOGGER.info("Adding {} to the index", experimentAccession);
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);

        return analyticsIndexerService.index(experiment, bioentityIdToIdentifierSearch, batchSize);
    }

    private void indexPublicExperimentsConcurrently(Collection<String> experimentAccessions, ImmutableMap<String,
            Map<BioentityPropertyName, Set<String>>> bioentityIdToIdentifierSearch, int threads, int batchSize, int timeout) {

        LOGGER.debug("Starting ExecutorService with {} threads, {} timeout", threads, timeout);

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
        private final ImmutableMap<String, Map<BioentityPropertyName, Set<String>>>
                bioentityIdToIdentifierSearch;
        private final int batchSize;

        public ReindexTask(String experimentAccession, ImmutableMap<String, Map<BioentityPropertyName, Set<String>>>
                bioentityIdToIdentifierSearch,int batchSize) {
            this.experimentAccession = experimentAccession;
            this.bioentityIdToIdentifierSearch = bioentityIdToIdentifierSearch;
            this.batchSize = batchSize;
        }

        public void run() {
            try {
                addToAnalyticsIndex(experimentAccession, bioentityIdToIdentifierSearch, batchSize);
                AnalyticsIndexerManager.this.setChangedAndNotifyObservers(experimentAccession);
            } catch (Exception exception) {
                AnalyticsIndexerManager.this.setChangedAndNotifyObservers(
                        String.format("Failed to index %s - Cause: %s", experimentAccession, exception.getMessage()));
            }
        }
    }
}
