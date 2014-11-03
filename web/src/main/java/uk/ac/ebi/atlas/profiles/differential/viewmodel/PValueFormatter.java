package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.springframework.context.annotation.Scope;
import org.springframework.web.util.HtmlUtils;

import javax.inject.Named;
import java.text.DecimalFormat;

@Named
@Scope("singleton")
public class PValueFormatter {

    // values less than 10E-10 are shown as '< 10^-10'
    private static final Double MIN_REPORTED_VALUE = 1E-10d;

    private static final String TEN = "10";
    private static final String MULTIPLY_HTML_CODE = " \u00D7 ";
    private static final DecimalFormat FORMAT_2DP_WITH_EXPONENT = new DecimalFormat("#.##E0");
    private static final String E = "E";
    private static final String SUP_PRE = "<span style=\"vertical-align: super;\">";
    private static final String SUP_POST = "</span>";
    private static final int EXPONENT_MINIMUM = -3;

    public String formatPValueAsScientificNotation(double number) {
        return (number > 0 && number < MIN_REPORTED_VALUE) ? "<" + format2DpWithExponent(MIN_REPORTED_VALUE)
                : format2DpWithExponent(number);
    }

    public String formatPValueAsScientificNotationHtml(double number) {
        return (number > 0 && number < MIN_REPORTED_VALUE) ? "<" + format2DpWithExponentAsHtml(MIN_REPORTED_VALUE)
                : format2DpWithExponentAsHtml(number);
    }

    public String formatPValueAsScientificNotationHtmlEscaped(double number) {
        return HtmlUtils.htmlEscape(formatPValueAsScientificNotationHtml(number));
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

    String format2DpWithExponentAsHtml(double number) {
        String formattedNumber = format2DpWithExponent(number);

        String[] formatParts = formattedNumber.split(E);

        if (formatParts.length == 1) {
            return formattedNumber;
        }

        String mantissa = formatParts[0];
        String exponent = formatParts[1];

        StringBuilder stringBuilder = new StringBuilder();

        if (!"1".equals(mantissa)) {
            stringBuilder.append(mantissa).append(MULTIPLY_HTML_CODE);
        }
        stringBuilder.append(TEN)
                .append(SUP_PRE)
                .append(exponent)
                .append(SUP_POST);
        return stringBuilder.toString();
    }

}
