package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.baseline.Factor;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkState;

public class FilterFactorsConverter {

    private static final String SEPARATOR = ":";

    private FilterFactorsConverter() {
    }

    public static String prettyPrint(Iterable<Factor> factors) {
        if (!factors.iterator().hasNext()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Factor factor : factors) {
            sb.append(factor.getValue()).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public static String serialize(Iterable<Factor> factors) {
        List<String> serializedFactors = new ArrayList<>();
        for (Factor factor : factors) {
            serializedFactors.add(factor.getType() + SEPARATOR + factor.getValue());
        }
        return StringUtils.join(serializedFactors, ",");
    }

    public static SortedSet<Factor> deserialize(String csvSerializedFactors) {
        SortedSet<Factor> factors = Sets.newTreeSet();

        if (StringUtils.isBlank(csvSerializedFactors)) {
            return factors;
        }

        String[] serializedFactors = csvSerializedFactors.split(",");
        for (String serializedFactor : serializedFactors) {
            String[] split = serializedFactor.split(SEPARATOR);

            checkState(split.length == 2, "serialized Factor string should be like TYPE:value");

            factors.add(new Factor(split[0], split[1]));
        }
        return factors;
    }

}
