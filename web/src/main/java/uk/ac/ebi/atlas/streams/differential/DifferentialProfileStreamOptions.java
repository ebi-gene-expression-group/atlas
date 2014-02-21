package uk.ac.ebi.atlas.streams.differential;

import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.Set;

public interface DifferentialProfileStreamOptions {

    Set<String> getSelectedGeneIDs();

    boolean isSpecific();

    Set<Contrast> getSelectedQueryFactors();

    Set<Contrast> getAllQueryFactors();

    Regulation getRegulation();

    Integer getHeatmapMatrixSize();

    String getExperimentAccession();

    double getCutoff();

}
