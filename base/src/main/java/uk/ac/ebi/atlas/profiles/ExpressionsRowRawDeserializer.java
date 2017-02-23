
package uk.ac.ebi.atlas.profiles;

import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.profiles.tsv.ExpressionsRowDeserializer;

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
public abstract class ExpressionsRowRawDeserializer<T extends Expression> implements ExpressionsRowDeserializer<BaselineExpression, T> {

    private Queue<BaselineExpression> rawValuesRow = new LinkedList<>();

    @Override
    public ExpressionsRowRawDeserializer<T> reload(BaselineExpression... values) {
        checkState(this.rawValuesRow.isEmpty(), "Reload must be invoked only when readNext returns null");

        Collections.addAll(this.rawValuesRow, values);

        return this;
    }

    @Override
    public T next(){
        return nextExpression(rawValuesRow);
    }

    @Override
    public abstract T nextExpression(Queue<BaselineExpression> expressionLevelsBuffer);
}
