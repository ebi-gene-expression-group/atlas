package uk.ac.ebi.atlas.model;

import com.google.common.collect.Ordering;

import java.util.Comparator;

public class TranscriptSpecificityComparator implements Comparator<TranscriptExpression> {

    @Override
    public int compare(TranscriptExpression firstTranscriptExpression, TranscriptExpression otherTranscriptExpression) {
        if (firstTranscriptExpression.getSpecificity() == null) {
            return -1;
        }
        if (otherTranscriptExpression.getSpecificity() == null) {
            return +1;
        }
        int order = Ordering.natural().reverse().compare(firstTranscriptExpression.getSpecificity(), otherTranscriptExpression.getSpecificity());
        if (order != 0) {
            return order;
        }
        return firstTranscriptExpression.compareTo(otherTranscriptExpression);

    }

}
