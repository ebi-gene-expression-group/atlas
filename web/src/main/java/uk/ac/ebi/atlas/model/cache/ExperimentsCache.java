package uk.ac.ebi.atlas.model.cache;

import uk.ac.ebi.atlas.model.Experiment;

public interface ExperimentsCache<T extends Experiment> {

    T getExperiment(String experimentAccession);
}
