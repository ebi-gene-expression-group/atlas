package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.conditions.ConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedContrast;

import javax.inject.Inject;
import java.util.Collection;

@Controller
@Scope("request")
public class ConditionsQueryController {
    private ConditionsSearchService conditionsSearchService;

    @Inject
    public ConditionsQueryController(ConditionsSearchService conditionsSearchService) {
        this.conditionsSearchService = conditionsSearchService;
    }

    @RequestMapping(value = "/json/conditions", method = RequestMethod.GET, produces = "application/json")
        @ResponseStatus(HttpStatus.OK)
        @ResponseBody
    public String findContrasts(@RequestParam(value = "query") String query) {
        Collection<IndexedContrast> contrasts = conditionsSearchService.findContrasts(query);

        Gson gson = new Gson();
        return gson.toJson(contrasts);
    }
}
