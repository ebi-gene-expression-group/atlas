package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

public class CondensedSdrfParserOutput {

    private String experimentAccession;
    private ExperimentType experimentType;
    private ExperimentDesign experimentDesign;

    CondensedSdrfParserOutput(String experimentAccession, ExperimentType experimentType, ExperimentDesign experimentDesign) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.experimentDesign = experimentDesign;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }
}
