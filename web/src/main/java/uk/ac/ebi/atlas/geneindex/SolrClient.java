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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;

@Named
@Scope("prototype")
public class SolrClient {
    private static final Logger logger = Logger.getLogger(SolrClient.class);

    private static final String JSON_PATH_EXPRESSION = "$.response.docs[*].identifier";

    private static final String SOLR_REST_QUERY_TEMPLATE = "select?q={query} " +
            "AND species:{organism}&start=0&rows=100000&fl=identifier&wt=json";

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
        List<String> geneIds = jsonToString(jsonString);

        return toUppercase(geneIds);

    }

    public Set<String> toUppercase(List<String> geneIds) {
        return Sets.newHashSet(Iterables.transform(geneIds, new Function<String, String>() {
            public String apply(String geneId) {
                return geneId.toUpperCase();
            }
        }));
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
        } catch (Exception e) {
            // catching Exception instead of Throwable was suggested by Sonar, as Throwable also includes Errors
            logger.fatal("<findGeneIdJson> error connecting to the solr service: " + serverURL, e);
            throw e;
        }
    }

    protected String buildSpeciesQuery(Set<String> organisms) {
        return "(\"".concat(StringUtils.join(organisms, "\" OR \"").toLowerCase().concat("\")"));
    }

    protected List<String> jsonToString(String jsonString) {
        return JsonPath.read(jsonString, JSON_PATH_EXPRESSION);
    }

}
