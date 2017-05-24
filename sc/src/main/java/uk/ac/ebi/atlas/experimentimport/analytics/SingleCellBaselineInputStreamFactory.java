package uk.ac.ebi.atlas.experimentimport.analytics;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.SingleCellFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellBaselineInputStreamFactory {

    private final SingleCellFileHub dataFileHub;

    @Inject
    public SingleCellBaselineInputStreamFactory(SingleCellFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SingleCellBaselineInputStream create(String experimentAccession) throws IOException {
        AtlasResource<TsvReader> resource = dataFileHub.getSingleCellExperimentFiles(experimentAccession).singlecell;
        return new SingleCellBaselineInputStream(resource.getReader(), resource.toString());
    }
}