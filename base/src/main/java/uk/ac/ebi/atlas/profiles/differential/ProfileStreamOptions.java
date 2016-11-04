package uk.ac.ebi.atlas.profiles.differential;

import java.util.Set;

public interface ProfileStreamOptions<T> {

    Integer getHeatmapMatrixSize();

    boolean isSpecific();

    Set<T> getSelectedQueryFactors();

    Set<T> getAllQueryFactors();

}
