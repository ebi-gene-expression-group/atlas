package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;
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
