package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.jayway.jsonpath.JsonPath;

import javax.inject.Named;

@Named
public class BaselineAnalyticsExpressionAvailableReader {

    private static final String NUMFOUND_PATH = "$.response.numFound";

    public int extractResultCount(String json) {
        return JsonPath.read(json, NUMFOUND_PATH);
    }
}
