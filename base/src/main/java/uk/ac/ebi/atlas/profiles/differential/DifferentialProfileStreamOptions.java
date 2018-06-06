package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

public interface DifferentialProfileStreamOptions extends ProfileStreamOptions<Contrast> {
    Regulation getRegulation();
    String getExperimentAccession();
    double getPValueCutoff();
    double getFoldChangeCutoff();
}
