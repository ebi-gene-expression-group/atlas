package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class TranscriptExpression implements Comparable<TranscriptExpression> {

    private String transcriptId;

    private Expression expression;

    private Integer specificity;


    public TranscriptExpression(String transcriptId, double expressionLevel, ExperimentRun experimentRun) {
        this.transcriptId = checkNotNull(transcriptId);
        this.expression = new Expression(checkNotNull(experimentRun), expressionLevel);
    }

    public TranscriptExpression(String transcriptId, Expression expression, Integer specificity) {
        this.transcriptId = transcriptId;
        this.expression = expression;
        this.specificity = specificity;
    }

    public TranscriptExpression(String transcriptId, Expression expression) {
        this.transcriptId = transcriptId;
        this.expression = expression;
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public String getRunAccession() {
        return expression.getRunAccession();
    }

    public Set<FactorValue> getFactorValues() {
        return expression.getFactorValues();
    }

    public double getLevel() {
        return expression.getLevel();
    }

    public String getOrganismPart() {
        return expression.getOrganismPart();
    }

    public TranscriptExpression addFactorValue(String factor, String value) {
        expression.addFactorValue(factor, value);

        return this;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Integer getSpecificity() {
        return specificity;
    }

    public void setSpecificity(Integer specificity) {
        this.specificity = specificity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transcriptId, expression.getExperimentRun(), expression.getLevel());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TranscriptExpression other = (TranscriptExpression) obj;

        return Objects.equals(this.getTranscriptId(), other.getTranscriptId())
                && Objects.equals(this.expression.getExperimentRun(), other.expression.getExperimentRun())
                && Objects.equals(this.expression.getLevel(), other.expression.getLevel());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("transcriptId", getTranscriptId())
                .add("level", expression.getLevel())
                .add("experimentRun", expression.getExperimentRun()).toString();
    }

    @Override
    public int compareTo(TranscriptExpression other) {
        int compareTo = Double.compare(expression.getLevel(), other.expression.getLevel());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = getTranscriptId().compareTo(other.getTranscriptId());
        if (compareTo != 0) {
            return compareTo;
        }
        return expression.getExperimentRun().compareTo(other.expression.getExperimentRun());

    }
}
