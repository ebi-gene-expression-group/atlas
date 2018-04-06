package uk.ac.ebi.atlas.trader.cache.loader;

import org.apache.commons.math3.analysis.function.Sin;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellRnaSeqBaselineExperimentFactory extends SingleCellBaselineExperimentFactory {

    @Inject
    public SingleCellRnaSeqBaselineExperimentFactory(ConfigurationTrader configurationTrader,
                                                     SpeciesFactory speciesFactory) {
        super(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE, speciesFactory);
    }
}
