package uk.ac.ebi.atlas.widget;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public final class ExpressionDataController extends JsonExceptionHandlingController {

    private final AnalyticsSearchService analyticsSearchService;

    @Inject
    public ExpressionDataController(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @RequestMapping(value = "/json/expressionData",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    public String hasTissueExpressionWithRequestParameter(@RequestParam(value = "geneId") String geneId) {
        return GSON.toJson(ImmutableMap.of(geneId, hasBaselineTissueExpression(geneId)));
    }

//    @RequestMapping(value = "/json/expressionData/{geneId}",
//                    method = RequestMethod.GET,
//                    produces = "application/json;charset=UTF-8")
//    public String hasTissueExpressionWithPathVariable(@PathVariable String geneId ) {
//        return gson.toJson(ImmutableMap.of(geneId, hasBaselineTissueExpression(geneId)));
//    }

    private boolean hasBaselineTissueExpression(String geneId) {
        return analyticsSearchService.tissueExpressionAvailableFor(SemanticQuery.create(geneId));
    }
}
