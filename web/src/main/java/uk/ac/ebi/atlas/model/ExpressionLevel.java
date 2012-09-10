package uk.ac.ebi.atlas.model;

import java.util.Set;

public class ExpressionLevel {
    private ExperimentRun experimentRun;
    private double rpkm;

    public ExpressionLevel(ExperimentRun experimentRun, double rpkm) {
        this.experimentRun = experimentRun;
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
}