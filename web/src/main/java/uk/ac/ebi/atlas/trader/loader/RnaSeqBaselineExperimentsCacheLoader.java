
package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqBaselineExperimentsCacheLoader extends BaselineExperimentsCacheLoader {

    @Inject
    public RnaSeqBaselineExperimentsCacheLoader(RnaSeqBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                ConfigurationTrader configurationTrader,
                                                SpeciesKingdomTrader speciesKingdomTrader) {
        super(ExperimentType.RNASEQ_MRNA_BASELINE, expressionLevelFile,configurationTrader, speciesKingdomTrader);
    }
}
