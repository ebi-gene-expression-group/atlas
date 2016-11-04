package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.text.DecimalFormat;

@Named
@Scope("singleton")
public class PValueFormatter {

    // values less than 10E-10 are shown as '< 10^-10'
    private static final Double MIN_REPORTED_VALUE = 1E-10d;

    private static final DecimalFormat FORMAT_2DP_WITH_EXPONENT = new DecimalFormat("#.##E0");
    private static final String E = "E";
    private static final int EXPONENT_MINIMUM = -3;

    public String formatPValueAsScientificNotation(double number) {
        return (number > 0 && number < MIN_REPORTED_VALUE) ? "<" + format2DpWithExponent(MIN_REPORTED_VALUE)
                : format2DpWithExponent(number);
    }

    String format2DpWithExponent(double number) {
        // Examples values of auxFormat: 6.2E-3, 0E0
        String auxFormat = FORMAT_2DP_WITH_EXPONENT.format(number);

        // We now convert this format to 6.2*10^-3 (and 0 in the case of 0E0 specifically)
        String[] formatParts = auxFormat.split(E);
        int exponent = Integer.parseInt(formatParts[1]);
        if (exponent >= EXPONENT_MINIMUM && exponent <= 0) {
            return new DecimalFormat("#.###").format(number);
        }

        return auxFormat;
    }

}
