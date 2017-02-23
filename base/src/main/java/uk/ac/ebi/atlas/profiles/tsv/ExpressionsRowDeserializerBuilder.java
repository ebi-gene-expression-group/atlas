package uk.ac.ebi.atlas.profiles.tsv;

import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;

public interface ExpressionsRowDeserializerBuilder<Expr extends Expression> {

    ExpressionsRowDeserializer<Expr> build(String... tsvFileHeaders);

}
