package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.tsne;


import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TSnePlotStreamerFactory {
    private final DataFileHub dataFileHub;

    @Inject
    public TSnePlotStreamerFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public TSnePlotStreamer create(String experimentAccession) {
        DataFileHub.SingleCellExperimentFiles scFiles = dataFileHub.getSingleCellExperimentFiles(experimentAccession);

        return new TSnePlotStreamer(scFiles.tSnePlotTsvs);
    }
}
