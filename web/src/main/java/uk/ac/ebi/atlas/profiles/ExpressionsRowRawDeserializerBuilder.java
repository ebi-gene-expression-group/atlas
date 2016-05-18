
package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

public interface ExpressionsRowRawDeserializerBuilder<T extends Expression> {

    ExpressionsRowRawDeserializerBuilder forExperiment(String experimentAccession);

    ExpressionsRowRawDeserializerBuilder withHeaders(String... tsvFileHeaders);

    ExpressionsRowRawDeserializer<T> build();

}
