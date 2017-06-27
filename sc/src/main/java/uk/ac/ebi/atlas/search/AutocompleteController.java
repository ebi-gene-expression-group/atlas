package uk.ac.ebi.atlas.search;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.query.SolrBioentitiesSuggesterService;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

@Controller
@Scope("request")
public class AutocompleteController extends JsonExceptionHandlingController {

    private final SolrBioentitiesSuggesterService suggesterService;
    private final SpeciesFactory speciesFactory;

    @Inject
    public AutocompleteController(SolrBioentitiesSuggesterService suggesterService, SpeciesFactory speciesFactory) {
        this.suggesterService = suggesterService;
        this.speciesFactory = speciesFactory;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {

        return new Gson().toJson(suggesterService.fetchBioentitySuggestions(query, speciesFactory.create(species), suggestCount));
    }

}
