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

package uk.ac.ebi.atlas.model.barcharts;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named("cutoffScale")
@Scope("singleton")
public class CutoffScale {

    private static final int DEFAULT_NUMBER_OF_FRACTIONAL_DIGITS = 1;
    private static final int MAX_NUMBER_OF_VALUES = 100;

    private static final ConcurrentMap<Integer, Double> magnifiedScale = new ConcurrentHashMap<>();

    protected CutoffScale(){

    }

    public double floorToScale(double expressionLevel){
        if (expressionLevel >= 1) {
            // Remove decimal places and replace all but first digit with zeros.
            expressionLevel = Math.floor(expressionLevel);

            double tenths = Math.pow(10, (int)Math.log10(expressionLevel));
            return ((int)(expressionLevel / tenths)) * tenths;
        } else {
            return (Math.floor(expressionLevel * 10))/ 10;
        }

    }

    public SortedSet<Double> getValuesSmallerThan(double expressionLevel){

        SortedSet<Double> scaledValues = new TreeSet<>();
        int i = 0;
        do{

            double scaledValue = getNthValue(i++);
            if (expressionLevel > scaledValue){
                scaledValues.add(scaledValue);
                continue;
            }
            break;

        } while(i< MAX_NUMBER_OF_VALUES);

        return scaledValues;
    }

    public double getNthValue(int position) {

        if (position > 0) {

            Double nthValue = magnifiedScale.get(position);

            if (nthValue == null) {


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

                magnifiedScale.put(position, nthValue);

            }

            return nthValue;

        }

        return 0;
    }

}
