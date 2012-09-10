package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class TranscriptExpression implements Comparable<TranscriptExpression> {

    private String transcriptId;

    private ExpressionLevel expressionLevel;

    private Integer specificity;


    public TranscriptExpression(String transcriptId, double rpkm, ExperimentRun experimentRun) {
        this.transcriptId = checkNotNull(transcriptId);
        this.expressionLevel = new ExpressionLevel(checkNotNull(experimentRun), rpkm);
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public String getRunAccession() {
        return expressionLevel.getRunAccession();
    }

    public Set<FactorValue> getFactorValues() {
        return expressionLevel.getFactorValues();
    }

    public double getRpkm() {
        return expressionLevel.getRpkm();
    }

    public TranscriptExpression addFactorValue(String factor, String value) {
        expressionLevel.addFactorValue(factor, value);

        return this;
    }

    public ExpressionLevel getExpressionLevel() {
        return expressionLevel;
    }

    public void setExpressionLevel(ExpressionLevel expressionLevel) {
        this.expressionLevel = expressionLevel;
    }

    public Integer getSpecificity() {
        return specificity;
    }

    public void setSpecificity(Integer specificity) {
        this.specificity = specificity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transcriptId, expressionLevel.getExperimentRun(), expressionLevel.getRpkm());
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
                && Objects.equals(this.expressionLevel.getExperimentRun(), other.expressionLevel.getExperimentRun())
                && Objects.equals(this.expressionLevel.getRpkm(), other.expressionLevel.getRpkm());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("transcriptId", getTranscriptId())
                .add("rpkm", expressionLevel.getRpkm())
                .add("experimentRun", expressionLevel.getExperimentRun()).toString();
    }

    @Override
    public int compareTo(TranscriptExpression other) {
        int compareTo = Double.compare(expressionLevel.getRpkm(), other.expressionLevel.getRpkm());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = getTranscriptId().compareTo(other.getTranscriptId());
        if (compareTo != 0) {
            return compareTo;
        }
        return expressionLevel.getExperimentRun().compareTo(other.expressionLevel.getExperimentRun());

    }
}
