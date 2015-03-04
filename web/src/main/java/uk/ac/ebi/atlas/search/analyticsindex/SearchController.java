package uk.ac.ebi.atlas.search.analyticsindex;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

public abstract class SearchController {

    private final EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private final AnalyticsSearchDao analyticsSearchDao;

    public SearchController(EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, AnalyticsSearchDao analyticsSearchDao) {
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.analyticsSearchDao = analyticsSearchDao;
    }

    public void addSearchHeader(GeneQuerySearchRequestParameters requestParameters, Model model) {
        GeneQuery geneQuery = requestParameters.getGeneQuery();
        ImmutableSet<String> experimentTypes = analyticsSearchDao.fetchExperimentTypes(geneQuery);

        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

        model.addAttribute("searchDescription", requestParameters.getDescription());
        model.addAttribute("geneQuery", geneQuery);

        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery.asString(), requestParameters.getConditionQuery());
        model.addAttribute("globalSearchTerm", globalSearchTerm);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
