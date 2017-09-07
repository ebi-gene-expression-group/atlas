package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.resource.SingleCellDataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellBaselineInputStreamFactory {

    private final SingleCellDataFileHub dataFileHub;

    @Inject
    public SingleCellBaselineInputStreamFactory(SingleCellDataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SingleCellBaselineInputStream create(String experimentAccession) throws IOException {
        return new SingleCellBaselineInputStream(dataFileHub.getSingleCellExperimentFiles(experimentAccession).data.get());
    }
}