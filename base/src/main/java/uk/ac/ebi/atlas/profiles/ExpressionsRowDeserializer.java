package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

import java.util.Queue;

public interface ExpressionsRowDeserializer<V, T extends Expression> {
    // Warning because @SafeVarArgs can’t be added to non-final/non-static methods...
    ExpressionsRowDeserializer<V, T> reload(V... values);
    T next();
    T nextExpression(Queue<V> expressionLevelsBuffer);
}
