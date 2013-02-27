package uk.ac.ebi.atlas.web;

import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Named
public class FilterParametersConverter {

    public String serializeFactors(Collection<Factor> factors) {
        return "";
    }

    public Set<Factor> deserialize(Set<String> serializedFactors) {

        return null;
    }



}
