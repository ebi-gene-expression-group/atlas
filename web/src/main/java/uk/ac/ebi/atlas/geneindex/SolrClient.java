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
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class SolrClient {
    private static final Logger logger = Logger.getLogger(SolrClient.class);

    static final String JSON_PATH_SUGGESTION_EXPRESSION = "$..suggestion";

    private static final String JSON_PATH_GENE_IDENTIFIERS_EXPRESSION = "$.response.docs[*].identifier";

    private static final String SOLR_REST_QUERY_TEMPLATE = "select?q={query} " +
            "AND species:{organism}&start=0&rows=100000&fl=identifier&wt=json";

    private static final String SOLR_REST_GENENAMES_QUERY_TEMPLATE = "suggest_genename?q={0}&wt=json&omitHeader=true";

    private static final String SOLR_REST_PROPERTIES_QUERY_TEMPLATE = "suggest_properties?q={0}&wt=json&omitHeader=true&rows=0";

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
        String jsonString = findGeneIdJson(searchText, organisms);
        List<String> geneIds = JsonPath.read(jsonString, JSON_PATH_GENE_IDENTIFIERS_EXPRESSION);

        return toUppercase(geneIds);

    }

    public Set<String> toUppercase(List<String> geneIds) {
        Set<String> result = new HashSet<>();
        for (String geneId : geneIds) {
            result.add(geneId.toUpperCase());
        }
        return result;
    }

    protected String findGeneIdJson(String searchText, Set<String> organisms) {

        try {

            String geneProperty = queryBuilder.buildQueryString(searchText);
            String organismQuery = buildSpeciesQuery(organisms);

            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());

            stopWatch.start();

            String jsonResponse = restTemplate.getForObject(serverURL + SOLR_REST_QUERY_TEMPLATE, String.class, geneProperty, organismQuery);

            stopWatch.stop();

            logger.info("<findGeneIdJson> time taken " + stopWatch.getTotalTimeSeconds() + " s - solr response for geneQuery: " + geneProperty + " - organismQuery: " + organismQuery);

            return jsonResponse;
        } catch (RestClientException e) {
            logger.fatal("<findGeneIdJson> error connecting to the solr service: " + serverURL, e);
            throw e;
        }
    }

    protected String buildSpeciesQuery(Set<String> organisms) {
        return "(\"".concat(StringUtils.join(organisms, "\" OR \"").toLowerCase().concat("\")"));
    }

    public List<String> findGeneNameSuggestions(String query){
        String jsonString = getJsonSuggestions(query, SOLR_REST_GENENAMES_QUERY_TEMPLATE);

        return JsonPath.read(jsonString, JSON_PATH_SUGGESTION_EXPRESSION);
    }

    //ToDo: maybe should be extracted as a class to get json responses from generic rest services, setting a url and parameters
    protected String getJsonSuggestions(String query, String restQueryTemplate) {
        try {

            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());

            stopWatch.start();

            String jsonResponse = restTemplate.getForObject(serverURL + restQueryTemplate, String.class, query);

            stopWatch.stop();

            logger.info("<getJsonSuggestions> time taken " + stopWatch.getTotalTimeSeconds() + " s - solr response for suggestion query: " + query);

            return jsonResponse;

        } catch (RestClientException e) {
            logger.fatal("<getJsonSuggestions> error connecting to the solr service: " + serverURL, e);
            throw e;
        }
    }

    public List<String> findGenePropertySuggestions(String query){
        String jsonString = getJsonSuggestions(query, SOLR_REST_PROPERTIES_QUERY_TEMPLATE);

        return JsonPath.read(jsonString, JSON_PATH_SUGGESTION_EXPRESSION);

    }

}
