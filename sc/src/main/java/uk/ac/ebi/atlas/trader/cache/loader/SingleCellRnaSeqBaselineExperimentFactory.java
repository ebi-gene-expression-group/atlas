package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellRnaSeqBaselineExperimentFactory extends BaselineExperimentFactory {

    @Inject
    public SingleCellRnaSeqBaselineExperimentFactory(ConfigurationTrader configurationTrader,
                                                     SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
        super(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE,configurationTrader, speciesFactory, dataFileHub);
    }
}
