package uk.ac.ebi.atlas.controllers.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.suggester.SuggesterDao;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public class AutoCompleteController extends JsonExceptionHandlingController {
    private final SuggesterDao suggesterDao;

    public AutoCompleteController(SuggesterDao suggesterDao) {
        this.suggesterDao = suggesterDao;
    }

    @RequestMapping(value = "/json/suggestions",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {
        return GSON.toJson(suggesterDao.fetchPropertySuggestions(query, suggestCount, species.split(",")));
    }
}
