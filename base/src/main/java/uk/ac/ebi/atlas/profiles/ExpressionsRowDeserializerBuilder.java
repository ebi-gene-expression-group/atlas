package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

public interface ExpressionsRowDeserializerBuilder<V, T extends Expression> {

    ExpressionsRowDeserializer<V, T> build(String... tsvFileHeaders);

}
