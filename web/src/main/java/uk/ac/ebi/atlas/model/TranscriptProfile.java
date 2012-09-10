package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

        private String transcriptId;
        private TranscriptProfile transcriptExpressions;

        public Builder(String transcriptId) {
            transcriptExpressions = new TranscriptProfile(transcriptId);
            this.transcriptId = transcriptId;
        }

        public Builder addExpressionLevel(ExpressionLevel expressionLevel) {
            transcriptExpressions.expressionLevels.add(expressionLevel);
            return this;
        }

        public TranscriptProfile create() {
            return transcriptExpressions;
        }


    }
}
