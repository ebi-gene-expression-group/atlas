package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfileSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.trader.cache.OrganismsCache;

import javax.inject.Inject;

@Controller
@Scope("request")
public final class WidgetEndPointController {

    private BaselineExperimentProfileSearchService baselineExperimentProfileSearchService;
    private OrganismsCache organismsCache;

    @Inject
    public WidgetEndPointController(BaselineExperimentProfileSearchService baselineExperimentProfileSearchService,
                                    OrganismsCache organismsCache) {
        this.baselineExperimentProfileSearchService = baselineExperimentProfileSearchService;
        this.organismsCache = organismsCache;
    }

    @RequestMapping(value = "/json/expressionData", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView existGeneIdentifier(@RequestParam(value = "geneId", required = true) String geneId ) {

        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());

        BaselineExperimentSearchResult tissueResults = baselineExperimentProfileSearchService.query(ImmutableSet.of(geneId));

        mav.addObject(geneId, !tissueResults.isEmpty());

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
