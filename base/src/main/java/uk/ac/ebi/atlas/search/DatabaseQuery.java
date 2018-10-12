
package uk.ac.ebi.atlas.search;

import com.google.common.collect.Lists;

import java.util.List;

public class DatabaseQuery<T> {

    private List<T> params = Lists.newArrayList();
    private StringBuilder queryBuilder = new StringBuilder();

    public void addParameter(T value) {
        params.add(value);
    }

    public List<T> getParameters() {
        return params;
    }

    public String getQuery() {
        return queryBuilder.toString();
    }

    void setQueryString(String query) {
        queryBuilder = new StringBuilder(query);
    }

    public DatabaseQuery appendToQueryString(String text) {
        queryBuilder.append(text);
        return this;
    }

    @Override
    public String toString() {
        return "query: " + getQuery() + ", params: " + params;
    }
}
