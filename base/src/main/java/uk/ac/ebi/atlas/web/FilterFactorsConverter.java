package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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
            Validate.isTrue(!factor.getType().contains(SEPARATOR), factor+"type should not contain a :");
            Validate.isTrue(!factor.getValue().contains(SEPARATOR),factor+"value should not contain a :");
            serializedFactors.add(factor.getType() + SEPARATOR + factor.getValue());
        }
        try {
            return URLEncoder.encode(StringUtils.join(serializedFactors, ","), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static SortedSet<Factor> deserialize(String csvSerializedFactors) {
        SortedSet<Factor> factors = Sets.newTreeSet();

        if (StringUtils.isBlank(csvSerializedFactors)) {
            return factors;
        }

        String[] serializedFactors;
        try {
            serializedFactors = URLDecoder.decode(csvSerializedFactors, "UTF-8").split(",");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (String serializedFactor : serializedFactors) {
            String[] split = serializedFactor.split(SEPARATOR);

            checkState(split.length == 2, "serialized Factor string should be like TYPE:value, is:"
                    + Arrays.deepToString(split));

            factors.add(new Factor(split[0], split[1]));
        }
        return factors;
    }

}
