package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TranscriptExpressionLevels implements Iterable<ExpressionLevel> {

    private String transcriptId;

    private Set<ExpressionLevel> expressionLevels = new HashSet<>();

    private TranscriptExpressionLevels(String transcriptId) {
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

        private String transcriptId;
        private TranscriptExpressionLevels transcriptExpressionLevels;

        public Builder(String transcriptId) {
            transcriptExpressionLevels = new TranscriptExpressionLevels(transcriptId);
            this.transcriptId = transcriptId;
        }

        public Builder addExpressionLevel(ExpressionLevel expressionLevel) {
            transcriptExpressionLevels.expressionLevels.add(expressionLevel);
            return this;
        }

        public TranscriptExpressionLevels create() {
            return transcriptExpressionLevels;
        }


    }
}
