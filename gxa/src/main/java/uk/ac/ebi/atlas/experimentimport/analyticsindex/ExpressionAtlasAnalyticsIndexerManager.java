package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentSorter;
import uk.ac.ebi.atlas.utils.ExpressionAtlasBioentityIdentifiersReader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExpressionAtlasAnalyticsIndexerManager extends AnalyticsIndexerManager {

    @Inject
    public ExpressionAtlasAnalyticsIndexerManager(AnalyticsIndexerService analyticsIndexerService,
                                                  AnalyticsIndexerMonitor analyticsIndexerMonitor,
                                                  DataFileHub dataFileHub,
                                                  ExpressionAtlasExperimentTrader experimentTrader,
                                                  ExpressionAtlasBioentityIdentifiersReader bioentityIdentifiersReader,
                                                  BioentityPropertiesDao bioentityPropertiesDao) {
        super(new ExperimentSorter(dataFileHub, experimentTrader), analyticsIndexerMonitor,
                bioentityIdentifiersReader, analyticsIndexerService, experimentTrader, bioentityPropertiesDao);
    }
}
