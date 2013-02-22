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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class SolrClient {
    private static final Logger LOGGER = Logger.getLogger(SolrClient.class);

    static final String JSON_PATH_SUGGESTION = "$..suggestion";

    private static final String JSON_PATH_GENE_IDENTIFIERS = "$.response.docs[*].identifier";

    private static final String SOLR_SEARCH_QUERY_TEMPLATE = "select?q={query} " +
            "AND species:{organism}&start=0&rows=100000&fl=identifier&wt=json";

    private static final String SOLR_AUTOCOMPLETE_GENENAMES_TEMPLATE = "suggest_genename?q={0}&wt=json&omitHeader=true";

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE = "suggest_properties?q={0}&wt=json&omitHeader=true&rows=0";

    private RestTemplate restTemplate;

    private String serverURL;

    private GenePropertyQueryBuilder queryBuilder;

    @Inject
    public SolrClient(RestTemplate restTemplate, GenePropertyQueryBuilder queryBuilder) {
        this.restTemplate = restTemplate;
        this.queryBuilder = queryBuilder;
    }

    @Value("#{configuration['index.server.url']}")
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public Set<String> findGeneIds(String searchText, Set<String> organisms) {
        String geneQuery = queryBuilder.buildQueryString(searchText);
        String organismsQuery = buildSpeciesQuery(organisms);

        String jsonString = getJsonResponse(SOLR_SEARCH_QUERY_TEMPLATE, geneQuery, organismsQuery);

        List<String> geneIds = JsonPath.read(jsonString, JSON_PATH_GENE_IDENTIFIERS);

        return toUppercase(geneIds);

    }

    public List<String> findGeneNameSuggestions(String query){
        String jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_GENENAMES_TEMPLATE, query);

        return JsonPath.read(jsonString, JSON_PATH_SUGGESTION);
    }

    public List<String> findGenePropertySuggestions(String query){
        String jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE, query);

        return JsonPath.read(jsonString, JSON_PATH_SUGGESTION);

    }

    String getJsonResponse(String restQueryTemplate, String... arguments) {
        try {

            String jsonResponse = restTemplate.getForObject(serverURL + restQueryTemplate, String.class, arguments);

            LOGGER.debug("<getJsonResponse> response size (characters) = " + jsonResponse.length() + ", arguments: " + Arrays.toString(arguments));

            return jsonResponse;

        } catch (RestClientException e) {
            LOGGER.fatal("<getJsonResponse> error connecting to the solr service: " + serverURL, e);
            throw e;
        }
    }

    String buildSpeciesQuery(Set<String> organisms) {
        return "(\"".concat(StringUtils.join(organisms, "\" OR \"").toLowerCase().concat("\")"));
    }

    Set<String> toUppercase(List<String> geneIds) {
        Set<String> result = new HashSet<>();
        for (String geneId : geneIds) {
            result.add(geneId.toUpperCase());
        }
        return result;
    }

}
