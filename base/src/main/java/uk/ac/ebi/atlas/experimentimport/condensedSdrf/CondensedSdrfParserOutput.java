package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

public class CondensedSdrfParserOutput {

    private String experimentAccession;
    private ExperimentType experimentType;
    private ExperimentDesign experimentDesign;
    private String species;

    CondensedSdrfParserOutput(String experimentAccession,
                              ExperimentType experimentType,
                              ExperimentDesign experimentDesign,
                              String species) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.experimentDesign = experimentDesign;
        this.species = species;
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

    public String getSpecies() {
        return species;
    }

}
