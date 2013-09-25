package uk.ac.ebi.atlas.dao;

import com.google.common.collect.Lists;

import java.util.List;

public class AssayGroupQuery {

    private List<String> values = Lists.newArrayList();
    private String query;

    void addValue(String value) {
        values.add(value);
    }

    String[] getValues() {
        return values.toArray(new String[values.size()]);
    }

    String getQuery() {
        return query;
    }

    void setQueryString(String query) {
        this.query = query;
    }
}
