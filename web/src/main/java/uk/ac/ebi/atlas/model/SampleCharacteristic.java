package uk.ac.ebi.atlas.model;

import com.google.auto.value.AutoValue;
import com.google.common.base.Optional;

@AutoValue
public abstract class SampleCharacteristic {

    public static SampleCharacteristic create(String characteristic) {
        return new AutoValue_SampleCharacteristic(characteristic, Optional.<OntologyTerm>absent());
    }

    public static SampleCharacteristic create(String characteristic, Optional<OntologyTerm> ontologyTermOptional) {
        return new AutoValue_SampleCharacteristic(characteristic, ontologyTermOptional);
    }

    public abstract String value();
    public abstract Optional<OntologyTerm> ontologyTerm();

}
