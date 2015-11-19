package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;

import javax.inject.Inject;
import java.util.Collection;

@Controller
@Scope("request")
public class ConditionsQueryController {
    private DifferentialConditionsSearchService differentialConditionsSearchService;

    @Inject
    public ConditionsQueryController(DifferentialConditionsSearchService differentialConditionsSearchService) {
        this.differentialConditionsSearchService = differentialConditionsSearchService;
    }

    @RequestMapping(value = "/json/conditions", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
        @ResponseStatus(HttpStatus.OK)
        @ResponseBody
    public String findContrasts(@RequestParam(value = "query") String query) {
        Collection<IndexedAssayGroup> contrasts = differentialConditionsSearchService.findContrasts(query);

        Gson gson = new Gson();
        return gson.toJson(contrasts);
    }
}
