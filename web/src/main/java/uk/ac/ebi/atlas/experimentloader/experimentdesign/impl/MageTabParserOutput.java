package uk.ac.ebi.atlas.experimentloader.experimentdesign.impl;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.model.ExperimentDesign;

public class MageTabParserOutput {

    private final SetMultimap<String, String> characteristicsOntologyTerms;
    private ExperimentDesign experimentDesign;

    MageTabParserOutput(ExperimentDesign experimentDesign, SetMultimap<String, String> characteristicsOntologyTerms) {
        this.experimentDesign = experimentDesign;
        this.characteristicsOntologyTerms = characteristicsOntologyTerms;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    // ontology terms by assay group ID
    public SetMultimap<String, String> getCharacteristicsOntologyTerms() {
        return characteristicsOntologyTerms;
    }

}
