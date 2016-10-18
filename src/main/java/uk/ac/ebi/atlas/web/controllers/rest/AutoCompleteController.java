package uk.ac.ebi.atlas.web.controllers.rest;


import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.SuggestionService;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;
import java.util.List;

@Controller
@Scope("request")
public class AutoCompleteController {

    private final SuggestionService suggestionService;

    @Inject
    public AutoCompleteController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions(@RequestParam(value = "query") String query,
                                      @RequestParam(value = "species", required = false, defaultValue="") String species) {
        if (StringUtils.isBlank(query)) {
            return StringUtils.EMPTY;
        }

        List<SemanticQueryTerm> suggestions = suggestionService.fetchTopSuggestions(query, species);

        return new Gson().toJson(suggestions);
    }

}
