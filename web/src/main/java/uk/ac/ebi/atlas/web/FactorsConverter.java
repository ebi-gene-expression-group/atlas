package uk.ac.ebi.atlas.web;

import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Named
public class FactorsConverter {

    public static final String FACTOR_SEPARATOR = ":";

    public String serializeFactors(Collection<Factor> factors) {
        List<String> serializedFactors = new ArrayList();
        for (Factor factor : factors) {
            serializedFactors.add(factor.getType() + FACTOR_SEPARATOR + factor.getValue());
        }
        return StringUtils.join(serializedFactors, ",");
    }

    public Set<Factor> deserialize(Set<String> serializedFactors) {

        return null;
    }



}
