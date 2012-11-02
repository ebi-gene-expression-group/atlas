package uk.ac.ebi.atlas.utils;

import org.apache.commons.math.util.MathUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    private static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    private static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;

    public static String roundToString(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

    public static double round(double value) {
        return MathUtils.round(value, value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE);
    }
}