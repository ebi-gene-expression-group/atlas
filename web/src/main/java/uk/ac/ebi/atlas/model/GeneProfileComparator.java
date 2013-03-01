package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Set;

public class GeneProfileComparator implements Comparator<GeneProfile> {

    private boolean isSpecific;
    private Set<Factor> selectedFactors;
    private Set<Factor> allFactors;

    public GeneProfileComparator(boolean isSpecific, Set<Factor> selectQueryFactors,
                                 Set<Factor> allQueryFactors) {
        this.isSpecific = isSpecific;
        this.selectedFactors = selectQueryFactors;
        this.allFactors = allQueryFactors;
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

            return naturalOrdering.compare(firstGeneProfile.getExpressionLevelFoldChangeOn(selectedFactors, allFactors),
                    otherGeneProfile.getExpressionLevelFoldChangeOn(selectedFactors, allFactors));
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


}
