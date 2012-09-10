package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TranscriptExpressionLevels implements Iterable<ExpressionLevel> {

    private String transcriptId;

    private Set<ExpressionLevel> expressionLevels = new HashSet<>();

    public TranscriptExpressionLevels(String transcriptId) {
        this.transcriptId = transcriptId;
    }

    public TranscriptExpressionLevels addExpressionLevel(ExpressionLevel expressionLevel) {
        expressionLevels.add(expressionLevel);

        return this;
    }

    public Set<ExpressionLevel> getExpressionLevels() {
        return expressionLevels;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    @Override
    public Iterator<ExpressionLevel> iterator() {
        return expressionLevels.iterator();
    }
}
