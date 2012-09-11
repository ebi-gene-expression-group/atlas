package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class TranscriptProfile implements Iterable<ExpressionLevel> {

    private String transcriptId;

    private Set<ExpressionLevel> expressionLevels = new HashSet<>();

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
        return expressionLevels.size();
    }

    @Override
    public Iterator<ExpressionLevel> iterator() {
        return expressionLevels.iterator();
    }

    public static class Builder {

        private TranscriptProfile transcriptProfile;

        private static double DEFAULT_RPKM_CUT_OFF_VALUE = 0D;

        private double rpkmCutOffValue = DEFAULT_RPKM_CUT_OFF_VALUE;

        public Builder(String transcriptId) {
            transcriptProfile = new TranscriptProfile(transcriptId);
        }

        public Builder addExpressionLevel(ExpressionLevel expressionLevel) {

            if (expressionLevel.isGreaterThan(rpkmCutOffValue)) {
                transcriptProfile.expressionLevels.add(expressionLevel);
            }

            return this;
        }

        public Builder withRpkmCutOff(double cutOff) {
            checkState(transcriptProfile.expressionLevels.size() == 0, "Set RPKM cutOff should be invoked before adding any Expression Levels!");
            this.rpkmCutOffValue = cutOff;

            return this;
        }

        public TranscriptProfile create() {
            return transcriptProfile;
        }

        public boolean containsExpressionLevels() {
            return !transcriptProfile.expressionLevels.isEmpty();
        }

    }
}
