package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.analytics.ScxaAnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

// Responsible for:
// - Database: experiments table and expression values tables
// - Design files on disk
// TODO Remove the analyticsLodaderFactory after https://github.com/gxa/atlas/pull/43 has been merged, as Solr will
// TODO become the only source of truth
@Named
public class ScxaExperimentCrud extends ExperimentCrud {
    @Inject
    public ScxaExperimentCrud(ScxaExperimentDao experimentDao,
                              CondensedSdrfParser condensedSdrfParser,
                              ExperimentDesignFileWriterService experimentDesignFileWriterService,
                              ExperimentChecker experimentChecker,
                              ScxaAnalyticsLoaderFactory analyticsLoaderFactory,
                              ConfigurationTrader configurationTrader) {
        super(experimentDao,
              condensedSdrfParser,
              experimentDesignFileWriterService,
              experimentChecker,
              analyticsLoaderFactory,
              configurationTrader);
    }
}
