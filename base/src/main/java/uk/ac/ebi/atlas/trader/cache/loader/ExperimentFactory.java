package uk.ac.ebi.atlas.trader.cache.loader;

import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;

public interface ExperimentFactory<E extends Experiment> {
    E create(ExperimentDTO experimentDTO, String experimentDescription,
       ExperimentDesign experimentDesign);
}
