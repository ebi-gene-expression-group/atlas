package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkState;

public class ExperimentFilesCrossValidator {

    private final ExperimentConfiguration experimentConfiguration;
    private final ExperimentDesign experimentDesign;

    public ExperimentFilesCrossValidator(ExperimentConfiguration experimentConfiguration, ExperimentDesign experimentDesign){
        this.experimentConfiguration = experimentConfiguration;
        this.experimentDesign = experimentDesign;
    }


    public void validate() throws IllegalStateException {
        factorsNotMissing();
        sampleCharacteristicsNotMissing();
        factorsConsistentWithinAssay();
    }


    void factorsNotMissing(){
        for(AssayGroup assayGroup: experimentConfiguration.getAssayGroups()){
            for(String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()){
                checkState(! experimentDesign.getFactorValues(assayGroupId).isEmpty(),
                        MessageFormat.format("Factor values for assay id {0} missing from experiment design file!",
                                assayGroupId));
            }
        }
    }

    void sampleCharacteristicsNotMissing(){
        for(AssayGroup assayGroup: experimentConfiguration.getAssayGroups()){
            for(String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()){
                checkState(! experimentDesign.getSampleCharacteristics(assayGroupId).isEmpty(),
                        MessageFormat.format("Sample characteristics values for assay id {0} missing from experiment design file!",
                                assayGroupId));
            }
        }
    }

    void factorsConsistentWithinAssay(){

        for(AssayGroup assayGroup : experimentConfiguration.getAssayGroups()){
            Set<Set<Map.Entry<String, String>>> factorsPerAssayForThisAssayGroup = new HashSet<>();
            for(String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()){
                Map<String, String> factorValues = experimentDesign.getFactorValues(assayGroupId);

                /*
                We allow differential experiments with factor type "block" e.g. E-MTAB-4442.
                They're introduced for the pipeline, to facilitate processing experiments with a particular type of batch effects.
                Differential experiments as of 5 Sep 2017 will work fine without an unambiguous assay group -> factor group mapping.
                We continue to make assertions for differential experiments too - to flag up cases with inconsistent factor values within assay group.
                They're probably not wanted but future developers should feel free to revisit this assertion or remove it.
                 */
                factorsPerAssayForThisAssayGroup.add(factorValues.entrySet().stream().filter(e -> !experimentConfiguration.getExperimentType().isDifferential() || !e.getKey().equals("block")).collect(Collectors.toSet()));
            }
            checkState(factorsPerAssayForThisAssayGroup.size() == 1,
                    MessageFormat.format("Factor values inconsistent across assays in assay group {0}!", assayGroup.getId()));
        }
    }
}
