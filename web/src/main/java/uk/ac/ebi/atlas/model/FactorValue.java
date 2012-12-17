package uk.ac.ebi.atlas.model;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class FactorValue implements Comparable<FactorValue> {

    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private String value;

    private Factor factor;

    public FactorValue(String type, String name, String value) {
        this.factor = new Factor(type, name);
        this.value = checkNotNull(value);
    }

    public Factor getFactor() {
        return factor;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayString() {
        return factor.getName().concat(FACTOR_VALUE_SEPARATOR)
                .concat(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factor, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FactorValue other = (FactorValue) obj;
        return Objects.equals(this.factor, other.factor) && Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(getDisplayString()).toString();
    }

    @Override
    public int compareTo(FactorValue factorValue) {
        int factorCompare = factor.compareTo(factorValue.factor);
        if (factorCompare != 0) {
            return factorCompare;
        }
        return value.compareTo(factorValue.value);
    }
}
