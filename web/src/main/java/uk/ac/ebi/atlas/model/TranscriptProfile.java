package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class TranscriptProfile implements Iterable<Expression> {

    private String transcriptId;

    private Set<Expression> expressions = new HashSet<>();

    private TranscriptProfile(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public static Builder forTranscriptId(String transcriptId) {
        return new Builder(transcriptId);
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public int getTranscriptSpecificity() {
        return expressions.size();
    }

    @Override
    public Iterator<Expression> iterator() {
        return expressions.iterator();
    }

    public static class Builder {

        private TranscriptProfile transcriptProfile;

        private static double DEFAULT_CUTOFF_VALUE = 0D;

        private double cutoffValue = DEFAULT_CUTOFF_VALUE;

        public Builder(String transcriptId) {
            transcriptProfile = new TranscriptProfile(transcriptId);
        }

        public Builder addExpression(Expression expression) {

            if (expression.isGreaterThan(cutoffValue)) {
                transcriptProfile.expressions.add(expression);
            }

            return this;
        }

        public Builder withCutoff(double cutoff) {
            checkState(transcriptProfile.expressions.size() == 0, "withCutoff should be invoked before adding any Expression!");
            this.cutoffValue = cutoff;

            return this;
        }

        public TranscriptProfile create() {
            return transcriptProfile;
        }

        public boolean containsExpressions() {
            return !transcriptProfile.expressions.isEmpty();
        }

    }
}
