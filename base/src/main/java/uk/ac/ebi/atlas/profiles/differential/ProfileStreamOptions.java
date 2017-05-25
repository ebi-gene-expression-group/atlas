package uk.ac.ebi.atlas.profiles.differential;

import uk.ac.ebi.atlas.model.DescribesDataColumns;

import java.util.List;

public interface ProfileStreamOptions<DataColumnDescriptor extends DescribesDataColumns> {

    Integer getHeatmapMatrixSize();

    boolean isSpecific();

    List<DataColumnDescriptor> getDataColumnsToReturn();

    List<DataColumnDescriptor> getAllDataColumns();

    String serializationShortString();

}
