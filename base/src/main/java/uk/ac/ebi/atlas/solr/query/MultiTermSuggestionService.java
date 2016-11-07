package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTermSuggestionService.class);

    private RestTemplate restTemplate;
    private String serverURL;

    private static final Pattern NON_WORD_CHARACTERS_PATTERN = Pattern.compile("[^\\w -]|_");

    private static final String SOLR_SPELLING_SUGGESTER_TEMPLATE = "suggest_properties?spellcheck.q={0}&wt=json&omitHeader=true&rows=0&json.nl=arrarr";

    @Inject
    public MultiTermSuggestionService(RestTemplate restTemplate, @Qualifier("solrSuggestionServerURL") String serverURL) {
        this.serverURL = serverURL;
        this.restTemplate = restTemplate;
    }

    public List<SemanticQueryTerm> fetchMultiTermSuggestions(String multiTermToken) {
        // uses Spelling suggester, see https://www.ebi.ac.uk/seqdb/confluence/display/GXA/Solr+server#Solrserver-Suggestcomponent
        // ie: http://lime:8983/solr/gxa/suggest_properties?spellcheck.q=<multiTermPhrase>&wt=json&omitHeader=true&rows=0&json.nl=arrarr

        Matcher notSpellCheckableMatcher = NON_WORD_CHARACTERS_PATTERN.matcher(multiTermToken);

        if (notSpellCheckableMatcher.find()) {
            return Lists.newArrayList();
        }

        String jsonString = getJsonResponse(SOLR_SPELLING_SUGGESTER_TEMPLATE, multiTermToken);
        return extractSuggestions(jsonString);
    }

    JsonElement extractSuggestionsElement(String jsonString) {
        JsonObject spellCheckObject = new JsonParser().parse(jsonString).getAsJsonObject().getAsJsonObject("spellcheck");
        if (spellCheckObject != null) {
            return spellCheckObject.get("suggestions");
        }
        return null;
    }

    List<SemanticQueryTerm> extractSuggestions(String jsonString) {
        List<SemanticQueryTerm> suggestionStrings = new ArrayList<>();

        JsonElement suggestionsElement = extractSuggestionsElement(jsonString);

        if (suggestionsElement != null && suggestionsElement.isJsonArray()) {

            JsonArray suggestionsArray = suggestionsElement.getAsJsonArray();

            if (suggestionsArray.size() > 0) {
                JsonArray suggestionElements = suggestionsArray.get(0).getAsJsonArray().get(1).getAsJsonObject().get("suggestion").getAsJsonArray();

                for (JsonElement suggestionElement : suggestionElements) {
                    String term = suggestionElement.getAsString();
                    SemanticQueryTerm termSource = SemanticQueryTerm.create(term);

                    suggestionStrings.add(termSource);
                }
            }
        }
        return suggestionStrings;
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
