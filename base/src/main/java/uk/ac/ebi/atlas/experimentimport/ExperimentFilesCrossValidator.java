package uk.ac.ebi.atlas.experimentimport;

import com.atlassian.util.concurrent.LazyReference;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;

import java.text.MessageFormat;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class ExperimentFilesCrossValidator {

    private final ExperimentConfiguration experimentConfiguration;
    private final ExperimentDesign experimentDesign;
    private LazyReference<List<AssayGroup>> assayGroups = new LazyReference<List<AssayGroup>>() {
        @Override
        protected List<AssayGroup> create() throws Exception {
            return experimentConfiguration.getAssayGroups();
        }
    };

    public ExperimentFilesCrossValidator(ExperimentConfiguration experimentConfiguration,
                                         ExperimentDesign experimentDesign) {
        this.experimentConfiguration = experimentConfiguration;
        this.experimentDesign = experimentDesign;
    }


    public void validate() throws IllegalStateException {
        checkState(!assayGroups.get().isEmpty(), "Experiment config appears to be without assay groups!");
        factorsNotMissing();
        sampleCharacteristicsNotMissing();
    }


    void factorsNotMissing() {
        for (AssayGroup assayGroup: assayGroups.get()) {
            for (String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()) {
                checkState(!experimentDesign.getFactorValues(assayGroupId).isEmpty(),
                        MessageFormat.format("Factor values for assay id {0} missing from experiment design file!",
                                assayGroupId));
            }
        }
    }

    void sampleCharacteristicsNotMissing() {
        for (AssayGroup assayGroup: assayGroups.get()) {
            for (String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()) {
                checkState(!experimentDesign.getSampleCharacteristics(assayGroupId).isEmpty(),
                        MessageFormat.format(
                                "Sample characteristics values for assay id {0} missing from experiment design file!",
                                assayGroupId));
            }
        }
    }
}
