/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.utils;

import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Named("numberUtils")
@Scope("singleton")
public class NumberUtils {

    private static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    private static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;

    // P-values less than 10E-10 are shown as '< 10^-10'
    private static final Double MIN_REPORTED_VALUE = 1E-10d;
    private static final String TEN = "10";
    private static final String MULTIPLY_HTML_CODE = " × ";
    private static final String E_PATTERN = "#.##E0";
    private static final String E = "E";
    private static final String SUP_PRE = "<span style=\"vertical-align: super;\">";
    private static final String SUP_POST = "</span>";


    public String removeTrailingZero(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

    public double round(double value) {
        return MathUtils.round(value, value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE);
    }

    public String htmlFormatDouble(double number) {
        return (number >0 && number < MIN_REPORTED_VALUE) ? "<" + formatNumber(MIN_REPORTED_VALUE) : formatNumber(number);

    }

    String formatNumber(double number) {

        DecimalFormat df = new DecimalFormat(E_PATTERN);
        // Examples values of auxFormat: 6.2E-3, 0E0
        String auxFormat = df.format(number);

        // We now convert this format to 6.2*10^-3 (and 0 in the case of 0E0 specifically)
        String[] formatParts = auxFormat.split(E);
        String mantissa = formatParts[0]; // in 6.2E-3, mantissa = 6.2
        int exponent = Integer.parseInt(formatParts[1]); // in 6.2E-3, exponent= -3
        if (exponent >= -3 && exponent <= 0) {
            return new DecimalFormat("#.###").format(number);
        }

        StringBuilder stringBuilder = new StringBuilder();

        if(! "1".equals(mantissa)){
            stringBuilder.append(mantissa).append(MULTIPLY_HTML_CODE);
        }
        stringBuilder.append(TEN)
                .append(SUP_PRE)
                .append(exponent)
                .append(SUP_POST);
        return stringBuilder.toString();
    }


}