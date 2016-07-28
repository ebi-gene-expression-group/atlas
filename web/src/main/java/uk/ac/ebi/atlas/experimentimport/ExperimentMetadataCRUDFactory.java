package uk.ac.ebi.atlas.experimentimport;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterFactory;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
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
    RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    @Inject
    RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCache;

    @Inject
    MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    PublicExperimentTypesCache publicExperimentTypesCache;

    @Inject
    ExperimentDTOBuilder experimentDTOBuilder;

    @Inject
    CondensedSdrfParser condensedSdrfParser;

    @Inject
    ProteomicsBaselineExperimentsCacheLoaderFactory loaderFactory;

    @Inject
    CacheConfiguration cacheConfiguration;

    @Inject
    EFOLookupService efoParentsLookupService;

    public ExperimentMetadataCRUD create(ExperimentDesignFileWriterFactory experimentDesignFileWriterFactory, ExperimentDAO experimentDao, ConditionsIndexTrader conditionsIndexTrader) {

        ProteomicsBaselineExperimentsCacheLoader loader = loaderFactory.create(experimentDao);
        LoadingCache<String, BaselineExperiment> loadingCache = CacheBuilder.newBuilder().maximumSize(1).build(loader);
        ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache = new ProteomicsBaselineExperimentsCache(loadingCache);
        ExperimentTrader experimentTrader = new ExperimentTrader(experimentDao, rnaSeqBaselineExperimentsCache,
                rnaSeqDiffExperimentsCache, microarrayExperimentsCache, proteomicsBaselineExperimentsCache, publicExperimentTypesCache);

        ExperimentMetadataCRUD experimentMetadataCRUD = new ExperimentMetadataCRUD(experimentDao, experimentTrader, experimentDTOBuilder, condensedSdrfParser, efoParentsLookupService);
        experimentMetadataCRUD.setConditionsIndexTrader(conditionsIndexTrader);
        experimentMetadataCRUD.setExperimentDesignFileWriterFactory(experimentDesignFileWriterFactory);

        return experimentMetadataCRUD;

    }
}
