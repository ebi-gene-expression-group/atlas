package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

import java.util.Collection;
import java.util.Queue;

public interface ExpressionsRowDeserializer<Expr extends Expression> {
    Collection<Expr> deserializeRow(String[] values);
}
