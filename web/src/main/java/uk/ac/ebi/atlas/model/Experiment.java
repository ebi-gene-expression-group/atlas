package uk.ac.ebi.atlas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//ToDo: remove this class, not required and doesn't make sense since we are exposing top tens and experiments are not made of top tens
public class Experiment implements Iterable<ExpressionLevel> {

    private String accession;

    private List<ExpressionLevel> expressionLevels = new ArrayList<>();

    public Experiment(String accession) {
        this.accession = accession;
    }

    public String getAccession() {
        return accession;
    }

    public List<ExpressionLevel> getExpressionLevels() {
        return expressionLevels;
    }

    public int size() {
        return getExpressionLevels().size();
    }

    public Experiment add(ExpressionLevel expressionLevel) {
        getExpressionLevels().add(expressionLevel);
        return this;
    }

    public boolean addAll(Collection<? extends ExpressionLevel> expressionLevels) {
        return getExpressionLevels().addAll(expressionLevels);
    }

    @Override
    public Iterator<ExpressionLevel> iterator() {
        return expressionLevels.iterator();
    }

}
