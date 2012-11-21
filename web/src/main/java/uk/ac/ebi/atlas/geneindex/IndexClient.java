/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.geneindex;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Scope("prototype")
public class IndexClient {

    private static final String JSON_PATH_EXPRESSION = "$.response.docs[*].identifier";

    private static final String SOLR_REST_QUERY_TEMPLATE = "select?q={query} " +
            "AND species:{organism}&start=0&rows=100000&fl=identifier&wt=json";

    private RestTemplate restTemplate;

    private String serverURL;

    private GenePropertyQueryBuilder queryBuilder;

    @Inject
    public IndexClient(RestTemplate restTemplate, GenePropertyQueryBuilder queryBuilder) {
        this.restTemplate = restTemplate;
        this.queryBuilder = queryBuilder;
    }

    @Value("#{configuration['index.server.url']}")
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public List<String> findGeneIds(String searchText, String organism) {
        String jsonString = findGeneIdJson(searchText, organism);
        return extractGeneIds(jsonString);
    }

    protected String findGeneIdJson(String searchText, String organism) {

        String geneProperty = queryBuilder.buildQueryString(searchText);
        String organismQuery = "\"" + organism.toLowerCase() + "\"";

        String object = restTemplate.getForObject(serverURL + SOLR_REST_QUERY_TEMPLATE, String.class, geneProperty, organismQuery);

        return object;
    }

    protected List<String> extractGeneIds(String jsonString) {
        return JsonPath.read(jsonString, JSON_PATH_EXPRESSION);
    }

}
