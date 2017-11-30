package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExperimentCrudFactory {
    private final CondensedSdrfParser condensedSdrfParser;
    private final ExperimentDesignFileWriterService experimentDesignFileWriterService;
    private final ConfigurationTrader configurationTrader;

    @Inject
    public ExperimentCrudFactory(CondensedSdrfParser condensedSdrfParser,
                                 ExperimentDesignFileWriterService experimentDesignFileWriterService,
                                 ConfigurationTrader configurationTrader) {
        this.condensedSdrfParser = condensedSdrfParser;
        this.experimentDesignFileWriterService = experimentDesignFileWriterService;
        this.configurationTrader = configurationTrader;
    }

    public ExperimentCrud create(ExperimentDao experimentDao,
                                        ExperimentChecker experimentChecker,
                                        AnalyticsLoaderFactory analyticsLoaderFactory) {
        return new ExperimentCrud(
                experimentDao,
                experimentChecker,
                analyticsLoaderFactory,
                condensedSdrfParser,
                experimentDesignFileWriterService,
                configurationTrader);
    }
}
