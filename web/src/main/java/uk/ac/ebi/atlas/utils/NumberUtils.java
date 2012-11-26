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

    public String removeTrailingZero(double value) {
        NumberFormat format = new DecimalFormat("0.####");
        return format.format(value);
    }

    public double round(double value) {
        return MathUtils.round(value, value >= 1 ? FRACTIONAL_DIGITS_FOR_VALUE_LARGER_OR_EQUAL_TO_ONE
                : FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ONE);
    }
}