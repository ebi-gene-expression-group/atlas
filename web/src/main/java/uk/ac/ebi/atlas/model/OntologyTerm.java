package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class OntologyTerm {

    public static OntologyTerm create(String id) {
        return new AutoValue_OntologyTerm(id, null);
    }

    public static OntologyTerm create(String id, String source) {
        return new AutoValue_OntologyTerm(id, source);
    }

    public abstract String id();

    @Nullable
    public abstract String source();

}
