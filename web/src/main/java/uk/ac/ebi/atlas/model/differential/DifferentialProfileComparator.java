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

public class DifferentialProfileComparator implements Comparator<DifferentialProfile> {

    private boolean isSpecific;
    private Set<Contrast> selectedFactors;
    private Set<Contrast> allFactors;
    private Regulation regulation;
    private double cutoff;


    public DifferentialProfileComparator(boolean isSpecific, Set<Contrast> selectQueryFactors,
                                         Set<Contrast> allQueryFactors, double cutoff, Regulation regulation) {
        this.isSpecific = isSpecific;
        this.selectedFactors = selectQueryFactors;
        this.allFactors = allQueryFactors;
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.regulation = regulation;
    }

    @Override
    public int compare(DifferentialProfile firstProfile, DifferentialProfile otherProfile) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            Ordering<Comparable> specificityOrdering = naturalOrdering;
            int order = specificityOrdering.compare(firstProfile.getSpecificity(regulation), otherProfile.getSpecificity(regulation));
            return 0 != order ? order : compareOnAverage(firstProfile, otherProfile, allFactors);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedFactors)) {

            return naturalOrdering.compare(getExpressionLevelFoldChangeOn(firstProfile),
                    getExpressionLevelFoldChangeOn(otherProfile));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            return compareOnAverage(firstProfile, otherProfile, allFactors);
        }

        //B2
        return compareOnAverage(firstProfile, otherProfile, selectedFactors);

    }

    private int compareOnAverage(DifferentialProfile firstProfile, DifferentialProfile otherProfile,
                                 Set<Contrast> averageOn) {

        Ordering<Double> naturalOrdering = Ordering.natural();
        double averageExpressionLevelOn1 = firstProfile.getAverageExpressionLevelOn(averageOn, regulation);
        double averageExpressionLevelOn2 = otherProfile.getAverageExpressionLevelOn(averageOn, regulation);
        return naturalOrdering.compare(averageExpressionLevelOn1,
                averageExpressionLevelOn2);
    }

    public double getExpressionLevelFoldChangeOn(DifferentialProfile Profile) {

        double averageExpressionLevelOnSelected = Profile.getAverageExpressionLevelOn(selectedFactors, regulation);

        Sets.SetView<Contrast> remainingFactors = Sets.difference(allFactors, selectedFactors);
        double averageExpressionLevelOnRemaining = Profile.getAverageExpressionLevelOn(remainingFactors, regulation);

        if (averageExpressionLevelOnRemaining == 0) {
            if (!remainingFactors.isEmpty()) {
                return averageExpressionLevelOnSelected / (cutoff / remainingFactors.size());
            } else {
                return averageExpressionLevelOnSelected;
            }
        }
        return averageExpressionLevelOnSelected / averageExpressionLevelOnRemaining;
    }

}
