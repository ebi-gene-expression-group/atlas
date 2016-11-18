package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.resource.DataFileHub;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexingService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class ExpressionAtlasExperimentMetadataCRUD extends ExperimentMetadataCRUD {


    @Inject
    public ExpressionAtlasExperimentMetadataCRUD(CondensedSdrfParser condensedSdrfParser,
                                                 ExperimentDesignFileWriterService experimentDesignFileWriterService,
                                                 ConditionsIndexingService conditionsIndexingService,
                                                 ExperimentDAO experimentDAO,
                                                 AnalyticsIndexerManager analyticsIndexerManager,
                                                 ExperimentTrader experimentTrader) {
        super(condensedSdrfParser, experimentDesignFileWriterService, conditionsIndexingService, experimentDAO, analyticsIndexerManager, experimentTrader);
    }
}