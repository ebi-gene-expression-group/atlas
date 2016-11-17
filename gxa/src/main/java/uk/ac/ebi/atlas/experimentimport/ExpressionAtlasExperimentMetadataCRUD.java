package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

// Experiment metadata is sourced from SDRF/IDF files, and stored in experiment design files, the database, and the Solr conditions Index
@Named
@Scope("prototype")
public class ExpressionAtlasExperimentMetadataCRUD extends ExperimentMetadataCRUD {

    @Inject
    public ExpressionAtlasExperimentMetadataCRUD(DataFileHub dataFileHub,
                                                 ExperimentDAO experimentDAO,
                                                 ExpressionAtlasExperimentTrader experimentTrader,
                                                 CondensedSdrfParser condensedSdrfParser,
                                                 AnalyticsIndexerManager analyticsIndexerManager,
                                                 ConditionsIndexTrader conditionsIndexTrader) {
        super(condensedSdrfParser, new ExperimentDesignFileWriterService(dataFileHub),
                conditionsIndexTrader, experimentDAO,
                analyticsIndexerManager, experimentTrader);
    }


}