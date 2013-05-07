/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
public class SolrClient {
    private static final Logger LOGGER = Logger.getLogger(SolrClient.class);

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w ]|_");

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE = "suggest_properties?q=\"{0}\" AND species:\"{1}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    @Value("#{configuration['index.types.tooltip']}")
    private String tooltipPropertyTypes;

    @Value("#{configuration['index.types.genepage']}")
    private String genePagePropertyTypes;

    private RestTemplate restTemplate;

    private final SolrQueryService solrQueryService;

    @Inject
    public SolrClient(RestTemplate restTemplate, SolrQueryService solrQueryService) {
        this.restTemplate = restTemplate;
        this.solrQueryService = solrQueryService;
    }

    void setTooltipPropertyTypes(String tooltipPropertyTypes) {
        this.tooltipPropertyTypes = tooltipPropertyTypes;
    }

    void setGenePagePropertyTypes(String genePagePropertyTypes) {
        this.genePagePropertyTypes = genePagePropertyTypes;
    }

    public Multimap<String, String> fetchTooltipProperties(String identifier) {

        String[] propertyTypes = tooltipPropertyTypes.trim().split(",");
        return fetchProperties(identifier, propertyTypes);

    }

    public Multimap<String, String> fetchGenePageProperties(String identifier) {

        String[] propertyTypes = genePagePropertyTypes.trim().split(",");
        return fetchProperties(identifier, propertyTypes);

    }

    Multimap<String, String> fetchProperties(String identifier, String[] propertyTypes) {

        try {
            return solrQueryService.fetchProperties(identifier, propertyTypes);
        } catch (SolrServerException e) {
            LOGGER.error("<fetchProperties> error querying solr service", e);
            throw new IllegalStateException(e);
        }

    }

    public String findSpeciesForGeneId(String identifier) {

        try {
            return solrQueryService.getSpeciesForIdentifier(identifier);
        } catch (SolrServerException e) {
            LOGGER.error("<fetchProperties> error querying solr service", e);
            throw new IllegalStateException(e);
        }
    }

    public Set<String> findGeneIds(String geneQuery, boolean exactMatch, String species) throws GenesNotFoundException {
        try {
            String lowercaseSpecies = species.toLowerCase();
            List<String> geneIds = solrQueryService.getGeneIds(geneQuery, exactMatch, lowercaseSpecies);
            if (geneIds.isEmpty()) {
                throw new GenesNotFoundException("No genes found for searchText = " + geneQuery + ", species = " + lowercaseSpecies);
            }
            return toUppercase(geneIds);
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIds> error querying solr service", e);
            throw new IllegalStateException(e);
        }
    }

    public List<String> findGeneIdSuggestionsInName(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInName(geneName, species.toLowerCase());
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInName> error querying solr service", e);
            return Collections.EMPTY_LIST;
        }

    }

    public List<String> findGeneIdSuggestionsInSynonym(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInSynonym(geneName, species.toLowerCase());
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInSynonym> error querying solr service", e);
            return Collections.EMPTY_LIST;
        }

    }

    public List<String> findGeneIdSuggestionsInIdentifier(String geneName, String species) {

        try {
            return solrQueryService.getGeneIdSuggestionsInIdentifier(geneName, species.toLowerCase());
        } catch (SolrServerException e) {
            LOGGER.error("<findGeneIdSuggestionsInIdentifier> error querying solr service", e);
            return Collections.EMPTY_LIST;
        }

    }

    public List<String> findGenePropertySuggestions(String multiTermToken, String species) {

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Collections.EMPTY_LIST;
        }

        String jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE, multiTermToken, species.toLowerCase());

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
        try {
            if (StringUtils.isBlank(arguments[0])) {
                return StringUtils.EMPTY;
            }
            return restTemplate.getForObject(serverURL + restQueryTemplate, String.class, arguments);

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
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

}
