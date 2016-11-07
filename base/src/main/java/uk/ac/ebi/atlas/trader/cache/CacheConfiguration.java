package uk.ac.ebi.atlas.trader.cache;

import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTrader;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.cache.loader.DifferentialExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.MicroarrayExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.ProteomicsBaselineExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.PublicExperimentTypesCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.RnaSeqBaselineExperimentsCacheLoader;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTradersCacheLoader;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {

    @Bean(name="rnaSeqBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> baselineExperimentsCache(RnaSeqBaselineExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="proteomicsBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> proteomicsBaselineExperimentsCache(ProteomicsBaselineExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }


    @Bean(name="differentialExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, DifferentialExperiment> differentialExperimentsCache(DifferentialExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="microarrayExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, MicroarrayExperiment> microarrayExperimentsCache(MicroarrayExperimentsCacheLoader cacheLoader){

        return CacheBuilder.newBuilder().build(cacheLoader);

    }

    @Bean(name="barChartTradersLoadingCache")
    @Inject
    public LoadingCache<String, BarChartTrader> barChartTradersCache(BarChartTradersCacheLoader barChartTradersCacheLoader) {

        return CacheBuilder.newBuilder().build(barChartTradersCacheLoader);

    }

    @Bean(name="publicExperimentTypesLoadingCache")
    @Inject
    public LoadingCache<String, ExperimentType> experimentTypesCache(PublicExperimentTypesCacheLoader cacheLoader) {

        return CacheBuilder.newBuilder().build(cacheLoader);

    }
}