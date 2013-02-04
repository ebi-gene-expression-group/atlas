package uk.ac.ebi.atlas.model;

import java.util.Set;

public class Expression {
    private double level;

    private FactorGroup factorGroup;

    public Expression(double level, FactorGroup factorGroup) {
        this.level = level;
        this.factorGroup = factorGroup;
    }

    public FactorGroup getFactorGroup() {
        return factorGroup;
    }

    public double getLevel() {
        return level;
    }

    public boolean isGreaterThan(double level) {
        return Double.compare(this.level, level) > 0;
    }

    public Factor getFactor(String type) {
        for (Factor factor : factorGroup) {
            if (factor.getType().equals(type)) {
                return factor;
            }
        }
        throw new IllegalStateException("Expression doesn't contain factor for a given type");
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
        if (!factorGroup.equals(that.factorGroup)) {
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
        result = 31 * result + factorGroup.hashCode();
        return result;
    }

    public boolean containsAll(Set<Factor> factors) {
        return factorGroup.containsAll(factors);
    }
}