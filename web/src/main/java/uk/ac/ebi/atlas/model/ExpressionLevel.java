package uk.ac.ebi.atlas.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class ExpressionLevel implements Comparable<ExpressionLevel> {

    private String identifier;

    private Set<FactorValue> factorValues = new HashSet<>();

    private int rpkm;

    public ExpressionLevel(String identifier, int rpkm, Collection<FactorValue> factorValues) {
        this.identifier = checkNotNull(identifier);
        this.rpkm = rpkm;
        if (factorValues != null){
            this.factorValues.addAll(factorValues);
        }
    }

    public ExpressionLevel(String identifier, int rpkm) {
        this(identifier, rpkm, null);
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

    public ExpressionLevel addFactorValue(String factor, String value) {
        factorValues.add(new FactorValue(factor, value));
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, factorValues, rpkm);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExpressionLevel other = (ExpressionLevel) obj;

        return Objects.equals(this.identifier, other.identifier)
                && Objects.equals(this.factorValues, other.factorValues)
                && Objects.equals(this.rpkm, other.rpkm);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("identifier", identifier)
                .add("rpkm", rpkm)
                .add("factorValues", factorValues).toString();
    }

    @Override
    public int compareTo(ExpressionLevel expressionLevel) {
        final int rpkmDiff = rpkm - expressionLevel.rpkm;
        if (rpkmDiff != 0) {
            return rpkmDiff;
        }

        return identifier.compareTo(expressionLevel.identifier);

    }
}
