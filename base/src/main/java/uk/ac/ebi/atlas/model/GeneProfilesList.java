package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GeneProfilesList<P extends Profile> extends ArrayList<P> {
    private long totalResultCount;

    public GeneProfilesList(Collection<P> collection) {
        super(collection);
    }

    public GeneProfilesList() {
    }

    public void setTotalResultCount(long totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public Map<String, String> properties() {
        return ImmutableMap.of("searchResultTotal", Long.toString(totalResultCount));
    }
}
