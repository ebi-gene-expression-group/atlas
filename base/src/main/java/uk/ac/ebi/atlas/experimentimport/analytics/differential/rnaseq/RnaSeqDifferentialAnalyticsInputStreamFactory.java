package uk.ac.ebi.atlas.experimentimport.analytics.differential.rnaseq;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class RnaSeqDifferentialAnalyticsInputStreamFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public RnaSeqDifferentialAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public RnaSeqDifferentialAnalyticsInputStream create(String experimentAccession) throws IOException {
        AtlasResource<TsvReader> resource = dataFileHub.getDifferentialExperimentFiles(experimentAccession).analytics;
        return new RnaSeqDifferentialAnalyticsInputStream(resource.getReader(), resource.toString());
    }
}
