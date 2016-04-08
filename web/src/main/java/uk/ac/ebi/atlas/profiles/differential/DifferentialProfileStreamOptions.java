package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.Set;

public interface DifferentialProfileStreamOptions extends ProfileStreamOptions<Contrast> {


    Regulation getRegulation();

    String getExperimentAccession();

    double getPValueCutOff();

    double getFoldChangeCutOff();
}
