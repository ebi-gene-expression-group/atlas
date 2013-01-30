package uk.ac.ebi.atlas.model;

import java.util.Set;

public class Expression {
    private double level;

    private Set<FactorValue> allFactorValues;

    public Expression(double level, Set<FactorValue> allFactorValues) {
        this.level = level;
        this.allFactorValues = allFactorValues;
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

    public FactorValue getFactorValue(String type) {
        for (FactorValue factorValue : allFactorValues) {
            if (factorValue.getType().equals(type)) {
                return factorValue;
            }
        }
        throw new IllegalStateException("Expression doesn't contain factor value for a given type");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Expression that = (Expression) o;

        if (Double.compare(that.level, level) != 0) {
            return false;
        }
        if (!allFactorValues.equals(that.allFactorValues)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = level != +0.0d ? Double.doubleToLongBits(level) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + allFactorValues.hashCode();
        return result;
    }
}