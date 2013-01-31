package uk.ac.ebi.atlas.model;

import java.util.Set;

public class Expression {
    private double level;

    private Set<Factor> allFactors;

    public Expression(double level, Set<Factor> allFactors) {
        this.level = level;
        this.allFactors = allFactors;
    }

    public Set<Factor> getAllFactors() {
        return allFactors;
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(this.level, level) > 0;
    }

    public Factor getFactorValue(String type) {
        for (Factor factor : allFactors) {
            if (factor.getType().equals(type)) {
                return factor;
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
        if (!allFactors.equals(that.allFactors)) {
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
        result = 31 * result + allFactors.hashCode();
        return result;
    }
}