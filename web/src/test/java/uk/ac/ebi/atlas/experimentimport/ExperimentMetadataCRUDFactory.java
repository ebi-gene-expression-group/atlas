package uk.ac.ebi.atlas.experimentimport;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.MageTabParserFactory;
import uk.ac.ebi.atlas.model.baseline.ProteomicsBaselineExperiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.*;
import uk.ac.ebi.atlas.trader.loader.ProteomicsBaselineExperimentsCacheLoader;
import uk.ac.ebi.atlas.trader.loader.ProteomicsBaselineExperimentsCacheLoaderFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExperimentMetadataCRUDFactory {

    @Inject
    ConfigurationTrader configurationTrader;

    @Inject
    BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    PublicExperimentTypesCache publicExperimentTypesCache;

    @Inject
    ExperimentDTOBuilder experimentDTOBuilder;

    @Inject
    MageTabParserFactory mageTabParserFactory;

    @Inject
    CondensedSdrfParser condensedSdrfParser;

    @Inject
    ProteomicsBaselineExperimentsCacheLoaderFactory loaderFactory;

    @Inject
    CacheConfiguration cacheConfiguration;

    @Inject
    EFOParentsLookupService efoParentsLookupService;

    public ExperimentMetadataCRUD create(ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder, ExperimentDAO experimentDao, ConditionsIndexTrader conditionsIndexTrader) {
        ProteomicsBaselineExperimentsCacheLoader loader = loaderFactory.create(experimentDao);
        LoadingCache<String, ProteomicsBaselineExperiment> loadingCache = CacheBuilder.newBuilder().maximumSize(1).build(loader);
        ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache = new ProteomicsBaselineExperimentsCache(loadingCache);
        ExperimentTrader experimentTrader = new ExperimentTrader(experimentDao, baselineExperimentsCache, rnaSeqDiffExperimentsCache, microarrayExperimentsCache, proteomicsBaselineExperimentsCache, publicExperimentTypesCache);
        return new ExperimentMetadataCRUD(experimentDao, experimentDesignFileWriterBuilder,
                experimentTrader, experimentDTOBuilder, condensedSdrfParser, mageTabParserFactory, conditionsIndexTrader, efoParentsLookupService);
    }
}
