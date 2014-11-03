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

package uk.ac.ebi.atlas.profiles.baseline;

import org.apache.commons.math.util.MathUtils;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.text.NumberFormat;

@Named("baselineExpressionLevelRounder")
@Scope("singleton")
public class BaselineExpressionLevelRounder {

    private static final int FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE = 0;
    private static final int FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE = 1;

    private final NumberFormat format1Dp = NumberFormat.getNumberInstance();
    private final NumberFormat formatNoDp = NumberFormat.getNumberInstance();

    public BaselineExpressionLevelRounder() {
        format1Dp.setGroupingUsed(false);
        format1Dp.setMaximumFractionDigits(1);
        formatNoDp.setGroupingUsed(false);
        formatNoDp.setMaximumFractionDigits(0);
    }

    public String format(double expressionLevel) {
        return expressionLevel >= 1 ? formatNoDp.format(expressionLevel) : format1Dp.format(expressionLevel);
    }

    public double round(double value) {
        int numberOfFractionalDigits = value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE;
        return MathUtils.round(value, numberOfFractionalDigits);
    }

}