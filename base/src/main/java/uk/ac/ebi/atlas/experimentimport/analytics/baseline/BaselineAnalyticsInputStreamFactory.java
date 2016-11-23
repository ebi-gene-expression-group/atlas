package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;
import com.google.common.base.Preconditions;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class BaselineAnalyticsInputStreamFactory {

    private final DataFileHub dataFileHub;

    @Inject
    public BaselineAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ObjectInputStream<BaselineAnalytics> create(String experimentAccession, ExperimentType experimentType) throws IOException {
        Preconditions.checkArgument(experimentType.isBaseline());
        AtlasResource<TsvReader> resource = dataFileHub.getBaselineExperimentFiles(experimentAccession).main;
        return experimentType.isProteomicsBaseline()
                ? new ProteomicsBaselineAnalyticsInputStream(resource.getReader(), resource.toString())
                : new RnaSeqBaselineAnalyticsInputStream(resource.getReader(), resource.toString());
    }
}
