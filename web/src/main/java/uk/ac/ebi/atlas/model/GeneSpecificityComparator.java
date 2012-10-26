package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;

public class GeneSpecificityComparator implements Comparator<GeneProfile> {

    boolean orderBySpecificity;

    public GeneSpecificityComparator(boolean orderBySpecificity){
        this.orderBySpecificity = orderBySpecificity;
    }

    @Override
    public int compare(GeneProfile firstGeneProfile, GeneProfile otherGeneProfile) {
        Ordering<Comparable> specificityOrdering = orderBySpecificity ? Ordering.natural().reverse() : Ordering.natural(); //reverse because specificity 1 is highest
        int order = specificityOrdering.compare(firstGeneProfile.getSpecificity(), otherGeneProfile.getSpecificity());
        if (order != 0) {
            return order;
        }
        return Ordering.natural().compare(firstGeneProfile.getMaxExpressionLevel(),otherGeneProfile.getMaxExpressionLevel());

    }

}
