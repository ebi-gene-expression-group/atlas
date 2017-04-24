package uk.ac.ebi.atlas.search;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public class JsonSearchController extends JsonExceptionHandlingController {

    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private final SpeciesFactory speciesFactory;

    @Inject
    public JsonSearchController(BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                SpeciesFactory speciesFactory) {
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.speciesFactory = speciesFactory;
    }

//    @RequestMapping(value = "/json/search/baseline_facets",
//                    method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String getJsonBaselineFacets(@RequestParam(value = "geneQuery", required = false, defaultValue = "")
//                                        SemanticQuery geneQuery,
//                                        @RequestParam(value = "conditionQuery", required = false, defaultValue = "")
//                                        SemanticQuery conditionQuery,
//                                        @RequestParam(value = "organism", required = false, defaultValue = "")
//                                        Species species) {
//        return gson.toJson(baselineAnalyticsSearchService.findFacetsForTreeSearch(geneQuery, conditionQuery, species));
//    }

    @RequestMapping(value = "/json/search/differential_facets",
                    method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDifferentialJsonFacets(@RequestParam(value = "geneQuery", required = false, defaultValue = "")
                                            SemanticQuery geneQuery,
                                            @RequestParam(value = "conditionQuery", required = false, defaultValue = "")
                                            SemanticQuery conditionQuery,
                                            @RequestParam(value = "species", required = false, defaultValue = "")
                                            String speciesParameter) {

        if (isBlank(speciesParameter)) {
            return gson.toJson(differentialAnalyticsSearchService.fetchFacets(geneQuery, conditionQuery));
        }

        return gson.toJson(
                differentialAnalyticsSearchService.fetchFacets(
                        geneQuery, conditionQuery, speciesFactory.create(speciesParameter)));
    }

    @RequestMapping(value = "/json/search/differential_results",
                    method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDifferentialJsonResults(@RequestParam(value = "geneQuery", required = false, defaultValue = "")
                                             SemanticQuery geneQuery,
                                             @RequestParam(value = "conditionQuery", required = false, defaultValue = "")
                                             SemanticQuery conditionQuery,
                                             @RequestParam(value = "species", required = false, defaultValue = "")
                                             String speciesParameter) {

        if (isBlank(speciesParameter)) {
            return gson.toJson(differentialAnalyticsSearchService.fetchResults(geneQuery, conditionQuery));
        }

        return gson.toJson(
                differentialAnalyticsSearchService.fetchResults(
                        geneQuery, conditionQuery, speciesFactory.create(speciesParameter)));
    }
}
