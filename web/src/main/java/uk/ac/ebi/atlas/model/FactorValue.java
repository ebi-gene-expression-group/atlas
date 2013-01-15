package uk.ac.ebi.atlas.model;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

public class FactorValue implements Comparable<FactorValue> {

    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private String type;

    private String name;

    private String value;

    public FactorValue(String type, String name, String value) {
        this.type = checkNotNull(type).replaceAll(" ", "_").toUpperCase();
        this.name = checkNotNull(name);
        this.value = checkNotNull(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getDisplayString() {
        return type.concat(FACTOR_VALUE_SEPARATOR).concat(name.concat(FACTOR_VALUE_SEPARATOR)
                .concat(value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
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
        return Objects.equals(this.type, other.type) && Objects.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(getDisplayString()).toString();
    }

    @Override
    public int compareTo(FactorValue factorValue) {
        int factorCompare = name.compareTo(factorValue.name);
        if (factorCompare != 0) {
            return factorCompare;
        }
        return value.compareTo(factorValue.value);
    }

    public static FactorValue createFactorValue(String factorValue) {
        String[] split = factorValue.split(":");
        if (split.length == 2) {
            return new FactorValue(split[0].trim(), "", split[1].trim());
        }
        return null;
    }
}
