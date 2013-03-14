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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class BaselineProfileComparator implements Comparator<BaselineProfile> {

    private boolean isSpecific;
    private Set<Factor> selectedFactors;
    private Set<Factor> allFactors;
    private double cutoff;


    public BaselineProfileComparator(boolean isSpecific, Set<Factor> selectQueryFactors,
                                     Set<Factor> allQueryFactors, double cutoff) {
        this.isSpecific = isSpecific;
        this.selectedFactors = selectQueryFactors;
        this.allFactors = allQueryFactors;
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.cutoff = cutoff > 0? cutoff: 0.09;
    }

    @Override
    public int compare(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            Ordering<Comparable> specificityOrdering = naturalOrdering.reverse();
            int order = specificityOrdering.compare(firstBaselineProfile.getSpecificity(), otherBaselineProfile.getSpecificity());
            return 0 != order ? order : compareOnAverage(firstBaselineProfile, otherBaselineProfile, allFactors);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedFactors)) {

            return naturalOrdering.compare(getExpressionLevelFoldChangeOn(firstBaselineProfile),
                    getExpressionLevelFoldChangeOn(otherBaselineProfile));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            return compareOnAverage(firstBaselineProfile, otherBaselineProfile, allFactors);
        }

        //B2
        return compareOnAverage(firstBaselineProfile, otherBaselineProfile, selectedFactors);

    }

    private int compareOnAverage(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile,
                                 Set<Factor> averageOn) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();
        return naturalOrdering.compare(firstBaselineProfile.getAverageExpressionLevelOn(averageOn),
                otherBaselineProfile.getAverageExpressionLevelOn(averageOn));
    }

    public double getExpressionLevelFoldChangeOn(BaselineProfile baselineProfile) {

        double averageExpressionLevelOnSelected = baselineProfile.getAverageExpressionLevelOn(selectedFactors);

        Sets.SetView<Factor> remainingFactors = Sets.difference(allFactors, selectedFactors);
        double averageExpressionLevelOnRemaining = baselineProfile.getAverageExpressionLevelOn(remainingFactors);

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
