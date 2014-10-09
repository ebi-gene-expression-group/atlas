package uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab;

import com.google.common.collect.SetMultimap;
import uk.ac.ebi.atlas.model.ExperimentDesign;

//TODO: remove this class, because we can get OntologyTermsByAssayAccession from experimentDesign
public class MageTabParserOutput {

    private final SetMultimap<String, String> ontologyTermIdsByAssayAccession;
    private ExperimentDesign experimentDesign;

    MageTabParserOutput(ExperimentDesign experimentDesign, SetMultimap<String, String> ontologyTermIdsByAssayAccession) {
        this.experimentDesign = experimentDesign;
        this.ontologyTermIdsByAssayAccession = ontologyTermIdsByAssayAccession;
    }

    public ExperimentDesign getExperimentDesign() {
        return experimentDesign;
    }


    public SetMultimap<String, String> getOntologyTermIdsByAssayAccession() {
        return ontologyTermIdsByAssayAccession;
    }

}
