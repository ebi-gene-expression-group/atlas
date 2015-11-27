package uk.ac.ebi.atlas.widget;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.trader.cache.OrganismsCache;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;

@Controller
@Scope("request")
public final class ExpressionDataController {

    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private OrganismsCache organismsCache;

    @Inject
    public ExpressionDataController(BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                    OrganismsCache organismsCache) {
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.organismsCache = organismsCache;
    }

    @RequestMapping(value = "/json/expressionData", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView existGeneIdentifier(@RequestParam(value = "geneId", required = true) String geneId ) {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        boolean results = baselineAnalyticsSearchService.tissueExpressionAvailableFor(GeneQuery.create(geneId));
        mav.addObject(geneId, results);
        return mav;
    }

    @RequestMapping(value = "/json/expressionData/species", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView existSpeciesForGeneIdentifier() {
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
        mav.addObject("species", organismsCache.getOrganismsList());
        return mav;
    }

}
