package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;

public class ExpressionLevelTranscriptSpecificityComparator implements Comparator<TranscriptExpressionLevel> {

    @Override
    public int compare(TranscriptExpressionLevel firstTranscriptExpressionLevel, TranscriptExpressionLevel otherTranscriptExpressionLevel) {
        if (firstTranscriptExpressionLevel.getSpecificity() == null) {
            return -1;
        }
        if (otherTranscriptExpressionLevel.getSpecificity() == null) {
            return +1;
        }
        int order = Ordering.natural().reverse().compare(firstTranscriptExpressionLevel.getSpecificity(), otherTranscriptExpressionLevel.getSpecificity());
        if (order != 0) {
            return order;
        }
        return firstTranscriptExpressionLevel.compareTo(otherTranscriptExpressionLevel);

    }

}
