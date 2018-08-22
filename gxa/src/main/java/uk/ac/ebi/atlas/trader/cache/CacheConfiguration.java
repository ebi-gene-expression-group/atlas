package uk.ac.ebi.atlas.trader.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.trader.cache.loader.DifferentialExperimentFactory;
import uk.ac.ebi.atlas.trader.cache.loader.ExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.cache.loader.MicroarrayExperimentFactory;
import uk.ac.ebi.atlas.trader.cache.loader.ProteomicsBaselineExperimentFactory;
import uk.ac.ebi.atlas.trader.cache.loader.RnaSeqBaselineExperimentFactory;

import javax.inject.Inject;

@Configuration
public class CacheConfiguration {
    private final ExperimentDesignParser experimentDesignParser;
    private final GxaExperimentDao experimentDao;
    private final IdfParser idfParser;

    public CacheConfiguration(ExperimentDesignParser experimentDesignParser, GxaExperimentDao experimentDao, IdfParser idfParser) {
        this.experimentDesignParser = experimentDesignParser;
        this.experimentDao = experimentDao;
        this.idfParser = idfParser;
    }

    @Bean(name = "rnaSeqBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment>
    baselineExperimentsCache(RnaSeqBaselineExperimentFactory experimentFactory) {

        return CacheBuilder.newBuilder()
                .build(new ExperimentsCacheLoader<>(experimentDesignParser, experimentDao, experimentFactory, idfParser));
    }

    @Bean(name = "proteomicsBaselineExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, BaselineExperiment>
    proteomicsBaselineExperimentsCache(ProteomicsBaselineExperimentFactory experimentFactory) {

        return CacheBuilder.newBuilder()
                .build(new ExperimentsCacheLoader<>(experimentDesignParser, experimentDao, experimentFactory, idfParser));
    }


    @Bean(name = "differentialExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, DifferentialExperiment>
    differentialExperimentsCache(DifferentialExperimentFactory experimentFactory) {

        return CacheBuilder.newBuilder()
                .build(new ExperimentsCacheLoader<>(experimentDesignParser, experimentDao, experimentFactory, idfParser));
    }

    @Bean(name = "microarrayExperimentsLoadingCache")
    @Inject
    public LoadingCache<String, MicroarrayExperiment>
    microarrayExperimentsCache(MicroarrayExperimentFactory experimentFactory) {

        return CacheBuilder.newBuilder()
                .build(new ExperimentsCacheLoader<>(experimentDesignParser, experimentDao, experimentFactory, idfParser));
    }
}
