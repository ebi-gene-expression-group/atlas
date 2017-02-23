package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

public interface ExpressionsRowDeserializerBuilder<Expr extends Expression> {

    ExpressionsRowDeserializer<Expr> build(String... tsvFileHeaders);

}
