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

package uk.ac.ebi.atlas.utils.score;

import javax.inject.Named;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

@Named("cutoffScale")
public class CutoffScale {

    private static final int DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS = 1;

    protected CutoffScale(){

    }

    protected double getNthValue(int cutoffIndex) {
        try {
            return Double.parseDouble(this.getNthStringValue(cutoffIndex));
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Illegal state for cutoffIndex = " + cutoffIndex, e);
        }
    }


    protected String getNthStringValue(int cutoffIndex) {

        if (cutoffIndex > 0) {

            int remainder = cutoffIndex % 9;

            double retVal;

            if (remainder != 0) {
                int power = (cutoffIndex / 9) - DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS;
                retVal = Math.pow(10, power) * remainder;
            } else {
                int power = (cutoffIndex / 9) - (DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS + 1);
                retVal = Math.pow(10, power) * 9;
            }

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS - (retVal< 1 ? 0 : 1));
            decimalFormat.setGroupingUsed(false);

            return decimalFormat.format(retVal);
        }

        return "0";
    }

}
