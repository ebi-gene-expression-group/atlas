package uk.ac.ebi.atlas.geneindex;

import com.google.common.base.Joiner;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class IndexClient {

    private RestTemplate restTemplate;

    private String serverURL;

    @Inject
    public IndexClient(RestTemplate restTemplate, GenePropertyQueryBuilder queryBuilder) {
        this.restTemplate = restTemplate;
        this.queryBuilder = queryBuilder;
    }

    private GenePropertyQueryBuilder queryBuilder;

    @Value("#{configuration['index.server.url']}")
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String findGeneIdJson(String searchText, String organism) {

        Map<String, String> variables = new HashMap<>(2);
        variables.put("query", queryBuilder.buildQueryString(searchText));
        variables.put("organism", ":\"" + organism.toLowerCase() + "\"");

        String object = restTemplate.getForObject(serverURL + "select?q={query} " +
                "AND species{organism}&start=0&rows=100000&fl=identifier&wt=json", String.class, variables);

        System.out.println("object = " + object);

        return object;
    }

    public Set<String> findGeneIds(String searchText, String organism) {

        return parseJson(findGeneIdJson(checkNotNull(searchText), organism));

    }



    protected String buildQueryString(Collection<String> queryValues) {
        StringBuilder stringBuilder = new StringBuilder("(alltext:\"");
        return Joiner.on("\" OR alltext:\"").appendTo(stringBuilder, queryValues).append("\")").toString();
    }

    protected Set<String> parseJson(String text) {
        Set<String> result = new HashSet<>();
        try (JsonReader reader = new JsonReader(new StringReader(text))) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("response")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        name = reader.nextName();
                        if (name.equals("docs")) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                reader.beginObject();
                                name = reader.nextName();
                                if (name.equals("identifier")) {
                                    result.add(reader.nextString());
                                } else {
                                    reader.skipValue();
                                }
                                reader.endObject();
                            }
                            reader.endArray();

                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return result;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
