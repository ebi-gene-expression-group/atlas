package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.auto.value.AutoValue;
import com.google.common.base.Objects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutionException;

/*
If you want to support drawing the charts for arbitrary columns of data
(we used to draw a chart corresponding to the query if some columns were selected)
- add them as cache keys
- set them as stream options
- make the StreamOptions.equals() take more parameters into account
- make the UI make sense

The last part became harder because choices of filters and cutoffs now appear orthogonal in the UI
 */
public class HistogramService<O extends ProfileStreamOptions<?>, E extends Experiment<?>> {

    protected final ExperimentTrader experimentTrader;
    private final LoadingCache<HistogramCacheKey<O>, HistogramAcrossGenes> cache;


    public HistogramService(final ProfileStreamFactory<?, ?, E, O, ?> profileStreamFactory,
                            final ExperimentTrader experimentTrader,
                            final double[] cutoffBins) {
        this.experimentTrader = experimentTrader;
        this.cache = CacheBuilder.newBuilder().build(new CacheLoader<HistogramCacheKey<O>, HistogramAcrossGenes>() {
            @Override
            public HistogramAcrossGenes load(@Nonnull HistogramCacheKey<O> cacheKey) {
                return new HistogramAcrossGenes(
                        profileStreamFactory.histogram(
                                (E) experimentTrader.getExperiment(cacheKey.accession(), cacheKey.accessKey()),
                                cacheKey.streamOptions(),
                                cutoffBins),
                        cutoffBins);
            }
        });
    }

    protected HistogramAcrossGenes get(String accession, String accessKey, O streamOptions) {
        try {
            return cache.get(HistogramCacheKey.create(accession, accessKey, streamOptions));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    static class
    Baseline<U extends ExpressionUnit.Absolute, P extends BaselineRequestPreferences<U>>
            extends HistogramService<BaselineProfileStreamOptions<U>, BaselineExperiment> {

        Baseline(ProfileStreamFactory<?, ?, BaselineExperiment, BaselineProfileStreamOptions<U>, ?>
                         profileStreamFactory,
                 ExperimentTrader experimentTrader,
                double[] cutoffBins) {
            super(profileStreamFactory, experimentTrader, cutoffBins);
        }

        public HistogramAcrossGenes get(String accession, String accessKey, P preferences) {
            return get(
                    accession,
                    accessKey,
                    new BaselineRequestContext<>(
                            preferences,
                            (BaselineExperiment) experimentTrader.getExperiment(accession, accessKey)));
        }
    }

    public static class RnaSeq
            extends Baseline<ExpressionUnit.Absolute.Rna, RnaSeqBaselineRequestPreferences> {

        public RnaSeq(RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory,
                      ExperimentTrader experimentTrader) {
            super(rnaSeqBaselineProfileStreamFactory, experimentTrader, new CutoffScale.Scaled().get());
        }

    }

    public static class Proteomics
            extends Baseline<ExpressionUnit.Absolute.Protein, ProteomicsBaselineRequestPreferences> {

        public Proteomics(ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory,
                          ExperimentTrader experimentTrader) {
            super(proteomicsBaselineProfileStreamFactory, experimentTrader, new CutoffScale.Logarithmic().get());
        }

    }

    @AutoValue
    public abstract static class HistogramCacheKey<O extends ProfileStreamOptions<?>> {
        abstract String accession();
        abstract String accessKey();
        abstract O streamOptions();

        static <O extends ProfileStreamOptions<?>> HistogramCacheKey<O> create(String accession,
                                                                               String accessKey,
                                                                               O streamOptions) {
            return new AutoValue_HistogramService_HistogramCacheKey<>(accession, accessKey, streamOptions);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }

            if ((null == object) || (getClass() != object.getClass())) {
                return false;
            }

            HistogramCacheKey other = (HistogramCacheKey) object;

            return Objects.equal(this.accession(), other.accession()) &&
                   Objects.equal(this.accessKey(), other.accessKey());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(accession(), accessKey());
        }
    }
}
