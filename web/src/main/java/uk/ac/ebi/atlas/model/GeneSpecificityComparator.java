package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class GeneSpecificityComparator implements Comparator<GeneProfile> {

    private boolean isSpecific;
    private Set<String> selectedFactorValues;
    private Set<String> allFactorValues;

    public GeneSpecificityComparator(boolean isSpecific, Set<String> selectFactorValues,
                                     Set<String> allFactorValues) {
        this.isSpecific = isSpecific;
        this.selectedFactorValues = selectFactorValues;
        this.allFactorValues = allFactorValues;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {

        // A1: Expressions in one tissue only first, then expressions in two tissues only, etc
        // (with higher average (across all the expressed tissues) fpkms at the top in each group
        if (isSpecific && CollectionUtils.isEmpty(selectedFactorValues)) {
            Ordering<Comparable> specificityOrdering = Ordering.natural().reverse();
            int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
            if (order != 0) {
                return order;
            } else {
                return Ordering.natural().compare(firstGeneProfile.getAverageExpressionLevelOn(allFactorValues)
                        , otherGeneProfile.getAverageExpressionLevelOn(allFactorValues));
            }
        }

        // B1: genes with higher 'average across all the selected tissues minus average
        // across all the non-selected tissues' come first
        else if (isSpecific && !selectedFactorValues.isEmpty()) {
            Set<String> nonSelectedFactorValues = new HashSet<>(allFactorValues);
            nonSelectedFactorValues.removeAll(selectedFactorValues);

            double averageAcrossSelectedFirstProfile = firstGeneProfile.getAverageExpressionLevelOn(selectedFactorValues);
            double averageAcrossNonSelectedFirstProfile = firstGeneProfile.getAverageExpressionLevelOn(nonSelectedFactorValues);
            double minusAverageFirstProfile = averageAcrossSelectedFirstProfile - averageAcrossNonSelectedFirstProfile;

            double averageAcrossSelectedOtherProfile = otherGeneProfile.getAverageExpressionLevelOn(selectedFactorValues);
            double averageAcrossNonSelectedOtherProfile = otherGeneProfile.getAverageExpressionLevelOn(nonSelectedFactorValues);
            double minusAverageOtherProfile = averageAcrossSelectedOtherProfile - averageAcrossNonSelectedOtherProfile;

            Ordering<Comparable> ordering = Ordering.natural();
            return ordering.compare(minusAverageFirstProfile, minusAverageOtherProfile);
        }

        // A2
        else if (!isSpecific && selectedFactorValues.isEmpty()) {

        }

        // B2
        else if (!isSpecific && !selectedFactorValues.isEmpty()) {

        }

        Ordering<Comparable> specificityOrdering = isSpecific ? Ordering.natural().reverse() : Ordering.natural();
        int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
        if (order != 0) {
            return order;
        }
        if (!isSpecific || selectedFactorValues.equals(allFactorValues)) {
            return Ordering.natural().compare(firstGeneProfile.getAverageExpressionLevelOn(selectedFactorValues)
                    , otherGeneProfile.getAverageExpressionLevelOn(selectedFactorValues));
        }
        //ToDo: maybe the geneProfile should know by itself what are 'all organismParts'
        return Ordering.natural().compare(firstGeneProfile.getWeightedExpressionLevelOn(selectedFactorValues, allFactorValues)
                , otherGeneProfile.getWeightedExpressionLevelOn(selectedFactorValues, allFactorValues));

    }


}
