package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ExecutionException;

/*
If you want to support drawing the charts for arbitrary columns of data
(we used to draw a chart corresponding to the query if some columns were selected)
- add them as cache keys
- set them as stream options
- make the UI make sense

The last part became harder because choices of filters and cutoffs now appear orthogonal in the UI
 */
@Named
public class HistogramTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistogramTrader.class);

    private LoadingCache<Pair<String,String>, HistogramAcrossGenes> histogramsCache;

    @Inject
    public HistogramTrader(final ExperimentTrader experimentTrader,
                           final ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory,
                           final RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory) {
        this.histogramsCache = CacheBuilder.newBuilder().build(new CacheLoader<Pair<String, String>, HistogramAcrossGenes>() {
            @Override
            public HistogramAcrossGenes load(Pair<String, String> accessionAndAccessKey) throws Exception {
                BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(accessionAndAccessKey.getLeft(), accessionAndAccessKey.getRight());
                double [] bins ;
                int [] histogram;
                if(experiment.getType().isProteomicsBaseline()){
                    bins = new CutoffScale.Logarithmic().get();
                    histogram = proteomicsBaselineProfileStreamFactory.histogram(experiment, new BaselineRequestContext(ProteomicsBaselineRequestPreferences.requestAllData(), experiment), bins);
                } else {
                    bins = new CutoffScale.Scaled().get();
                    histogram = rnaSeqBaselineProfileStreamFactory.histogram(experiment, new BaselineRequestContext(RnaSeqBaselineRequestPreferences.requestAllData(), experiment), bins);
                }
                return new HistogramAcrossGenes(histogram, bins);
            }
        });
    }

    public HistogramAcrossGenes get(String experimentAccession, String accessKey) {
        try {
            return histogramsCache.get(Pair.of(experimentAccession,accessKey));
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException("Exception while loading histogram data from file: " + e.getMessage(), e.getCause());
        }
    }
}
