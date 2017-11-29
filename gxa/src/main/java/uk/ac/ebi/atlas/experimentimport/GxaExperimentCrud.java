package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.analytics.GxaAnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

// Responsible for:
// - Database: experiments table, and the deprecated expression values tables
// - Design files on disk
// TODO Remove the analyticsLodaderFactory after https://github.com/gxa/atlas/pull/43 has been merged, as Solr will
// TODO become the only source of truth
@Named
public class GxaExperimentCrud extends ExperimentCrud {
    @Inject
    public GxaExperimentCrud(GxaExperimentDao experimentDao,
                             CondensedSdrfParser condensedSdrfParser,
                             ExperimentDesignFileWriterService experimentDesignFileWriterService,
                             ExperimentChecker experimentChecker,
                             GxaAnalyticsLoaderFactory analyticsLoaderFactory,
                             ConfigurationTrader configurationTrader) {
        super(experimentDao,
              condensedSdrfParser,
              experimentDesignFileWriterService,
              experimentChecker,
              analyticsLoaderFactory,
              configurationTrader);
    }
}
