package uk.ac.ebi.atlas.widget;

import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;

import javax.inject.Inject;

@Controller
@Scope("request")
public final class ExpressionDataController {

    private AnalyticsSearchService analyticsSearchService;
    private SpeciesPropertiesTrader speciesTrader;

    @Inject
    public ExpressionDataController(AnalyticsSearchService analyticsSearchService,
                                    SpeciesPropertiesTrader speciesTrader) {
        this.analyticsSearchService = analyticsSearchService;
        this.speciesTrader = speciesTrader;
    }

    @RequestMapping(value = "/json/expressionData", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView existGeneIdentifier(@RequestParam(value = "geneId") String geneId ) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        boolean results = analyticsSearchService.tissueExpressionAvailableFor(SemanticQuery.create(geneId));
        mav.addObject(geneId, results);
        return mav;
    }

    @RequestMapping(value = "/json/expressionData/species", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView existSpeciesForGeneIdentifier() {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject("species", speciesTrader.getAll());
        return mav;
    }

}
