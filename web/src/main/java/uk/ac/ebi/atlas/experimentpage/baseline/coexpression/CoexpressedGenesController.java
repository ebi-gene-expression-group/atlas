package uk.ac.ebi.atlas.experimentpage.baseline.coexpression;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;


@Controller
@Scope("request")
@RequestMapping
public class CoexpressedGenesController {

    @Inject
    CoexpressedGenesService coexpressedGenesService;

    @RequestMapping(value ={"/json/experiments/coexpression"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@RequestParam(value = "experiment", required = true) String experiment,
                                              @RequestParam(value = "identifier", required = true) String identifier) {
        return coexpressedGenesService.coexpressedGenesFor(experiment, identifier);
    }
}
