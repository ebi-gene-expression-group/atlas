package uk.ac.ebi.atlas.utils.spring.servlet.view;

import uk.ac.ebi.atlas.utils.Visitor;

public class CountingVisitor<T> implements Visitor<T> {

    private int count = 0;

    private Visitor<T> visitor;

    public CountingVisitor(Visitor<T> visitor) {
        this.visitor = visitor;
    }

    @Override
    public void visit(T value) {
        count++;
        visitor.visit(value);
    }

    public int getCount() {
        return count;
    }
}
