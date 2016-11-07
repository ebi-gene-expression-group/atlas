package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

public class CondensedSdrfParserOutput {

    private String experimentAccession;
    private ExperimentType experimentType;
    private String title;
    private ImmutableSet<String> pubmedIds;
    private ExperimentDesign experimentDesign;

    CondensedSdrfParserOutput(String experimentAccession, ExperimentType experimentType, String title, ImmutableSet<String> pubmedIds, ExperimentDesign experimentDesign) {
        this.experimentAccession = experimentAccession;
        this.experimentType = experimentType;
        this.title = title;
        this.pubmedIds = pubmedIds;
        this.experimentDesign = experimentDesign;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    public String getTitle() {
        return title;
    }

    public ImmutableSet<String> getPubmedIds() {
        return pubmedIds;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }
}
