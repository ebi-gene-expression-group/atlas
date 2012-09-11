package uk.ac.ebi.atlas.model;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExpressionLevel {
    private ExperimentRun experimentRun;
    private double rpkm;

    public ExpressionLevel(ExperimentRun experimentRun, double rpkm) {
        this.experimentRun = checkNotNull(experimentRun);
        this.rpkm = rpkm;
    }

    public String getRunAccession() {
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

    public ExperimentRun getExperimentRun() {
        return experimentRun;
    }

    public boolean isGreaterThan(double rpkm) {
        return Double.compare(this.rpkm, rpkm) > 0;
    }
}