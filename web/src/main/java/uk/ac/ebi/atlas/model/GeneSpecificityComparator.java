package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.Set;

public class GeneSpecificityComparator implements Comparator<GeneProfile> {

    private boolean rankGenesExpressedOnMostFactorsLast;
    private Set<String> selectedOrganismParts;
    private Set<String> allOrganismParts;

    public GeneSpecificityComparator( boolean rankGenesExpressedOnMostFactorsLast, Set<String> selectedOrganismParts,
                                      Set<String> allOrganismParts){
        this.rankGenesExpressedOnMostFactorsLast = rankGenesExpressedOnMostFactorsLast;
        this.selectedOrganismParts = selectedOrganismParts;
        this.allOrganismParts = allOrganismParts;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {
        Ordering<Comparable> specificityOrdering = rankGenesExpressedOnMostFactorsLast ? Ordering.natural().reverse() : Ordering.natural();
        int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
        if (order != 0) {
            return order;
        }
        if (!rankGenesExpressedOnMostFactorsLast || selectedOrganismParts.equals(allOrganismParts)){
            return Ordering.natural().compare(firstGeneProfile.getAverageExpressionLevelOn(selectedOrganismParts)
                                            ,otherGeneProfile.getAverageExpressionLevelOn(selectedOrganismParts));
        }
        //ToDo: maybe the geneProfile should know by itself what are 'all organismParts'
        return Ordering.natural().compare(firstGeneProfile.getWeightedExpressionLevelOn(selectedOrganismParts, allOrganismParts)
                        ,otherGeneProfile.getWeightedExpressionLevelOn(selectedOrganismParts, allOrganismParts));

    }


}
