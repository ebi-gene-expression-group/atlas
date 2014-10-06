package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;

@AutoValue
public abstract class SampleCharacteristic {

    public static SampleCharacteristic create(String value) {
        return new AutoValue_SampleCharacteristic(value, Optional.<OntologyTerm>absent());
    }

    public static SampleCharacteristic create(String value, Optional<OntologyTerm> ontologyTermOptional) {
        return new AutoValue_SampleCharacteristic(value, ontologyTermOptional);
    }

    public abstract String value();
    public abstract Optional<OntologyTerm> ontologyTerm();

}
