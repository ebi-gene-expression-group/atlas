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

package uk.ac.ebi.atlas.model.baseline.barcharts;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.h2.mvstore.DataUtils.checkArgument;

@Named("cutoffScale")
@Scope("singleton")
public class CutoffScale {

    private static final int DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS = 1;
    private static final int MAX_NUMBER_OF_VALUES = 100;

    private static final ConcurrentMap<Integer, Double> magnifiedScale = new ConcurrentHashMap<>();

    CutoffScale(){

    }

    public SortedSet<Double> getValuesSmallerThan(double expressionLevel){

        SortedSet<Double> scaledValues = new TreeSet<>();

        for (int i = 0; i < MAX_NUMBER_OF_VALUES; i++){

            double scaledValue = getNthValue(i);
            if (expressionLevel <= scaledValue){
                return scaledValues;
            }
            scaledValues.add(scaledValue);
        }

        return scaledValues;
    }

    public double getNthValue(int position) {

        checkArgument(position >= 0, "position must be >= 0 ");

        if (position == 0) {
            return 0;
        }

        Double nthValue = magnifiedScale.get(position);

        if (nthValue == null) {

            nthValue = calculateNthScaledValue(position);

            magnifiedScale.put(position, nthValue);

        }

        return nthValue;

    }

    private Double calculateNthScaledValue(int position) {
        Double nthValue;
        int remainder = position % 9;

        if (remainder != 0) {
            int power = (position / 9) - DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS;
            nthValue = Math.pow(10, power) * remainder;
        } else {
            int power = (position / 9) - (DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS + 1);
            nthValue = Math.pow(10, power) * 9;
        }

        if(nthValue > 1){
            nthValue = Math.floor(nthValue);
        }

        nthValue = (Math.floor(nthValue * 10))/ 10;
        return nthValue;
    }

}
