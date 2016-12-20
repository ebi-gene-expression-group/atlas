package uk.ac.ebi.atlas.web;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkState;

public class FilterFactorsConverter {

    private static final String INTRA_SEPARATOR = ":";
    private static final String ESCAPE = "_esc_";
    private static final String INTRA_SEPARATOR_ESCAPED = ESCAPE+INTRA_SEPARATOR;
    private static final String INTER_SEPARATOR = ",";
    private static final String INTER_SEPARATOR_ESCAPED = ESCAPE+INTER_SEPARATOR;
    //a filter factor containing ESCAPE+SEPARATOR or ESCAPE at the end will break this but wouldn't it be silly?

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

    private static String toSafe(String input){
        return input
                .replace(INTRA_SEPARATOR, INTRA_SEPARATOR_ESCAPED)
                .replace(INTER_SEPARATOR, INTER_SEPARATOR_ESCAPED);
    }

    private static String fromSafe(String input){
        return input
                .replace(INTER_SEPARATOR_ESCAPED,INTER_SEPARATOR)
                .replace(INTRA_SEPARATOR_ESCAPED,INTRA_SEPARATOR);

    }

    public static String serialize(Iterable<Factor> factors) {
        List<String> serializedFactors = new ArrayList<>();
        for (Factor factor : factors) {
            serializedFactors.add(
                    toSafe(factor.getType())
                            + INTRA_SEPARATOR
                            + toSafe(factor.getValue()));
        }
        try {
            return URLEncoder.encode(StringUtils.join(serializedFactors, INTER_SEPARATOR), "UTF-8");
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
            serializedFactors = URLDecoder.decode(csvSerializedFactors, "UTF-8")
                    .split(MessageFormat.format("(?<!{1}){0}", INTER_SEPARATOR, ESCAPE));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        for (String serializedFactor : serializedFactors) {
            String[] split = serializedFactor
                    .split(MessageFormat.format("(?<!{1}){0}", INTRA_SEPARATOR, ESCAPE));

            checkState(split.length == 2, "serialized Factor string should be like TYPE:value, is:"
                    + Arrays.deepToString(split));

            factors.add(new Factor(
                    fromSafe(split[0]),
                    fromSafe(split[1]))
            );
        }
        return factors;
    }

}
