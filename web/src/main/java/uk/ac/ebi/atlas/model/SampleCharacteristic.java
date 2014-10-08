package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;

import javax.annotation.Nullable;

@AutoValue
public abstract class SampleCharacteristic {

    public static SampleCharacteristic create(String header, String value) {
        return create(header, value, Optional.<OntologyTerm>absent());
    }

    public static SampleCharacteristic create(String header, String value, OntologyTerm ontologyTerm) {
        Optional<OntologyTerm> ontologyTermOptional = (ontologyTerm == null) ? Optional.<OntologyTerm>absent() : Optional.of(ontologyTerm);
        return create(header, value, ontologyTermOptional);
    }

    public static SampleCharacteristic create(String header, String value, Optional<OntologyTerm> ontologyTermOptional) {
        return new AutoValue_SampleCharacteristic(header, value, ontologyTermOptional);
    }

    public abstract String header();
    public abstract String value();
    public abstract Optional<OntologyTerm> ontologyTerm();

    public @Nullable String getOntologyTermId() {
        return ontologyTerm().isPresent() ? ontologyTerm().get().id() : null;
    }

    public @Nullable String getOntologyTermSourceAndId() {
        return ontologyTerm().isPresent() ? ontologyTerm().get().sourceAndId() : null;
    }

}
