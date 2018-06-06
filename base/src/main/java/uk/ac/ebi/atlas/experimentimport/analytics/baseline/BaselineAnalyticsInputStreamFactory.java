package uk.ac.ebi.atlas.experimentimport.analytics.baseline;

import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Named
public class BaselineAnalyticsInputStreamFactory {
    private final DataFileHub dataFileHub;

    @Inject
    public BaselineAnalyticsInputStreamFactory(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ObjectInputStream<BaselineAnalytics> create(String experimentAccession, ExperimentType experimentType)
            throws IOException {
        checkArgument(experimentType.isBaseline());

        if (experimentType.isProteomicsBaseline()) {
            AtlasResource<?> resource = dataFileHub.getProteomicsBaselineExperimentFiles(experimentAccession).main;
            return new ProteomicsBaselineAnalyticsInputStream(resource.getReader(), resource.toString());
        } else {
            AtlasResource<?> tpms =
                    dataFileHub.getRnaSeqBaselineExperimentFiles(experimentAccession)
                            .dataFile(ExpressionUnit.Absolute.Rna.TPM);

            AtlasResource<?> fpkms =
                    dataFileHub.getRnaSeqBaselineExperimentFiles(experimentAccession)
                            .dataFile(ExpressionUnit.Absolute.Rna.FPKM);

            checkArgument(
                    tpms.exists() || fpkms.exists(),
                    String.format("No FPKMs or TPMs found for %s", experimentAccession));

            return new RnaSeqBaselineAnalyticsInputStream(
                    tpms.exists() ? Optional.of(tpms.getReader()) : Optional.empty(),
                    fpkms.exists() ? Optional.of(fpkms.getReader()) : Optional.empty());
        }
    }
}
