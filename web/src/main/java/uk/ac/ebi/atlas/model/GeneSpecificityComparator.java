package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.Set;

public class GeneSpecificityComparator implements Comparator<GeneProfile> {

    private boolean orderBySpecificity;
    private Set<String> selectedOrganismParts;
    private Set<String> allOrganismParts;

    public GeneSpecificityComparator(boolean orderBySpecificity, Set<String> selectedOrganismParts, Set<String> allOrganismParts){
        this.orderBySpecificity = orderBySpecificity;
        this.selectedOrganismParts = selectedOrganismParts;
        this.allOrganismParts = allOrganismParts;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {
        Ordering<Comparable> specificityOrdering = orderBySpecificity ? Ordering.natural().reverse() : Ordering.natural(); //reverse because specificity 1 is highest
        int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
        if (order != 0) {
            return order;
        }
        return Ordering.natural().compare(firstGeneProfile.getAverageExpressionLevelOn(selectedOrganismParts)
                                            ,otherGeneProfile.getAverageExpressionLevelOn(selectedOrganismParts));

    }

}
