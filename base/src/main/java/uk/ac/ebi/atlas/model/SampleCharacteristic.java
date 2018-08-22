package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

@AutoValue
public abstract class SampleCharacteristic {
    public abstract String header();
    public abstract String value();
    public abstract Set<OntologyTerm> valueOntologyTerms();

    public static SampleCharacteristic create(String header, String value) {
        return create(header, value, new ImmutableSet.Builder<OntologyTerm>().build());
    }

    public static SampleCharacteristic create(String header, String value, OntologyTerm...  ontologyTerms) {
        return create(header, value, new ImmutableSet.Builder<OntologyTerm>().add(ontologyTerms).build());
    }

    private static SampleCharacteristic create(String header, String value, Set<OntologyTerm> ontologyTerms) {
        return new AutoValue_SampleCharacteristic(header, value, ontologyTerms);
    }
}
