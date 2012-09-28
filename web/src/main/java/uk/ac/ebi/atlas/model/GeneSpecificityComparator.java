package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;

public class GeneSpecificityComparator implements Comparator<GeneExpression> {

    @Override
    public int compare(GeneExpression firstGeneExpression, GeneExpression otherGeneExpression) {
        if (firstGeneExpression.getSpecificity() == null) {
            return -1;
        }
        if (otherGeneExpression.getSpecificity() == null) {
            return +1;
        }
        int order = Ordering.natural().reverse().compare(firstGeneExpression.getSpecificity(), otherGeneExpression.getSpecificity());
        if (order != 0) {
            return order;
        }
        return firstGeneExpression.compareTo(otherGeneExpression);

    }

}
