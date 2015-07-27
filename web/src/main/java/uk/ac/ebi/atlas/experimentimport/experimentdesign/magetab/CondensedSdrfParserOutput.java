package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.collect.ImmutableSet;
import uk.ac.ebi.atlas.model.ExperimentDesign;

public class CondensedSdrfParserOutput {

    private String title;
    ImmutableSet<String> pubmedIds;
    private ExperimentDesign experimentDesign;

    CondensedSdrfParserOutput(String title, ImmutableSet<String> pubmedIds, ExperimentDesign experimentDesign) {
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
}
