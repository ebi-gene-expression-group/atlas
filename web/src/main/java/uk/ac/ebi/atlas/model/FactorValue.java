package uk.ac.ebi.atlas.model;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class FactorValue implements Comparable<FactorValue> {

    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private String factor;

    private String value;

    public FactorValue(String factor, String value) {
        this.factor = checkNotNull(factor);
        this.value = checkNotNull(value);
    }

    public String getFactor() {
        return factor;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayString() {
        return factor.concat(FACTOR_VALUE_SEPARATOR)
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
    public String toString(){
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
