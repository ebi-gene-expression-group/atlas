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
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;

import java.util.Comparator;
import java.util.Set;

public class BaselineProfileComparator implements Comparator<BaselineProfile> {

    // This value is just smaller than the smallest non-zero value we allow,
    // c.f. FRACTIONAL_DIGITS_FOR_VALUE_SMALLER_THAN_ZEROPOINTONE in
    // BaselineExpressionLevelRounder class
    private static final double CUTOFF_DIVISOR_DEFAULT_VALUE = 0.00000009;

    private boolean isSpecific;
    private Set<Factor> selectedQueryFactors;
    private Set<Factor> allQueryFactors;
    private double cutoffDivisor;


    public static BaselineProfileComparator create(BaselineProfileStreamOptions options) {
        return new BaselineProfileComparator(options.isSpecific(),
                options.getSelectedQueryFactors(),
                options.getAllQueryFactors(),
                options.getCutoff());
    }

    public BaselineProfileComparator(boolean isSpecific, Set<Factor> selectedQueryFactors,
                                     Set<Factor> allQueryFactors, double cutoff) {
        this.isSpecific = isSpecific;
        this.selectedQueryFactors = selectedQueryFactors;
        this.allQueryFactors = allQueryFactors;

        cutoffDivisor = cutoff != 0 ? cutoff : CUTOFF_DIVISOR_DEFAULT_VALUE;
    }

    @Override
    public int compare(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile) {

        //ToDo: we are using reverse comparison logic everywhere in this method.
        //ToDo: This reduces readability, every time I read it I spend hours to understand it.
        //ToDo: We should have the comparator use normal natural (ascending) logic as standard in every Java library
        //ToDo: and then require the client to revert it (because the client needs to sort a priority queue in descending order).
        //ToDo: this would make also the client implementation more explicit.

        /*
        *  maybe Guava ComparisonChain reads better:
        *  ....
        *  return ComparisonChain.start()
        *                        .compare(x,y)
        *                        .compare(x,y,Ordering.natural().reverse())
        *                        .result()
        */

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            int order = Integer.compare(firstBaselineProfile.getSpecificity(), otherBaselineProfile.getSpecificity());
            return 0 != order ? order : compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, allQueryFactors);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedQueryFactors)) {
            // reverse because we want lower values to come first
            return Ordering.natural().reverse().compare(getExpressionLevelFoldChange(firstBaselineProfile),
                    getExpressionLevelFoldChange(otherBaselineProfile));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedQueryFactors)) {
            return compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, allQueryFactors);
        }

        //B2 - !isSpecific && !CollectionUtils.isEmpty(selectedQueryFactors)
        return compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, selectedQueryFactors);

    }

    int compareOnAverageExpressionLevel(BaselineProfile firstBaselineProfile, BaselineProfile otherBaselineProfile,
                                        Set<Factor> factors) {

        return Ordering.natural().reverse().
                compare(firstBaselineProfile.getAverageExpressionLevelOn(factors),
                        otherBaselineProfile.getAverageExpressionLevelOn(factors));
    }

    public double getExpressionLevelFoldChange(BaselineProfile baselineProfile) {

        double averageExpressionLevelOnSelectedQueryFactors = baselineProfile.getAverageExpressionLevelOn(selectedQueryFactors);

        Set<Factor> nonSelectedQueryFactors = Sets.difference(allQueryFactors, selectedQueryFactors);

        double maxExpressionLevelOnNonSelectedQueryFactors = baselineProfile.getMaxExpressionLevelOn(nonSelectedQueryFactors);

        if (maxExpressionLevelOnNonSelectedQueryFactors == 0) {
            if (nonSelectedQueryFactors.isEmpty()) {
                return averageExpressionLevelOnSelectedQueryFactors;
            }
            return averageExpressionLevelOnSelectedQueryFactors / cutoffDivisor;
        }

        return averageExpressionLevelOnSelectedQueryFactors / maxExpressionLevelOnNonSelectedQueryFactors;
    }

}