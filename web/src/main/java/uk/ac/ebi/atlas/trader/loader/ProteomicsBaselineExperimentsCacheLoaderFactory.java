package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProteomicsBaselineExperimentsCacheLoaderFactory {

    private ConfigurationTrader configurationTrader;
    private SpeciesFactory speciesFactory;
    private ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile;
    private ArrayExpressClient arrayExpressClient;
    private ExperimentDesignParser experimentDesignParser;

    @Inject
    public ProteomicsBaselineExperimentsCacheLoaderFactory(ConfigurationTrader configurationTrader,
                                                           SpeciesFactory speciesFactory,
                                                           ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                           ArrayExpressClient arrayExpressClient,
                                                           ExperimentDesignParser experimentDesignParser) {
        this.configurationTrader = configurationTrader;
        this.speciesFactory = speciesFactory;
        this.expressionLevelFile = expressionLevelFile;
        this.arrayExpressClient = arrayExpressClient;
        this.experimentDesignParser = experimentDesignParser;
    }

    public ProteomicsBaselineExperimentsCacheLoader create(ExperimentDAO experimentDao) {

        ProteomicsBaselineExperimentsCacheLoader loader =
                new ProteomicsBaselineExperimentsCacheLoader(
                        expressionLevelFile, configurationTrader, speciesFactory);

        loader.setExperimentDAO(experimentDao);
        loader.setArrayExpressClient(arrayExpressClient);
        loader.setExperimentDesignParser(experimentDesignParser);

        return loader;
    }

}
