package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
public class FactorsConverter {

    public static final String SEPARATOR = ":";

    public String serializeFactors(Collection<Factor> factors) {
        List<String> serializedFactors = new ArrayList();
        for (Factor factor : factors) {
            serializedFactors.add(serializeFactor(factor));
        }
        return StringUtils.join(serializedFactors, ",");
    }

    public String serializeFactor(Factor factor) {
        return  factor.getType() + SEPARATOR + factor.getValue();
    }

    public Set<Factor> deserialize(Set<String> serializedFactors) {
        Set<Factor> factors = Sets.newHashSet();
        for (String serializedFactor: serializedFactors){
            String[] split = serializedFactor.split(SEPARATOR);

            checkState(split.length == 2, "serialized Factor string should be like TYPE:value");

            factors.add(new Factor(split[0], split[1]));
        }
        return factors;
    }


}
