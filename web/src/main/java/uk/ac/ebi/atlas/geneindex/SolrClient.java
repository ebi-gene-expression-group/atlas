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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("singleton")
public class SolrClient {
    private static final Logger LOGGER = Logger.getLogger(SolrClient.class);

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w ]|_");

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE = "suggest_properties?q=\"{0}\" AND species:\"{1}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    private final BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    @Value("#{configuration['index.server.url']}")
    private String serverURL;

    @Value("#{configuration['index.types.tooltip']}")
    private String tooltipPropertyTypes;

    private RestTemplate restTemplate;

    private final SolrQueryService solrQueryService;

    @Inject
    public SolrClient(RestTemplate restTemplate, SolrQueryService solrQueryService, BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.restTemplate = restTemplate;
        this.solrQueryService = solrQueryService;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
    }

    void setTooltipPropertyTypes(String tooltipPropertyTypes) {
        this.tooltipPropertyTypes = tooltipPropertyTypes;
    }

    public SortedSetMultimap<String, String> fetchTooltipProperties(String identifier) {

        List<String> propertyTypes = Arrays.asList(tooltipPropertyTypes.trim().split(","));
        return fetchProperties(identifier, propertyTypes);

    }

    public SortedSetMultimap<String, String> fetchGenePageProperties(String identifier, List<String> propertyTypes) {
        SortedSetMultimap<String, String> propertiesByType = fetchProperties(identifier, propertyTypes);
        if (propertiesByType.isEmpty()) {
            throw new ResultNotFoundException("Gene/protein with accession : " + identifier + " is not found!");
        }
        return propertiesByType;
    }

    SortedSetMultimap<String, String> fetchProperties(String identifier, List<String> propertyTypes) {

        return solrQueryService.fetchProperties(identifier, propertyTypes);

    }

    public String findSpeciesForBioentityId(String identifier) {

        return getSpeciesForPropertyValue(identifier, SolrQueryService.IDENTIFIER_FIELD);

    }

    public List<String> findPropertyValuesForGeneId(String identifier, String propertyType) {

        return solrQueryService.getPropertyValuesForIdentifier(identifier, propertyType);

    }

    public String getSpeciesForPropertyValue(String propertyValue) {
        return getSpeciesForPropertyValue(propertyValue, "");
    }

    public String getSpeciesForPropertyValue(String propertyValue, String propertyName) {
        List<String> propertyValueTokens = bioentityPropertyValueTokenizer.split(propertyValue);
        Set<String> species = Sets.newHashSet();
        for (String propertyValueToken : propertyValueTokens) {
            species.addAll(solrQueryService.getSpeciesForPropertyValue(propertyValueToken, propertyName));
        }
        if (species.size() != 1) {
            throw new ResourceNotFoundException("Exact species can't be determined for propertyValue: " + propertyValue + " and propertyName: " + propertyName);
        }
        return species.iterator().next();
    }

    public GeneQueryResponse findGeneSets(String geneQuery, boolean exactMatch, String species, boolean tokenizeQuery) throws GenesNotFoundException {

        checkArgument(StringUtils.isNotBlank(geneQuery));

        GeneQueryResponse geneQueryResponse = new GeneQueryResponse();

        if (tokenizeQuery) {
            for (String queryToken : bioentityPropertyValueTokenizer.split(geneQuery)) {
                Set<String> geneIds = solrQueryService.getGeneIds(queryToken, exactMatch, species);
                geneQueryResponse.addGeneIds(queryToken, geneIds);
            }
        } else {
            Set<String> geneIds = solrQueryService.getGeneIds(geneQuery, exactMatch, species);
            geneQueryResponse.addGeneIds(geneQuery, geneIds);
        }
        return geneQueryResponse;

    }

    public List<String> findGeneIdSuggestionsInName(String geneName, String species) {

        return solrQueryService.getGeneIdSuggestionsInName(geneName, species.toLowerCase());
    }

    public List<String> findGeneIdSuggestionsInSynonym(String geneName, String species) {

        return solrQueryService.getGeneIdSuggestionsInSynonym(geneName, species.toLowerCase());
    }

    public List<String> findGeneIdSuggestionsInIdentifier(String geneName, String species) {

        return solrQueryService.getGeneIdSuggestionsInIdentifier(geneName, species.toLowerCase());

    }

    public List<String> findGenePropertySuggestions(String multiTermToken, String species) {

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Lists.newArrayList();
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

    String getJsonResponse(String restQueryTemplate, Object... arguments) {
        checkArgument(arguments != null && arguments.length > 0);
        try {

            return restTemplate.getForObject(serverURL + restQueryTemplate, String.class, arguments);

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

}
