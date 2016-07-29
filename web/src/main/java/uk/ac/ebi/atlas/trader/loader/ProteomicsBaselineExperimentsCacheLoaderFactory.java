package uk.ac.ebi.atlas.trader.loader;

import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactorsBuilder;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProteomicsBaselineExperimentsCacheLoaderFactory {

    @Inject
    private ConfigurationTrader configurationTrader;


    @Inject
    private SpeciesFactory speciesFactory;

    @Inject
    private ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile;

    @Inject
    private ArrayExpressClient arrayExpressClient;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    public ProteomicsBaselineExperimentsCacheLoader create(ExperimentDAO experimentDao) {

        ProteomicsBaselineExperimentsCacheLoader loader = new ProteomicsBaselineExperimentsCacheLoader
                (expressionLevelFile, configurationTrader, speciesFactory);

        loader.setExtraInfoPathTemplate(extraInfoPathTemplate);
        loader.setExperimentDAO(experimentDao);
        loader.setArrayExpressClient(arrayExpressClient);
        loader.setExperimentDesignParser(experimentDesignParser);

        return loader;
    }

}
