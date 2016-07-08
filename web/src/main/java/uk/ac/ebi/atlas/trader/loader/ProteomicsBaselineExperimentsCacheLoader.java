
package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ProteomicsBaselineExperimentsCacheLoader extends BaselineExperimentsCacheLoader {

    @Inject
    public ProteomicsBaselineExperimentsCacheLoader(ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                       ConfigurationTrader configurationTrader, SpeciesKingdomTrader speciesKingdomTrader) {
        super(ExperimentType.PROTEOMICS_BASELINE, expressionLevelFile, configurationTrader, speciesKingdomTrader);
    }
}
