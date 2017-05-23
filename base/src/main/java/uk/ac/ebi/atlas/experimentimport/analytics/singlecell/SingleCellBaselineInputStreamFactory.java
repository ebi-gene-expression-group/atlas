package uk.ac.ebi.atlas.experimentimport.analytics.singlecell;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class SingleCellBaselineInputStreamFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public SingleCellBaselineInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public SingleCellBaselineInputStream create(String experimentAccession) throws IOException {
        AtlasResource<TsvReader> resource = dataFileHub.getSingleCellExperimentFiles(experimentAccession).singlecell;
        return new SingleCellBaselineInputStream(resource.getReader(), resource.toString());
    }
}