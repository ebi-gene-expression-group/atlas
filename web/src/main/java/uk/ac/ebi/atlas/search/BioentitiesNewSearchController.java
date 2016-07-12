
package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableSet;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("prototype")
public class BioentitiesNewSearchController {

    private AnalyticsSearchService analyticsSearchService;

    @Inject
    public BioentitiesNewSearchController(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, RedirectAttributes redirectAttributes) {

        checkArgument(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify a gene query or a condition.");

        model.addAttribute("isSearch", true);
        model.addAttribute("searchDescription", requestParameters.getDescription());

        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {

            String species = requestParameters.hasOrganism() ? requestParameters.getOrganism().trim().toLowerCase() : "";
            GeneQuery geneQuery = requestParameters.getGeneQuery();

            // TODO We decide it’s a gene set because of how the query *looks*, and things like GO:FOOBAR will be incorrectly redirected to /genesets/GO:FOOBAR
            if (GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(geneQuery)) {
                setRedirectAttributes(redirectAttributes, requestParameters);
                return "redirect:/genesets/" + geneQuery.terms().iterator().next().value();
            }

            // Resolves to one gene ID
            ImmutableSet<String> geneIds = analyticsSearchService.searchBioentityIdentifiers(geneQuery, species);
            if (geneIds.size() == 0) {
                return "empty-search-page";
            }
            if (geneIds.size() == 1) {
                setRedirectAttributes(redirectAttributes, requestParameters);
                return "redirect:/genes/" + geneIds.iterator().next();
            }
            // Resolves to multiple IDs
            else {
                return "";
            }

        } else if (!requestParameters.hasGeneQuery() && requestParameters.hasCondition()) {

            // Only condition was specified
            // if (requestParameters.getConditionQuery().size() == 1) {
            //     return singleTermConditionQuery(requestParameters.getConditionQuery(), selectedSpecies, requestParameters.isExactMatch(), model, redirectAttributes);
            // } else {
            //     return multiTermConditionQuery(requestParameters.getConditionQuery(), selectedSpecies, requestParameters.isExactMatch(), model, redirectAttributes);
            // }
            return "";

        } else {

            return "";
            // Both gene query and condition were specified

        }
    }

    private void setRedirectAttributes(RedirectAttributes redirectAttributes, GeneQuerySearchRequestParameters requestParameters) {
        redirectAttributes.addFlashAttribute("isSearch", true);
        redirectAttributes.addFlashAttribute("searchDescription", requestParameters.getDescription());
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleSolrException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

}
