package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class GeneProfileComparator implements Comparator<GeneProfile> {

    private boolean isSpecific;
    private Set<FactorValue> selectedFactorValues;
    private Set<FactorValue> allFactorValues;

    public GeneProfileComparator(boolean isSpecific, Set<FactorValue> selectFactorValues,
                                 Set<FactorValue> allFactorValues) {
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
                                 Set<FactorValue> averageOn) {

        Ordering<Comparable> naturalOrdering = Ordering.natural();
        return naturalOrdering.compare(firstGeneProfile.getAverageExpressionLevelOn(averageOn),
                otherGeneProfile.getAverageExpressionLevelOn(averageOn));
    }


}
