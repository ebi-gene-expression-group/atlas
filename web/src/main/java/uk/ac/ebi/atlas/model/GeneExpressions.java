package uk.ac.ebi.atlas.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GeneExpressions implements Iterable<Expression> {
    Set<Expression> expressions = new HashSet<>();

    @Override
    public Iterator<Expression> iterator() {
        return expressions.iterator();
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }
}
