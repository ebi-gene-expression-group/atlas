package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.TreeMultimap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 20/07/15.
 */

@Named
@Scope("singleton")
public class AnalyticsIndexerManager extends Observable {

    private static final Logger LOGGER = Logger.getLogger(AnalyticsIndexerManager.class);

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineTsvFileTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialTsvFileTemplate;

    private AnalyticsIndexerService analyticsIndexerService;
    private final AnalyticsIndexerMonitor analyticsIndexerMonitor;
    private final ExperimentTrader experimentTrader;
    private final ExperimentSorter experimentSorter;

    protected static final String DEFAULT_THREADS_8 = "8";
    protected static final String DEFAULT_BATCH_SIZE_1024 = "1024";
    protected static final String DEFAULT_TIMEOUT_IN_HOURS_24 = "24";

    @Inject
    public AnalyticsIndexerManager(AnalyticsIndexerService analyticsIndexerService, AnalyticsIndexerMonitor analyticsIndexerMonitor, ExperimentTrader experimentTrader, ExperimentSorter experimentSorter) {
        this.analyticsIndexerService = analyticsIndexerService;
        this.analyticsIndexerMonitor = analyticsIndexerMonitor;
        this.experimentTrader = experimentTrader;
        this.experimentSorter = experimentSorter;
    }

    public int addToAnalyticsIndex(String experimentAccession) {
        return addToAnalyticsIndex(experimentAccession, Integer.parseInt(DEFAULT_BATCH_SIZE_1024));
    }

    public int addToAnalyticsIndex(String experimentAccession, int batchSize) {
        checkNotNull(experimentAccession);
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
        return analyticsIndexerService.index(experiment, batchSize);
    }

    public void deleteFromAnalyticsIndex(String experimentAccession) {
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
    }

    private void indexPublicExperimentsConcurrently(Collection<String> experimentAccessions, int threads, int batchSize, int timeout) {

        LOGGER.debug(String.format("Starting ExecutorService with %d threads, %,d Solr document batch size and %d hour(s) timeout", threads, batchSize, timeout));
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);

        for (String experimentAccession : experimentAccessions) {
            threadPool.execute(new ReindexTask(experimentAccession, batchSize));
        }

        // From http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
        threadPool.shutdown();
        try {
            if (threadPool.awaitTermination(timeout, TimeUnit.HOURS)) {
                setChanged();
                notifyObservers("Pool shut down successfully. All threads finished within the specified timeout.\n");
            }
            else {
                setChanged();
                notifyObservers("Pool timed out. Initiating shutdown of running threads...\n");
                threadPool.shutdownNow();
                // Wait a while for tasks to respond to being cancelled
                if (threadPool.awaitTermination(10, TimeUnit.MINUTES)) {
                    setChanged();
                    notifyObservers("Threads closed successfully.\n");
                } else {
                    setChanged();
                    notifyObservers("Unable to close open threads. This means there is a thread leak in the current session.\n");
                }
            }
        } catch (InterruptedException e) {
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
            setChanged();
            notifyObservers("Pool main thread interrupted. Make sure there are no remaining running threads.\n");
        }

        setChanged();
        notifyObservers();
    }

    public void indexAllPublicExperiments(int threads, int batchSize, int timeout) throws InterruptedException {
        addObserver(analyticsIndexerMonitor);

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortAllExperimentsPerSize();
        setChanged();
        notifyObservers(descendingFileSizeToExperimentAccessions);

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(), threads, batchSize, timeout);

        deleteObserver(analyticsIndexerMonitor);
    }

    public void indexPublicExperiments(ExperimentType experimentType, int threads, int batchSize, int timeout) throws InterruptedException {
        addObserver(analyticsIndexerMonitor);

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortExperimentsPerSize(experimentType);
        setChanged();
        notifyObservers(descendingFileSizeToExperimentAccessions);

        indexPublicExperimentsConcurrently(descendingFileSizeToExperimentAccessions.values(), threads, batchSize, timeout);

        deleteObserver(analyticsIndexerMonitor);
    }

    private class ReindexTask implements Runnable {
        private final String experimentAccession;
        private final int batchSize;

        public ReindexTask(String experimentAccession, int batchSize) {
            this.experimentAccession = experimentAccession;
            this.batchSize = batchSize;
        }

        public void run() {
            addToAnalyticsIndex(experimentAccession, batchSize);

            AnalyticsIndexerManager.this.setChanged();
            AnalyticsIndexerManager.this.notifyObservers(experimentAccession);
        }
    }
}
