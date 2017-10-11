package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class GeneProfilesList<T extends Profile> extends ArrayList<T> {

    private Integer totalResultCount = 0;

    public GeneProfilesList(Collection<T> collection) {
        super(collection);
    }

    public GeneProfilesList() {
    }

    public Integer getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }


    public Map<String, String> properties() {
        return ImmutableMap.of("searchResultTotal", Integer.toString(getTotalResultCount()));
    }

}
