
package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static com.google.common.base.Preconditions.checkState;

/*
 * Converts a single row of expression values, eg:
 *
 * Gene ID, Gene Name, g1, g2, g3, g4, g5
 * mus1, musName, 1, 2, 3, 4, 5
 *
 * into Expressions objects, eg:
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 *
 * Implemented as a iterator, from which the next expression in pulled off one by one
 */
public abstract class ExpressionsRowTsvDeserializer<T extends Expression> implements ExpressionsRowDeserializer<String, T> {

    private Queue<String> tsvRow = new LinkedList<>();

    @Override
    public ExpressionsRowTsvDeserializer reload(String... values) {
        checkState(this.tsvRow.isEmpty(), "Reload must be invoked only when readNext returns null");

        Collections.addAll(this.tsvRow, values);

        return this;
    }

    @Override
    public T next(){
        return nextExpression(tsvRow);
    }

    @Override
    public abstract T nextExpression(Queue<String> expressionLevelsBuffer);
}
