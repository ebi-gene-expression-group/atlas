package uk.ac.ebi.atlas.model;

import com.google.common.base.Objects;

import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkNotNull;

public class FactorValue implements Comparable<FactorValue> {

    private String type;

    private String name;

    private String value;

    public FactorValue(String type, String value) {
        this(type, null, value);
    }

    public FactorValue(String type, String name, String value) {

        this.type = normalize(checkNotNull(type));
        this.name = name;
        this.value = checkNotNull(value).toLowerCase();
    }

    protected String normalize(String type) {
        return type.replaceAll(" ", "_").toUpperCase();
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

    public FactorValue setName(String name){
        this.name = name;
        return this;
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

    public static SortedSet<String> getValues(SortedSet<FactorValue> factorValues) {
        SortedSet<String> result = new TreeSet<>();
        for (FactorValue factorValue : factorValues) {
            result.add(factorValue.getValue());
        }
        return result;
    }

}
