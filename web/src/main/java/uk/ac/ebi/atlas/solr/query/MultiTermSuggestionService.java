package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Lists;
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

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Scope("prototype") //can't be singleton because RestTemplate is not thread safe
public class MultiTermSuggestionService {

    private static final Logger LOGGER = Logger.getLogger(MultiTermSuggestionService.class);

    private RestTemplate restTemplate;

    @Value("#{configuration['index.server.gxa.url']}")
    private String serverURL;

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w ]|_");

    private static final String SOLR_SPELLING_SUGGESTER_TEMPLATE = "suggest_properties?q=\"{0}\" AND species:\"{1}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    private static final String SOLR_SPELLING_SUGGESTER_TEMPLATE_NO_SPECIES = "suggest_properties?q=\"{0}\"&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    @Inject
    public MultiTermSuggestionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> fetchMultiTermSuggestions(String multiTermToken, String species) {
        // uses Spelling suggester, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Solr+server#Solrserver-Suggestcomponent
        // ie: http://lime:8983/solr/gxa/suggest_properties?q="<multiTermToken>"&wt=json&omitHeader=true&rows=0&json.nl=arrarr

        species = SolrQueryUtil.limitSpeciesNameToTwoWords(species);

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Lists.newArrayList();
        }

        String jsonString;
        if (StringUtils.isNotBlank(species)) {
            jsonString = getJsonResponse(SOLR_SPELLING_SUGGESTER_TEMPLATE, multiTermToken, species);
        } else {
            jsonString = getJsonResponse(SOLR_SPELLING_SUGGESTER_TEMPLATE_NO_SPECIES, multiTermToken);
        }
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
