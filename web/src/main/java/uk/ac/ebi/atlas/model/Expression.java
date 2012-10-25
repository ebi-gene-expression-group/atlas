package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class Expression {
    private ExperimentRun experimentRun;
    private double level;

    public Expression(ExperimentRun experimentRun, double level) {
        this.experimentRun = checkNotNull(experimentRun);
        this.level = level;
    }

    public String getRunAccession() {
        return experimentRun.getRunAccession();
    }

    public Set<FactorValue> getFactorValues() {
        return experimentRun.getFactorValues();
    }

    public String getOrganismPart() {
        return experimentRun.getOrganismPart();
    }

    public double getLevel() {
        return level;
    }

    public Expression addFactorValue(String factor, String value) {
        experimentRun.addFactorValue(factor, value);
        return this;
    }

    public ExperimentRun getExperimentRun() {
        return experimentRun;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(this.level, level) > 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(experimentRun, level);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expression other = (Expression) obj;

        return Objects.equals(experimentRun, other.experimentRun)
                && Objects.equals(level, level);
    }
}