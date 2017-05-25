package uk.ac.ebi.atlas.experimentimport.analytics.differential.microarray;

import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class MicroarrayDifferentialAnalyticsInputStreamFactory {

    private DataFileHub dataFileHub;

    @Inject
    public MicroarrayDifferentialAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public MicroarrayDifferentialAnalyticsInputStream create(String experimentAccession, String arrayDesign)
    throws IOException {
        AtlasResource<?> analyticsResource =
                dataFileHub.getMicroarrayExperimentFiles(experimentAccession, arrayDesign).analytics;
        return new MicroarrayDifferentialAnalyticsInputStream(analyticsResource.getReader(), experimentAccession);
    }
}
