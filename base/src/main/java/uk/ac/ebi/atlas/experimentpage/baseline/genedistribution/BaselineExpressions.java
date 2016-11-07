
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import uk.ac.ebi.atlas.model.baseline.BaselineExpression;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BaselineExpressions implements Iterable<BaselineExpression> {

    private Set<BaselineExpression> expressions = new HashSet<>();

    @Override
    public Iterator<BaselineExpression> iterator() {
        return expressions.iterator();
    }

    public void addExpression(BaselineExpression expression) {
        expressions.add(expression);
    }
}
