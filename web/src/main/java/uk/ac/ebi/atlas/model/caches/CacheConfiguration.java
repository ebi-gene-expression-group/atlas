package uk.ac.ebi.atlas.model.caches;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.utils.score.HistogramCountLoader;
import uk.ac.ebi.atlas.utils.score.HistogramCounter;

import javax.inject.Inject;
import javax.inject.Named;

@Configuration
public class CacheConfiguration {

    private static final int EXPERIMENTS_CACHE_MAX_SIZE = 10;

    @Bean(name="experimentRunsCache")
    @Inject
    public LoadingCache<String, Experiment> experimentRunsCache(ExperimentMetadataLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE)
                                        .build(cacheLoader);

    }

    @Bean(name="histogramCountersCache")
    @Inject
    public LoadingCache<String, HistogramCounter> histogramCounterCache(HistogramCountLoader histogramCountLoader) {
        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE)
                .build(histogramCountLoader);

    }

}