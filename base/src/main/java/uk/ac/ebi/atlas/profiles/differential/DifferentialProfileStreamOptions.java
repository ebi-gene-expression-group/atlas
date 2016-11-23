package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;

public interface DifferentialProfileStreamOptions extends ProfileStreamOptions<Contrast> {
    Regulation getRegulation();
    String getExperimentAccession();
    double getPValueCutOff();
    double getFoldChangeCutOff();
}
