package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.model.ExperimentType;

public interface ExperimentChecker {
    void checkAllFiles(String experimentAccession, ExperimentType experimentType);
}
