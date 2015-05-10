package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

import java.util.Queue;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 06/05/15.
 */
public interface ExpressionsRowDeserializer<V, T extends Expression> {
    // Because @SafeVarArgs can’t be added to non-final methods...
    @SuppressWarnings ({"unchecked", "varargs"})
    ExpressionsRowDeserializer<V, T> reload(V... values);
    T next();
    T nextExpression(Queue<V> expressionLevelsBuffer);
}
