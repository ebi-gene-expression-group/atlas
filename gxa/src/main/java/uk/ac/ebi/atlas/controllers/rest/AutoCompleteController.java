package uk.ac.ebi.atlas.controllers.rest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.suggester.SuggesterDao;
import uk.ac.ebi.atlas.search.suggester.SuggesterService;

import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public class AutoCompleteController extends JsonExceptionHandlingController {
    private final SuggesterService suggesterService;

    public AutoCompleteController(SuggesterService suggesterService) {
        this.suggesterService = suggesterService;
    }

    @RequestMapping(value = "/json/suggestions",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species) {
        return GSON.toJson(
                suggesterService.fetchPropertiesWithHighlighting(query, species.split(",")).collect(toList()));
    }
}
