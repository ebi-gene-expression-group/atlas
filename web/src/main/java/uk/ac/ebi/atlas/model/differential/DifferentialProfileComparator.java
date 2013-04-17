/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class DifferentialProfileComparator<T extends DifferentialProfile> implements Comparator<T> {

    private boolean isSpecific;
    private Set<Contrast> selectedQueryFactors;
    private Set<Contrast> allQueryFactors;
    private Regulation regulation;
    private double cutoff = 0.05;


    public DifferentialProfileComparator(boolean isSpecific, Set<Contrast> selectedQueryFactors,
                                         Set<Contrast> allQueryFactors, Regulation regulation, double cutoff) {
        this.isSpecific = isSpecific;
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.regulation = regulation;
        this.cutoff = cutoff;
    }

    @Override
    public int compare(DifferentialProfile firstProfile, DifferentialProfile otherProfile) {

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            int comparison = Integer.compare(firstProfile.getSpecificity(regulation), otherProfile.getSpecificity(regulation));
            if (0 == comparison){
                comparison = compareOnAverage(firstProfile, otherProfile, allQueryFactors);
            }
            return comparison;
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedQueryFactors)) {
            return Ordering.natural().reverse().compare(
                                                    getExpressionLevelFoldChangeOn(firstProfile),
                                                    getExpressionLevelFoldChangeOn(otherProfile));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            return compareOnAverage(firstProfile, otherProfile, allQueryFactors);
        }

        //B2
        return compareOnAverage(firstProfile, otherProfile, selectedQueryFactors);

    }

    protected int compareOnAverage(DifferentialProfile firstProfile, DifferentialProfile otherProfile,
                                 Set<Contrast> averageOn) {

        double averageExpressionLevelOn1 = firstProfile.getAverageExpressionLevelOn(averageOn, regulation);
        double averageExpressionLevelOn2 = otherProfile.getAverageExpressionLevelOn(averageOn, regulation);
        return Double.compare(averageExpressionLevelOn1, averageExpressionLevelOn2);

    }

    public double getExpressionLevelFoldChangeOn(DifferentialProfile profile) {

        double averageExpressionLevelOnSelected = profile.getAverageExpressionLevelOn(selectedQueryFactors, regulation);

        Sets.SetView<Contrast> remainingFactors = Sets.difference(allQueryFactors, selectedQueryFactors);
        double averageExpressionLevelOnRemaining = profile.getAverageExpressionLevelOn(remainingFactors, regulation);
        if (averageExpressionLevelOnRemaining == 0) {
            averageExpressionLevelOnRemaining = cutoff;
        }

        return averageExpressionLevelOnRemaining / averageExpressionLevelOnSelected;
    }

}
