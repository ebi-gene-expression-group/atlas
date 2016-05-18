
package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

public interface ExpressionsRowDeserializerBuilder<V, T extends Expression> {

    ExpressionsRowDeserializerBuilder<V, T> forExperiment(String experimentAccession);

    ExpressionsRowDeserializerBuilder<V, T> withHeaders(String... tsvFileHeaders);

    ExpressionsRowDeserializer<V, T> build();

}
