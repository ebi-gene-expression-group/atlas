package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class NameValue {

    public abstract String name();
    public abstract String value();

    public static NameValue create(String value) {
        return new AutoValue_NameValue(value, value);
    }

    public static NameValue create(String value, String name) {
        return new AutoValue_NameValue(value, name);
    }

}
