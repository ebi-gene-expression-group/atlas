package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExpressionLevel implements Comparable<ExpressionLevel> {

    private static final String UNKNOWN_EXPERIMENT_RUN_ACCESSION = "UNKNOWN_EXPERIMENT_RUN";
    private String transcriptId;

    private ExperimentRun experimentRun;

    private double rpkm;

    public ExpressionLevel(String transcriptId, double rpkm, ExperimentRun experimentRun) {
        this.transcriptId = checkNotNull(transcriptId);
        if (experimentRun == null) {
            experimentRun = new ExperimentRun(UNKNOWN_EXPERIMENT_RUN_ACCESSION);
        }
        this.experimentRun = experimentRun;
        this.rpkm = rpkm;
    }

    public ExpressionLevel(String transcriptId, double rpkm) {
        this(transcriptId, rpkm, null);
    }

    public String getTranscriptId() {
        return transcriptId;
    }

    public String getRunAccession(){
        return experimentRun.getRunAccession();
    }

    public Set<FactorValue> getFactorValues() {
        return experimentRun.getFactorValues();
    }

    public double getRpkm() {
        return rpkm;
    }

    public ExpressionLevel addFactorValue(String factor, String value) {
        experimentRun.addFactorValue(factor, value);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transcriptId, experimentRun, rpkm);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExpressionLevel other = (ExpressionLevel) obj;

        return Objects.equals(this.transcriptId, other.transcriptId)
                && Objects.equals(this.experimentRun, other.experimentRun)
                && Objects.equals(this.rpkm, other.rpkm);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("transcriptId", transcriptId)
                .add("rpkm", rpkm)
                .add("experimentRun", experimentRun).toString();
    }

    @Override
    public int compareTo(ExpressionLevel other) {
        int compareTo = Double.compare(rpkm, other.rpkm);
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = transcriptId.compareTo(other.transcriptId);
        if (compareTo != 0) {
            return compareTo;
        }
        return experimentRun.compareTo(other.experimentRun);

    }
}
