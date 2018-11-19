package uk.ac.ebi.atlas.model.card;

import uk.ac.ebi.atlas.model.experiment.Experiment;

public interface UrlHelpers {
    String getExperimentsFilteredBySpeciesUrl(String species);
    String getExperimentUrl(Experiment experiment);
}
