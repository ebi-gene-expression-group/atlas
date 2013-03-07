package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class GeneProfileComparator implements Comparator<GeneProfile> {

    private boolean isSpecific;
    private Set<Factor> selectedFactors;
    private Set<Factor> allFactors;
    private double cutoff;


    public GeneProfileComparator(boolean isSpecific, Set<Factor> selectQueryFactors,
                                 Set<Factor> allQueryFactors, double cutoff) {
        this.isSpecific = isSpecific;
        this.selectedFactors = selectQueryFactors;
        this.allFactors = allQueryFactors;
        //This is needed to bring up genes which are expressed only in selected tissues when cutoff is 0.
        this.cutoff = cutoff > 0? cutoff: 0.09;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            Ordering<Comparable> specificityOrdering = naturalOrdering.reverse();
            int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
            return 0 != order ? order : compareOnAverage(firstGeneProfile, otherGeneProfile, allFactors);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedFactors)) {

            return naturalOrdering.compare(getExpressionLevelFoldChangeOn(firstGeneProfile),
                    getExpressionLevelFoldChangeOn(otherGeneProfile));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedFactors)) {
            return compareOnAverage(firstGeneProfile, otherGeneProfile, allFactors);
        }

        //B2
        return compareOnAverage(firstGeneProfile, otherGeneProfile, selectedFactors);

    }

    private int compareOnAverage(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile,
                                 Set<Factor> averageOn) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();
        return naturalOrdering.compare(firstGeneProfile.getAverageExpressionLevelOn(averageOn),
                otherGeneProfile.getAverageExpressionLevelOn(averageOn));
    }

    public double getExpressionLevelFoldChangeOn(GeneProfile geneProfile) {

        double averageExpressionLevelOnSelected = geneProfile.getAverageExpressionLevelOn(selectedFactors);

        Sets.SetView<Factor> remainingFactors = Sets.difference(allFactors, selectedFactors);
        double averageExpressionLevelOnRemaining = geneProfile.getAverageExpressionLevelOn(remainingFactors);

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
