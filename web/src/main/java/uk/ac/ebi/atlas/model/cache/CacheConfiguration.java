package uk.ac.ebi.atlas.model.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.barcharts.BarChartTrader;
import uk.ac.ebi.atlas.model.cache.baseline.BarChartTraderLoader;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentLoader;
import uk.ac.ebi.atlas.model.cache.differential.DifferentialExperimentLoader;
import uk.ac.ebi.atlas.model.cache.microarray.MicroarrayExperimentLoader;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {

    private static final int EXPERIMENTS_CACHE_MAX_SIZE = 100;

    @Bean(name="baselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> baselineExperimentsCache(BaselineExperimentLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE).build(cacheLoader);

    }


    @Bean(name="differentialExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, DifferentialExperiment> differentialExperimentsCache(DifferentialExperimentLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE).build(cacheLoader);

    }

    @Bean(name="microarrayExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, MicroarrayExperiment> microarrayExperimentsCache(MicroarrayExperimentLoader cacheLoader){

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE).build(cacheLoader);

    }

    @Bean(name="barChartTradersLoadingCache")
    @Inject
    public LoadingCache<String, BarChartTrader> barChartTradersCache(BarChartTraderLoader barChartTraderLoader) {

        return CacheBuilder.newBuilder().maximumSize(EXPERIMENTS_CACHE_MAX_SIZE).build(barChartTraderLoader);

    }

}