package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.DescribesDataColumns;

import java.util.List;

public interface ProfileStreamOptions<D extends DescribesDataColumns> {
    Integer getHeatmapMatrixSize();
    boolean isSpecific();
    List<D> getDataColumnsToReturn();
    List<D> getAllDataColumns();
}
