package uk.ac.ebi.atlas.model;

import com.google.common.base.Objects;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class FactorValue implements Comparable<FactorValue> {

    //ToDo: this should be removed... class should be possibly independent from representation
    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private String type;

    private String name;

    private String value;

    public FactorValue(String type, String value) {
        this(type, null, value);
    }

    public FactorValue(String type, String name, String value) {
        //ToDo: this pre-processing of type metadata (space to underscore and case conversion) should be removed, we should assume configuration is well formed and valid
        this.type = checkNotNull(type).replaceAll(" ", "_").toUpperCase();
        this.name = name;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(type, value);
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() != other.getClass()) {
            return false;
        }
        return Objects.equal(this.getClass(), other.getClass())
                && Objects.equal(this.type, ((FactorValue) other).type)
                && Objects.equal(this.value, ((FactorValue) other).value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("name", name)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(FactorValue factorValue) {
        int factorCompare = type.compareTo(factorValue.type);
        if (factorCompare != 0) {
            return factorCompare;
        }
        return value.compareTo(factorValue.value);
    }

    //ToDo: this should be dropped, construction depending on representation, no good...
    public static FactorValue createFactorValue(String factorValue) {
        return createFactorValue(factorValue.split(":"));
    }

    //ToDo: this should be dropped, construction must be done with constructor
    protected static FactorValue createFactorValue(String[] split) {
        if (split.length == 2) {
            return new FactorValue(split[0].trim(), split[1].trim());
        }
        throw new IllegalArgumentException("FactorValue mus");
    }


    public static SortedSet<String> getFactorValuesStrings(SortedSet<FactorValue> factorValues) {
        SortedSet<String> result = new TreeSet<>();
        for (FactorValue factorValue : factorValues) {
            result.add(factorValue.getValue());
        }
        return result;
    }

    // representational differences are due to encoding in URL / RequestPreferences
    // you would need to change RequestPreferences to translate URL String into FactorValues
    // or use this method to "emulate" URL like encoding of factor values
    public static SortedSet<String> getFactorValuesURLRepresentation(Set<FactorValue> factorValues) {
        SortedSet<String> result = new TreeSet<>();
        for (FactorValue factorValue : factorValues) {
            result.add(factorValue.getType() + ":" + factorValue.getValue());
        }
        return result;
    }
}
