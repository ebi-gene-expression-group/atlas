package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.DataFileHub.SingleCellExperimentFiles;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellAnalyticsStreamFactory {
    private final DataFileHub dataFileHub;

    @Inject
    public SingleCellAnalyticsStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SingleCellAnalyticsStream create(String experimentAccession) throws IOException {
        SingleCellExperimentFiles scFiles = dataFileHub.getSingleCellExperimentFiles(experimentAccession);
        return new SingleCellAnalyticsStream(scFiles.tpmsMatrix, scFiles.geneIdsTsv, scFiles.cellIdsTsv);
    }
}
