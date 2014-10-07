package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;

import javax.annotation.Nullable;

@AutoValue
public abstract class SampleCharacteristic {

    public static SampleCharacteristic create(String value) {
        return create(value, Optional.<OntologyTerm>absent());
    }

    public static SampleCharacteristic create(String value, OntologyTerm ontologyTerm) {
        Optional<OntologyTerm> ontologyTermOptional = (ontologyTerm == null) ? Optional.<OntologyTerm>absent() : Optional.of(ontologyTerm);
        return create(value, ontologyTermOptional);
    }

    public static SampleCharacteristic create(String value, Optional<OntologyTerm> ontologyTermOptional) {
        return new AutoValue_SampleCharacteristic(value, ontologyTermOptional);
    }

    public abstract String value();
    public abstract Optional<OntologyTerm> ontologyTerm();

    public @Nullable String getOntologyTermId() {
        return ontologyTerm().isPresent() ? ontologyTerm().get().id() : null;
    }

    public @Nullable String getOntologyTermSourceAndId() {
        return ontologyTerm().isPresent() ? ontologyTerm().get().sourceAndId() : null;
    }

}
