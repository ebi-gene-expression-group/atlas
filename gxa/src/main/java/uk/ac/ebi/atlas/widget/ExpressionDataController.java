package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;

import javax.inject.Inject;

@Controller
@Scope("request")
public final class ExpressionDataController extends JsonExceptionHandlingController {

    private final AnalyticsSearchService analyticsSearchService;
    private final SpeciesPropertiesTrader speciesTrader;
    private final Gson gson = new Gson();

    @Inject
    public ExpressionDataController(AnalyticsSearchService analyticsSearchService,
                                    SpeciesPropertiesTrader speciesTrader) {
        this.analyticsSearchService = analyticsSearchService;
        this.speciesTrader = speciesTrader;
    }

    @RequestMapping(value = "/json/expressionData", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String existGeneIdentifier(@RequestParam(value = "geneId") String geneId ) {
        boolean results = analyticsSearchService.tissueExpressionAvailableFor(SemanticQuery.create(geneId));
        return gson.toJson(ImmutableMap.of(geneId, results));
    }

    // Wojtek: this doesn't seem right.
    @RequestMapping(value = "/json/expressionData/species", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String existSpeciesForGeneIdentifier() {
        return gson.toJson(ImmutableMap.of("species", speciesTrader.getAll()));
    }

}
