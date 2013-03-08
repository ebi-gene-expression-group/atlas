package uk.ac.ebi.atlas.model;

import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractGeneProfile<T> implements Iterable<T> {
    private String geneId;

    public AbstractGeneProfile(String geneId) {
        this.geneId = geneId;
    }


    public String getGeneId() {
        return geneId;
    }

    public boolean isEmpty() {
        return getExpressions().isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return getExpressions().iterator();
    }

    protected abstract Collection<T> getExpressions();

}
