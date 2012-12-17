package uk.ac.ebi.atlas.model;

import java.util.Objects;

public class Expression {
    private double level;

    private FactorValue factorValue;

    public Expression(FactorValue factorValue, double level) {
        this.factorValue = factorValue;
        this.level = level;
    }

    public String getFactorValue() {
        return factorValue.getValue();
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