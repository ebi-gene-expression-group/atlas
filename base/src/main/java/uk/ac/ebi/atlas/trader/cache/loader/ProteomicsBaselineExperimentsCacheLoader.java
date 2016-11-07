package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProteomicsBaselineExperimentsCacheLoader extends BaselineExperimentsCacheLoader {

    /*
    This is a bean because ProteomicsBaselineExperimentExpressionLevelFile is also a bean, with prototype scope.
    They don't need to be.
    Wojtek thinks it's not great but currently has bigger fish to fry.
     */
    @Inject
    public ProteomicsBaselineExperimentsCacheLoader(ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                    ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory) {
        super(ExperimentType.PROTEOMICS_BASELINE, expressionLevelFile, configurationTrader, speciesFactory);
    }
}
