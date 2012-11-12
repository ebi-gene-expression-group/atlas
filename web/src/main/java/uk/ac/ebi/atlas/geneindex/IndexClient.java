package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.Joiner;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Named
public class IndexClient {

    private RestTemplate restTemplate;

    @Inject
    public IndexClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public String findGeneIds(Collection<String> queryValues, String organism) throws URISyntaxException {

        Map<String, String> variables = new HashMap<>(2);
        variables.put("query", buildQueryString(queryValues));
        variables.put("organism", ":\"" + organism + "\"");

        String object = restTemplate.getForObject("http://localhost:8983/solr/select?q={query} " +
                "AND species{organism}&start=0&rows=100000&fl=identifier&wt=json", String.class, variables);

        System.out.println("object = " + object);

        return object;
    }

    protected String buildQueryString(Collection<String> queryValues) {
        StringBuilder stringBuilder = new StringBuilder("(alltext:\"");
        return Joiner.on("\" OR alltext:\"").appendTo(stringBuilder, queryValues).append("\")").toString();
    }
}
