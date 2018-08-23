package uk.ac.ebi.atlas.trader.cache.loader;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

@Component
public class ProteomicsBaselineExperimentFactory extends BaselineExperimentFactory {
    public ProteomicsBaselineExperimentFactory(ConfigurationTrader configurationTrader,
                                               SpeciesFactory speciesFactory) {
        super(ExperimentType.PROTEOMICS_BASELINE, configurationTrader, speciesFactory);
    }
}
