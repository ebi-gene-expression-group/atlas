package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class GeneProfileComparator implements Comparator<GeneProfile> {

    private boolean isSpecific;
    private Set<String> selectedFactorValues;
    private Set<String> allFactorValues;

    public GeneProfileComparator(boolean isSpecific, Set<String> selectFactorValues,
                                 Set<String> allFactorValues) {
        this.isSpecific = isSpecific;
        this.selectedFactorValues = selectFactorValues;
        this.allFactorValues = allFactorValues;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();

        // A1:
        if (isSpecific && CollectionUtils.isEmpty(selectedFactorValues)) {
            Ordering<Comparable> specificityOrdering = naturalOrdering.reverse();
            int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
            return 0 != order ? order : compareOnAverage(firstGeneProfile, otherGeneProfile, allFactorValues);
        }

        // B1:
        if (isSpecific && !CollectionUtils.isEmpty(selectedFactorValues)) {

            return naturalOrdering.compare(firstGeneProfile.getWeightedExpressionLevelOn(selectedFactorValues, allFactorValues),
                    otherGeneProfile.getWeightedExpressionLevelOn(selectedFactorValues, allFactorValues));
        }

        // A2
        if (!isSpecific && CollectionUtils.isEmpty(selectedFactorValues)) {
            return compareOnAverage(firstGeneProfile, otherGeneProfile, allFactorValues);
        }

        //B2
        return compareOnAverage(firstGeneProfile, otherGeneProfile, selectedFactorValues);

    }

    private int compareOnAverage(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile,
                                 Set<String> averageOn) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();
        return naturalOrdering.compare(firstGeneProfile.getAverageExpressionLevelOn(averageOn),
                otherGeneProfile.getAverageExpressionLevelOn(averageOn));
    }


}
