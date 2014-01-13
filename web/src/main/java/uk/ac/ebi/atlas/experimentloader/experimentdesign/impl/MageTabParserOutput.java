package uk.ac.ebi.atlas.experimentloader.experimentdesign.impl;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.model.ExperimentDesign;

public class MageTabParserOutput {

    private final SetMultimap<String, String> ontologyTerms;
    private ExperimentDesign experimentDesign;

    MageTabParserOutput(ExperimentDesign experimentDesign, SetMultimap<String, String> ontologyTerms) {
        this.experimentDesign = experimentDesign;
        this.ontologyTerms = ontologyTerms;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }

    // ontology terms by assay group ID
    public SetMultimap<String, String> getOntologyTerms() {
        return ontologyTerms;
    }

}
