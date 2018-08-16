package uk.ac.ebi.atlas.experimentimport.analytics.differential;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

public final class DifferentialTsvFileParsingUtil {
    protected DifferentialTsvFileParsingUtil() {
        throw new UnsupportedOperationException();
    }

    /*
     * Parses contrast headers into contrasts
     *
     * eg:
     *
     * [g1_g2.p-value, g1_2.log2foldchange, g2_g3.p-value, g2_g3.log2foldchange] => [g1_g2, g2_g3]
     *
     * [g1_g2.p-value, g1_2.t-statistic, g1_2.log2foldchange, g2_g3.p-value, g1_2.t-statistic, g2_g3.log2foldchange] =>
     * [g1_g2, g2_g3]
     */
    public static ImmutableList<String> parseHeaderIntoContrastIds(String[] contrastHeaders) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (String header : contrastHeaders) {
            if (header.endsWith(".p-value")) {
                String contrastId = StringUtils.substringBefore(header, ".");
                builder.add(contrastId);
            }
        }

        return builder.build();
    }

    public static double parseDouble(String value) {
        if (value.equalsIgnoreCase("inf")) {
            return Double.POSITIVE_INFINITY;
        }
        if (value.equalsIgnoreCase("-inf")) {
            return Double.NEGATIVE_INFINITY;
        }
        return Double.parseDouble(value);
    }
}
