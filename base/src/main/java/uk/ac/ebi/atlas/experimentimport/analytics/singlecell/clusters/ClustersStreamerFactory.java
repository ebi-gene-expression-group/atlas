package uk.ac.ebi.atlas.experimentimport.analytics.singlecell.clusters;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.resource.DataFileHub;

@Component
public class ClustersStreamerFactory {
    private final DataFileHub dataFileHub;

    public ClustersStreamerFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ClustersStreamer create(String experimentAccession) {
        return new ClustersStreamer(dataFileHub.getSingleCellExperimentFiles(experimentAccession).clustersTsv);
    }
}
