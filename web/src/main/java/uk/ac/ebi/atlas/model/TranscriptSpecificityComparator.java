package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;

public class TranscriptSpecificityComparator implements Comparator<ExpressionLevel> {

    @Override
    public int compare(ExpressionLevel firstExpressionLevel, ExpressionLevel otherExpressionLevel) {
        if(firstExpressionLevel.getTranscriptSpecificity() == null) {
            return -1;
        }
        if(otherExpressionLevel.getTranscriptSpecificity() == null) {
            return +1;
        }
        int order = Ordering.natural().reverse().compare(firstExpressionLevel.getTranscriptSpecificity(), otherExpressionLevel.getTranscriptSpecificity());
        if (order != 0) {
            return order;
        }
        return firstExpressionLevel.compareTo(otherExpressionLevel);

    }

}
