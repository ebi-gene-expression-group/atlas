package uk.ac.ebi.atlas.search;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.search.EFO.ConditionSearchEFOExpander;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroup;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroupSearchService;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.thirdpartyintegration.EBIGlobalSearchQueryBuilder;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkArgument;
import static uk.ac.ebi.atlas.search.baseline.BaselineExperimentAssayGroups.*;

@Controller
@Scope("prototype")
public class BioentitiesSearchController {

    private static final int DEFAULT_BASELINE_SPLIT = 10;

    private DiffAnalyticsSearchService diffAnalyticsSearchService;
    private BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService;

    private EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private SolrQueryService solrQueryService;
    private ConditionSearchEFOExpander efoExpander;

    @Inject
    public BioentitiesSearchController(DiffAnalyticsSearchService diffAnalyticsSearchService, BaselineExperimentAssayGroupSearchService baselineExperimentAssayGroupSearchService, EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, SolrQueryService solrQueryService, ConditionSearchEFOExpander efoExpander) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
        this.baselineExperimentAssayGroupSearchService = baselineExperimentAssayGroupSearchService;
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.solrQueryService = solrQueryService;
        this.efoExpander = efoExpander;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, RedirectAttributes redirectAttributes) {

        checkArgument(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify a gene query or condition!");

        String geneQueryString = requestParameters.getGeneQuery().asString().trim();
        GeneQuery geneQuery = requestParameters.getGeneQuery();

        String selectedSpecies = "";
        if(requestParameters.hasOrganism()) {
            selectedSpecies = requestParameters.getOrganism().trim();
        }



        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {
            //If Query just for a single bioentityID
            Optional<String> geneIdRedirectString = getGeneIdRedirectString(geneQuery, selectedSpecies, requestParameters.isExactMatch());
            if (geneIdRedirectString.isPresent()) {
                redirectAttributes.addFlashAttribute("searchDescription", geneQueryString);
                redirectAttributes.addFlashAttribute("selectedSpecies", selectedSpecies.toLowerCase());
                return geneIdRedirectString.get();
            }
        }

        model.addAttribute("geneQuery", requestParameters.getGeneQuery());
        model.addAttribute("searchDescription", requestParameters.getDescription());
        model.addAttribute("mainTitle", "Expression summary for " + requestParameters.getDescription());

        ConditionQuery condition = efoExpander.addEfoAccessions(requestParameters.getConditionQuery());

        Optional<Set<String>> geneIdsFromGeneQuery = solrQueryService.expandGeneQueryIntoGeneIds
                (geneQueryString, selectedSpecies.toLowerCase(), requestParameters.isExactMatch());

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups =
                baselineExperimentAssayGroupSearchService.query(geneQueryString, condition.asString(), selectedSpecies.toLowerCase(), geneIdsFromGeneQuery);

        if (hasAllSameSpecies(baselineExperimentAssayGroups)
                && hasAnyTissueExperiment(baselineExperimentAssayGroups)
                && !requestParameters.hasCondition()) {
            model.addAttribute("widgetHasBaselineProfiles", true);
            model.addAttribute("species", baselineExperimentAssayGroups.iterator().next().getSpecies());

            SortedSet<BaselineExperimentAssayGroup> nonTissueExperimentAssayGroups = selectNonTissueExperiments(baselineExperimentAssayGroups);
            model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(nonTissueExperimentAssayGroups, DEFAULT_BASELINE_SPLIT));
            model.addAttribute("remainingBaselineCounts", nonTissueExperimentAssayGroups);
        } else {
            model.addAttribute("firstBaselineCounts", removeFirstAssayGroups(baselineExperimentAssayGroups, DEFAULT_BASELINE_SPLIT));
            model.addAttribute("remainingBaselineCounts", baselineExperimentAssayGroups);
        }

        // used to populate diff-heatmap-table
        DiffAnalyticsList bioentityExpressions =
                diffAnalyticsSearchService.fetchTop(condition.asString(),selectedSpecies, geneIdsFromGeneQuery);

        model.addAttribute("bioentities", bioentityExpressions);
        model.addAttribute("preferences", new DifferentialRequestPreferences());
        model.addAttribute("requestParameters", requestParameters);
        model.addAttribute("exactMatch", requestParameters.isExactMatch());

        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQueryString, requestParameters.getConditionQuery());

        model.addAttribute("globalSearchTerm", globalSearchTerm);

        return "bioEntities";
    }

    private Optional<String> getGeneIdRedirectString(GeneQuery geneQuery, String species, boolean isExactMatch) {

        boolean singleTerm = geneQuery.size() == 1;
        if (singleTerm && GeneSetUtil.matchesGeneSetAccession(geneQuery.terms().get(0).toUpperCase())) {
            return Optional.of("redirect:/genesets/" + geneQuery.terms().get(0));
        }

        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(geneQuery.asString().trim());

        if (bioentityProperty != null) {
            String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();
            return Optional.of("redirect:/" + bioentityPageName + "/" + bioentityProperty.getBioentityIdentifier());
        }

        if (StringUtils.isBlank(species)) {
           species = "";
        }
        Optional<Set<String>> geneIdsOrSets = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery.asString().trim(), species, isExactMatch);

        if (geneIdsOrSets.isPresent() && geneIdsOrSets.get().size() == 1) {
            return Optional.of("redirect:/" + BioentityType.GENE.getBioentityPageName() + "/" + geneIdsOrSets.get().iterator().next());
        }

        return Optional.absent();

    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("bioEntities");
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
