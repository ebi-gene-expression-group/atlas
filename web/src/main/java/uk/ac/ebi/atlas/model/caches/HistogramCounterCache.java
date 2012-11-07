package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.score.HistogramCounter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

@Named("histogramCounter")
@Scope("singleton")
public class HistogramCounterCache {

    private static final Logger logger = Logger.getLogger(HistogramCounterCache.class);

    private LoadingCache<String, HistogramCounter> histogramCounters;

    @Inject
    public HistogramCounterCache(LoadingCache<String, HistogramCounter> histogramCounters) {
        this.histogramCounters = histogramCounters;
    }

    public HistogramCounter getExperimentRuns(String experimentAccession) {
        try {

            return histogramCounters.get(experimentAccession);

        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(),
                    e.getCause());
        }
    }

}
