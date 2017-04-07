package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            Set<ImmutableSet<Map.Entry<String, String>>> factorsPerAssayForThisAssayGroup = new HashSet<>();
            for(String assayGroupId: assayGroup.assaysAnalyzedForThisDataColumn()){
                Map<String, String> factorValues = experimentDesign.getFactorValues(assayGroupId);
                factorsPerAssayForThisAssayGroup.add(ImmutableSet.copyOf(factorValues.entrySet()));
            }
            checkState(factorsPerAssayForThisAssayGroup.size() == 1,
                    MessageFormat.format("Factor values inconsistent across assays in assay group {0}!", assayGroup.getId()));
        }
    }
}
