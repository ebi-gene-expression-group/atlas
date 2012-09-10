package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class TranscriptExpressionLevel implements Comparable<TranscriptExpressionLevel> {

    private static final String UNKNOWN_EXPERIMENT_RUN_ACCESSION = "UNKNOWN_EXPERIMENT_RUN";
    private Transcript transcript;

    private final ExpressionLevel expressionLevel = new ExpressionLevel();

    public TranscriptExpressionLevel(String transcriptId, double rpkm, ExperimentRun experimentRun) {
        this.transcript = new Transcript(checkNotNull(transcriptId));
        if (experimentRun == null) {
            experimentRun = new ExperimentRun(UNKNOWN_EXPERIMENT_RUN_ACCESSION);
        }
        this.expressionLevel.experimentRun = experimentRun;
        if (Double.isNaN(rpkm)) {
            this.expressionLevel.rpkm = Double.NEGATIVE_INFINITY;
        } else {
            this.expressionLevel.rpkm = rpkm;
        }
    }

    public TranscriptExpressionLevel(Transcript transcript, double rpkm, ExperimentRun experimentRun) {
        this.transcript = checkNotNull(transcript);
        if (experimentRun == null) {
            experimentRun = new ExperimentRun(UNKNOWN_EXPERIMENT_RUN_ACCESSION);
        }
        this.expressionLevel.experimentRun = experimentRun;
        if (Double.isNaN(rpkm)) {
            this.expressionLevel.rpkm = Double.NEGATIVE_INFINITY;
        } else {
            this.expressionLevel.rpkm = rpkm;
        }
    }

    public TranscriptExpressionLevel(String transcriptId, double rpkm) {
        this(transcriptId, rpkm, null);
    }

    public String getTranscriptId() {
        return transcript.getId();
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

    public TranscriptExpressionLevel addFactorValue(String factor, String value) {
        expressionLevel.addFactorValue(factor, value);

        return this;
    }

    public Integer getTranscriptSpecificity() {
        return transcript.getSpecificityIndex();
    }

    public TranscriptExpressionLevel setSpecificity(int specificity) {
        transcript.setSpecificityIndex(specificity);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transcript.getId(), expressionLevel.experimentRun, expressionLevel.getRpkm());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TranscriptExpressionLevel other = (TranscriptExpressionLevel) obj;

        return Objects.equals(this.getTranscriptId(), other.getTranscriptId())
                && Objects.equals(this.expressionLevel.experimentRun, other.expressionLevel.experimentRun)
                && Objects.equals(this.expressionLevel.getRpkm(), other.expressionLevel.getRpkm());
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("transcriptId", getTranscriptId())
                .add("rpkm", expressionLevel.getRpkm())
                .add("experimentRun", expressionLevel.experimentRun).toString();
    }

    @Override
    public int compareTo(TranscriptExpressionLevel other) {
        int compareTo = Double.compare(expressionLevel.getRpkm(), other.expressionLevel.getRpkm());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = getTranscriptId().compareTo(other.getTranscriptId());
        if (compareTo != 0) {
            return compareTo;
        }
        return expressionLevel.experimentRun.compareTo(other.expressionLevel.experimentRun);

    }
}
