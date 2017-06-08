package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import com.google.common.base.Preconditions;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

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
        if(experimentType.isProteomicsBaseline()){
            AtlasResource<?> resource = dataFileHub.getProteomicsBaselineExperimentFiles(experimentAccession).main;
            return new ProteomicsBaselineAnalyticsInputStream(resource.getReader(), resource.toString());
        } else {
            AtlasResource<?> resource = dataFileHub.getRnaSeqBaselineExperimentFiles(experimentAccession).dataFile(ExpressionUnit.Absolute.Rna.TPM);
            return new RnaSeqBaselineAnalyticsInputStream(resource.getReader(), resource.toString());
        }
    }
}
