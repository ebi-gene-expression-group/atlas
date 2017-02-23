package uk.ac.ebi.atlas.profiles.tsv;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;

import java.util.*;

public abstract class DifferentialExpressionsRowDeserializer<Expr extends DifferentialExpression>
        implements ExpressionsRowDeserializer<Expr> {

    @Override
    public Collection<Expr> deserializeRow(String[] values) {
        Queue<String> tsvRow = new LinkedList<>(Arrays.asList(values));
        List<Expr> result = new ArrayList<>();
        Expr differentialExpression = nextExpression(tsvRow);
        while(differentialExpression!= null){
            result.add(differentialExpression);
            differentialExpression = nextExpression(tsvRow);
        }
        return result;
    }

    public abstract Expr nextExpression(Queue<String> tsvRow);

    protected double parseDouble(String value) {
        if (value.equalsIgnoreCase("inf")) {
            return Double.POSITIVE_INFINITY;
        }
        if (value.equalsIgnoreCase("-inf")) {
            return Double.NEGATIVE_INFINITY;
        }
        return Double.parseDouble(value);
    }

}
