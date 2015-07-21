package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import org.apache.hadoop.hdfs.server.datanode.DirectoryScanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentSorter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
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

    @Value("#{configuration['experiment.magetab.path.template']}")
    private String baselineTsvFileTemplate;

    @Value("#{configuration['diff.experiment.data.path.template']}")
    private String differentialTsvFileTemplate;

    private AnalyticsIndexerService analyticsIndexerService;
    private final ExperimentTrader experimentTrader;
    private final ExperimentSorter experimentSorter;

    private static final int INDEXING_THREADS = 4;

    @Inject
    public AnalyticsIndexerManager(AnalyticsIndexerService analyticsIndexerService, ExperimentTrader experimentTrader, ExperimentSorter experimentSorter) {
        this.analyticsIndexerService = analyticsIndexerService;
        this.experimentTrader = experimentTrader;
        this.experimentSorter = experimentSorter;
    }

    public int addToAnalyticsIndex(String experimentAccession) {
        checkNotNull(experimentAccession);
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
        return analyticsIndexerService.index(experiment);
    }

    public void deleteFromAnalyticsIndex(String experimentAccession) {
        analyticsIndexerService.deleteExperimentFromIndex(experimentAccession);
    }

    public void indexAllPublicExperiments(@Nullable Integer numThreads) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(numThreads != null && numThreads > 0 ? numThreads : INDEXING_THREADS);

        TreeMultimap<Long, String> descendingFileSizeToExperimentAccessions = experimentSorter.reverseSortExperimentsPerSize();
        setChanged();
        notifyObservers(descendingFileSizeToExperimentAccessions);

        for (String experimentAccession : descendingFileSizeToExperimentAccessions.values()) {
            threadPool.execute(new ReindexTask(experimentAccession));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(12, TimeUnit.HOURS);    // ~ twice the time of the biggest experiment

        setChanged();
        notifyObservers();
    }

    private class ReindexTask implements Runnable {
        private final String experimentAccession;

        public ReindexTask(String experimentAccession) {
            this.experimentAccession = experimentAccession;
        }

        public void run() {
            addToAnalyticsIndex(experimentAccession);

            AnalyticsIndexerManager.this.setChanged();
            AnalyticsIndexerManager.this.notifyObservers(experimentAccession);
        }
    }
}
