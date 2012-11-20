package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.Joiner;
import com.jayway.jsonpath.JsonPath;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URISyntaxException;
import java.util.*;

@Named
@Scope("prototype")
public class IndexClient {

    private static final String JSON_PATH_EXPRESSION = "$.response.docs[*].identifier";

    private static final String SOLR_REST_QUERY_TEMPLATE = "http://localhost:8983/solr/select?q={query} " +
                                                "AND species{organism}&start=0&rows=100000&fl=identifier&wt=json";

    private RestTemplate restTemplate;

    @Inject
    public IndexClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public String findGeneIdJson(Collection<String> queryValues, String organism) throws IllegalStateException {

        String queryString = buildQueryString(queryValues);

        String object = restTemplate.getForObject(SOLR_REST_QUERY_TEMPLATE, String.class, queryString, organism);

        return object;
    }

    public List<String> findGeneIds(Set<String> queryValues, String organism) {
        String jsonString = findGeneIdJson(queryValues,  organism);
        return extractGeneIds(jsonString);
    }

    protected List<String> extractGeneIds(String jsonString){
        return JsonPath.read(jsonString, JSON_PATH_EXPRESSION);
    }

    protected String buildQueryString(Collection<String> queryValues) {
        StringBuilder stringBuilder = new StringBuilder("(alltext:\"");
        return Joiner.on("\" OR alltext:\"").appendTo(stringBuilder, queryValues).append("\")").toString();
    }
}
