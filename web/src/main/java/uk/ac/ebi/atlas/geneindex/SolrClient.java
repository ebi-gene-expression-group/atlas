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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
public class SolrClient {
    private static final Logger LOGGER = Logger.getLogger(SolrClient.class);

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w\\d ]");

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE = "suggest_properties?q=\"{0}\" AND species:\"{1}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    private RestTemplate restTemplate;

    private final SolrQueryService solrQueryService;

    @Inject
    public SolrClient(RestTemplate restTemplate, SolrQueryService solrQueryService) {
        this.restTemplate = restTemplate;
        this.solrQueryService = solrQueryService;
    }

    public Set<String> findGeneIds(String searchText, String species) {

        String geneQuery = buildQueryAllTextString(customEscape(searchText));

        try {
            return toUppercase(solrQueryService.getGeneIds(geneQuery, species));
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIds> error querying solr service", e);
        }

        return Collections.EMPTY_SET;
    }

    public List<String> findGeneIdSuggestionsInName(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInName(geneName, species);
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInName> error querying solr service", e);
        }

        return Collections.EMPTY_LIST;
    }

    public List<String> findGeneIdSuggestionsInSynonym(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInSynonym(geneName, species);
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInSynonym> error querying solr service", e);
        }

        return Collections.EMPTY_LIST;
    }

    public List<String> findGeneIdSuggestionsInIdentifier(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInIdentifier(geneName, species);
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInIdentifier> error querying solr service", e);
        }

        return Collections.EMPTY_LIST;
    }


    public List<String> findGenePropertySuggestions(String multiTermToken, String species) {

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Collections.EMPTY_LIST;
        }

        String jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE, multiTermToken, species);

        return extractCollations(jsonString);
    }

    JsonElement extractSuggestionsElement(String jsonString) {
        JsonObject spellCheckObject = new JsonParser().parse(jsonString).getAsJsonObject().getAsJsonObject("spellcheck");
        if (spellCheckObject != null) {
            return spellCheckObject.get("suggestions");
        }
        return null;
    }

    List<String> extractCollations(String jsonString) {
        List<String> suggestionStrings = new ArrayList<>();

        JsonElement suggestionsElement = extractSuggestionsElement(jsonString);

        if (suggestionsElement != null && suggestionsElement.isJsonArray()) {

            JsonArray suggestionElements = suggestionsElement.getAsJsonArray();

            for (JsonElement suggestionElement : suggestionElements) {
                JsonArray suggestionEntry = suggestionElement.getAsJsonArray();
                if ("collation".equals(suggestionEntry.get(0).getAsString())) {
                    String collation = suggestionEntry.get(1).getAsString();
                    suggestionStrings.add(extractSuggestion(collation));
                }
            }
        }
        return suggestionStrings;
    }

    String extractSuggestion(String collation) {
        return StringUtils.split(collation, "\"")[0];
    }

    String getJsonResponse(String restQueryTemplate, String... arguments) {
        if (StringUtils.isBlank(arguments[0])) {
            return "";
        }

        try {

            String jsonResponse = restTemplate.getForObject(serverURL + restQueryTemplate, String.class, arguments);

            LOGGER.debug("<getJsonResponse> response size (characters) = " + jsonResponse.length() + ", arguments: " + Arrays.toString(arguments));

            return jsonResponse;

        } catch (HttpClientErrorException e) {
            if (HttpStatus.BAD_REQUEST.equals(e.getStatusCode())) {
                throw new InvalidQueryException("Invalid gene query, please check syntax! ", e);
            }
            LOGGER.error("<getJsonResponse> error connecting to the solr service: " + serverURL, e);
            throw e;
        } catch (RestClientException e) {
            LOGGER.error("<getJsonResponse> error connecting to the solr service: " + serverURL, e);
            throw e;
        }
    }

    Set<String> toUppercase(List<String> geneIds) {
        Set<String> result = new HashSet<>();
        for (String geneId : geneIds) {
            result.add(geneId.toUpperCase());
        }
        return result;
    }

    String buildQueryAllTextString(String searchText) {
        StringBuilder stringBuilder = new StringBuilder("(property_search:");
        stringBuilder.append(searchText);
        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    private String customEscape(String searchText) {
        return searchText.replace(":", "\\:");
    }
}
