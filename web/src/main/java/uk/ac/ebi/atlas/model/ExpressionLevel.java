package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Set;

public class ExpressionLevel {

    private String identifier;

    private Set<FactorValue> factorValues = new HashSet<>();

    private int rpkm;

    public ExpressionLevel(String identifier, Set<FactorValue> factorValues, int rpkm) {
        this.identifier = identifier;
        this.factorValues = factorValues;
        this.rpkm = rpkm;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<FactorValue> getFactorValues() {
        return factorValues;
    }

    public int getRpkm() {
        return rpkm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpressionLevel that = (ExpressionLevel) o;

        if (rpkm != that.rpkm) return false;
        if (!factorValues.equals(that.factorValues)) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + factorValues.hashCode();
        result = 31 * result + rpkm;
        return result;
    }

    @Override
    public String toString() {
        return "ExpressionLevel{" +
                "identifier='" + identifier + '\'' +
                ", rpkm=" + rpkm +
                ", factorValues=" + factorValues +
                '}';
    }
}
