package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.Set;

public interface DifferentialProfileStreamOptions extends ProfileStreamOptions {

    Set<String> getSelectedGeneIDs();

    boolean isSpecific();

    Set<Contrast> getSelectedQueryFactors();

    Set<Contrast> getAllQueryFactors();

    Regulation getRegulation();

    String getExperimentAccession();

    double getPValueCutOff();

    double getFoldChangeCutOff();
}
