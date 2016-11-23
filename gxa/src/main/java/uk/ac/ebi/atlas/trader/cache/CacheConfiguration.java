package uk.ac.ebi.atlas.trader.cache;

import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTrader;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.trader.cache.loader.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.BarChartTradersCacheLoader;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {

    @Inject
    ArrayExpressClient arrayExpressClient;

    @Inject
    ExperimentDesignParser experimentDesignParser;

    @Inject
    ExperimentDAO experimentDAO;

    @Bean(name="rnaSeqBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> baselineExperimentsCache(RnaSeqBaselineExperimentFactory experimentFactory){

        return CacheBuilder.newBuilder().build(new ExperimentsCacheLoader<>(arrayExpressClient,
                experimentDesignParser, experimentDAO, experimentFactory));

    }

    @Bean(name="proteomicsBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment> proteomicsBaselineExperimentsCache(ProteomicsBaselineExperimentFactory experimentFactory){

        return CacheBuilder.newBuilder().build(new ExperimentsCacheLoader<>(arrayExpressClient,
                experimentDesignParser, experimentDAO, experimentFactory));

    }


    @Bean(name="differentialExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, DifferentialExperiment> differentialExperimentsCache(DifferentialExperimentFactory experimentFactory){

        return CacheBuilder.newBuilder().build(new ExperimentsCacheLoader<>(arrayExpressClient,
                experimentDesignParser, experimentDAO, experimentFactory));

    }

    @Bean(name="microarrayExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, MicroarrayExperiment> microarrayExperimentsCache(MicroarrayExperimentFactory experimentFactory){

        return CacheBuilder.newBuilder().build(new ExperimentsCacheLoader<>(arrayExpressClient,
                experimentDesignParser, experimentDAO, experimentFactory));

    }

    @Bean(name="barChartTradersLoadingCache")
    @Inject
    public LoadingCache<Pair<String,String>, BarChartTrader> barChartTradersCache(BarChartTradersCacheLoader
                                                                             barChartTradersCacheLoader) {

        return CacheBuilder.newBuilder().build(barChartTradersCacheLoader);

    }

    @Bean(name="publicExperimentTypesLoadingCache")
    @Inject
    public LoadingCache<String, ExperimentType> experimentTypesCache(PublicExperimentTypesCacheLoader experimentFactory) {

        return CacheBuilder.newBuilder().build(experimentFactory);

    }
}