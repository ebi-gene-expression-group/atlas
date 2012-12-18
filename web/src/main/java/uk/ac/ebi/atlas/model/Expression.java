package uk.ac.ebi.atlas.model;

import java.util.Objects;
import java.util.Set;

public class Expression {
    private double level;

    private FactorValue factorValue;

    private Set<FactorValue> allFactorValues;

    public Expression(FactorValue factorValue, double level, Set<FactorValue> allFactorValues) {
        this.factorValue = factorValue;
        this.level = level;
        this.allFactorValues = allFactorValues;
    }

    public String getFactorValue() {
        return factorValue.getValue();
    }

    public Set<FactorValue> getAllFactorValues() {
        return allFactorValues;
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(this.level, level) > 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(factorValue, level);
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

        return Objects.equals(factorValue, other.factorValue)
                && Objects.equals(level, level);
    }
}