package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.model.ExperimentType;

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
