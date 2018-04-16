package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellRnaSeqBaselineExperimentFactory extends SingleCellBaselineExperimentFactory {

    @Inject
    public SingleCellRnaSeqBaselineExperimentFactory(SpeciesFactory speciesFactory) {
        super(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE, speciesFactory);
    }
}
