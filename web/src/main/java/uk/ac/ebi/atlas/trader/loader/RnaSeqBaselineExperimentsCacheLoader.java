
package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RnaSeqBaselineExperimentsCacheLoader extends BaselineExperimentsCacheLoader {

    @Inject
    public RnaSeqBaselineExperimentsCacheLoader(RnaSeqBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                ConfigurationTrader configurationTrader,
                                                SpeciesFactory speciesFactory) {
        super(ExperimentType.RNASEQ_MRNA_BASELINE, expressionLevelFile,configurationTrader, speciesFactory);
    }
}
