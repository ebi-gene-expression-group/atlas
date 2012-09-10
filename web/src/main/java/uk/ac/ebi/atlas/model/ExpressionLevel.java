package uk.ac.ebi.atlas.model;

import java.util.Set;

public class ExpressionLevel {
    ExperimentRun experimentRun;
    double rpkm;

    public ExpressionLevel() {
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
}