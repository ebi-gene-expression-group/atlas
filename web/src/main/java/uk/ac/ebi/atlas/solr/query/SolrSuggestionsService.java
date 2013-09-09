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

package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("prototype") //can't be singleton because RestTemplate is not thread safe
public class SolrSuggestionsService {
    private static final Logger LOGGER = Logger.getLogger(SolrSuggestionsService.class);

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w ]|_");

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE = "suggest_properties?q=\"{0}\" AND species:\"{1}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    private static final String SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE_NO_SPECIES = "suggest_properties?q=\"{0}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    @Value("#{configuration['index.property_names.bioentity_name']}")
    private String[] bioentityNamePropertyNames;

    @Value("#{configuration['index.property_names.synonym']}")
    private String[] synonymPropertyNames;

    @Value("#{configuration['index.property_names.identifier']}")
    private String[] identifierPropertyNames;

    @Value("#{configuration['index.server.gxa.url']}")
    private String serverURL;

    private RestTemplate restTemplate;

    private final SolrQueryBuilderFactory solrQueryBuilderFactory;

    private GxaSolrServer solrServer;

    @Inject
    public SolrSuggestionsService(RestTemplate restTemplate, SolrQueryBuilderFactory solrQueryBuilderFactory, GxaSolrServer solrServer) {
        this.restTemplate = restTemplate;
        this.solrQueryBuilderFactory = solrQueryBuilderFactory;
        this.solrServer = solrServer;
    }

    String limitSpeciesNameToTwoWords(String species) {

        String[] words = StringUtils.split(species);
        if (ArrayUtils.getLength(words) > 2){
            return words[0].concat(" ").concat(words[1]);
        }
        return species;
    }

    public List<String> findGeneIdSuggestionsInName(String geneName, String species) {

        species = limitSpeciesNameToTwoWords(species);

        return getGeneIdSuggestionsInName(geneName, species);
    }

    public List<String> findGeneIdSuggestionsInSynonym(String geneName, String species) {

        species = limitSpeciesNameToTwoWords(species);

        return getGeneIdSuggestionsInSynonym(geneName, species);
    }

    public List<String> findGeneIdSuggestionsInIdentifier(String geneName, String species) {

        species = limitSpeciesNameToTwoWords(species);

        return getGeneIdSuggestionsInIdentifier(geneName, species);

    }

    public List<String> findGenePropertySuggestions(String multiTermToken, String species) {

        species = limitSpeciesNameToTwoWords(species);

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Lists.newArrayList();
        }

        String jsonString;
        if (StringUtils.isNotBlank(species)){
            jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE, multiTermToken, species);
        } else {
            jsonString = getJsonResponse(SOLR_AUTOCOMPLETE_PROPERTIES_TEMPLATE_NO_SPECIES, multiTermToken);
        }
        return extractCollations(jsonString);
    }

    List<String> getGeneIdSuggestionsInName(String queryString, String species) {

        return getSuggestions(queryString, species, bioentityNamePropertyNames);
    }

    List<String> getGeneIdSuggestionsInSynonym(String queryString, String species) {

        return getSuggestions(queryString, species, synonymPropertyNames);
    }

    List<String> getGeneIdSuggestionsInIdentifier(String queryString, String species) {

        return getSuggestions(queryString, species, identifierPropertyNames);
    }

    List<String> getSuggestions(String queryString, String species, String[] propertyNames) {

        SolrQuery solrQuery = solrQueryBuilderFactory.createPropertyValueQueryBuilder()
                .withSpecies(species)
                .withBioentityTypes(BioentityType.getAllSolrAliases())
                .withPropertyNames(propertyNames)
                .buildPropertyValueAutocompleteQuery(queryString);

        return getSolrResultsForQuery(solrQuery);
    }

    List<String> getSolrResultsForQuery(SolrQuery solrQuery) {

        LOGGER.debug("<getSolrResultsForQuery> processing solr query: " + solrQuery.getQuery());

        QueryResponse solrResponse = solrServer.query(solrQuery);

        List<String> geneNames = Lists.newArrayList();
        if (solrResponse.getFacetFields().get(0).getValues() != null) {
            for (FacetField.Count facetField : solrResponse.getFacetFields().get(0).getValues()) {
                geneNames.add(facetField.getName());
            }
        }
        return geneNames;
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
