package uk.ac.ebi.atlas.controllers.rest;

import com.google.gson.Gson;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrBioentitiesSuggesterService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

@Controller
@Scope("request")
public class AutoCompleteController extends JsonExceptionHandlingController {

    private final SolrBioentitiesSuggesterService suggesterService;
    private final SpeciesFactory speciesFactory;

    @Inject
    public AutoCompleteController(SolrBioentitiesSuggesterService suggesterService, SpeciesFactory speciesFactory) {
        this.suggesterService = suggesterService;
        this.speciesFactory = speciesFactory;
    }

    @RequestMapping(value = "/json/suggestions",
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {

//        if (StringUtils.isBlank(query)) {
//            return StringUtils.EMPTY;
//        }

        return new Gson().toJson(suggesterService.fetchPropertySuggestions(query, speciesFactory.create(species), suggestCount));
    }

}
