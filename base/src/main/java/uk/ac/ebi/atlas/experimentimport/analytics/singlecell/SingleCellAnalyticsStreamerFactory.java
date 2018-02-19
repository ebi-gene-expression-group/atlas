package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.DataFileHub.SingleCellExperimentFiles;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SingleCellAnalyticsStreamerFactory {
    private final DataFileHub dataFileHub;

    @Inject
    public SingleCellAnalyticsStreamerFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SingleCellAnalyticsStreamer create(String experimentAccession) {
        SingleCellExperimentFiles scFiles = dataFileHub.getSingleCellExperimentFiles(experimentAccession);
        return new SingleCellAnalyticsStreamer(scFiles.tpmsMatrix, scFiles.geneIdsTsv, scFiles.cellIdsTsv);
    }
}
