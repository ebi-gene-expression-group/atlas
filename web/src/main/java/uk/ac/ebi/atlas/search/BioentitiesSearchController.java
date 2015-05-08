/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.search;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrException;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
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

    private static final Logger LOGGER = Logger.getLogger(BioentitiesSearchController.class);

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
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, BindingResult result) {

        checkArgument(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify a gene query or condition!");

        String geneQuery = requestParameters.getGeneQuery().asString().trim();

        String selectedSpecies = "";
        if(requestParameters.hasOrganism()) {
            selectedSpecies = requestParameters.getOrganism().trim();
        }

        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {
            //If Query just for a single bioentityID
            Optional<String> geneIdRedirectString = getGeneIdRedirectString(geneQuery, selectedSpecies, requestParameters.isExactMatch());
            if (geneIdRedirectString.isPresent()) {
                return geneIdRedirectString.get();
            }
        }

        model.addAttribute("geneQuery", requestParameters.getGeneQuery());

        model.addAttribute("searchDescription", requestParameters.getDescription());

        model.addAttribute("mainTitle", "Expression summary for " + requestParameters.getDescription());

        String condition = efoExpander.addEfoAccessions(requestParameters.getConditionQuery()).asString();

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = baselineExperimentAssayGroupSearchService.query(geneQuery, condition, selectedSpecies.toLowerCase(), requestParameters.isExactMatch());

        boolean showWidget = hasAllSameSpecies(baselineExperimentAssayGroups) && hasAnyTissueExperiment(baselineExperimentAssayGroups) & !requestParameters.hasCondition();

        if (showWidget) {
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
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(geneQuery, condition, selectedSpecies, requestParameters.isExactMatch());

        model.addAttribute("bioentities", bioentityExpressions);

        model.addAttribute("preferences", new DifferentialRequestPreferences());

        model.addAttribute("requestParameters", requestParameters);

        model.addAttribute("exactMatch", requestParameters.isExactMatch());

        String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery, requestParameters.getConditionQuery());

        model.addAttribute("globalSearchTerm", globalSearchTerm);

        return "bioEntities";
    }

    private Optional<String> getGeneIdRedirectString(String geneQuery, String specie, boolean isExactMatch) {

        boolean singleTerm = !StringUtils.containsWhitespace(geneQuery);
        if (singleTerm && GeneSetUtil.isGeneSet(geneQuery.toUpperCase())) {
            return Optional.of("redirect:/genesets/" + geneQuery);
        }

        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(geneQuery);

        if (bioentityProperty != null) {
            String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();
            return Optional.of("redirect:/" + bioentityPageName + "/" + geneQuery);
        }

        String species = "";
        if (StringUtils.isNotBlank(specie)) {
           species = specie;
        }
        Optional<Set<String>> geneIdsOrSets = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

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
