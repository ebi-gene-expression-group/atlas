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
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.search.baseline.BaselineExpressionSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExpressionSearchService;
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

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@Scope("prototype")
public class BioentitiesSearchController {

    private static final Logger LOGGER = Logger.getLogger(BioentitiesSearchController.class);

    private DiffAnalyticsSearchService diffAnalyticsSearchService;
    private BaselineExpressionSearchService baselineExpressionSearchService;

    private EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder;

    private SolrQueryService solrQueryService;

    @Inject
    public BioentitiesSearchController(DiffAnalyticsSearchService diffAnalyticsSearchService, BaselineExpressionSearchService baselineExpressionSearchService, EBIGlobalSearchQueryBuilder ebiGlobalSearchQueryBuilder, SolrQueryService solrQueryService) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
        this.baselineExpressionSearchService = baselineExpressionSearchService;
        this.ebiGlobalSearchQueryBuilder = ebiGlobalSearchQueryBuilder;
        this.solrQueryService = solrQueryService;
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("bioEntities");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, BindingResult result) {

        checkArgument(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify a gene query or condition!");

        String geneQuery = requestParameters.getGeneQuery().trim();

        if (requestParameters.hasGeneQuery() && !requestParameters.hasCondition()) {
            //If Query just for a single bioentityID
            Optional<String> geneIdRedirectString = getGeneIdRedirectString(geneQuery, requestParameters.isExactMatch());
            if (geneIdRedirectString.isPresent()) {
                return geneIdRedirectString.get();
            }
        }

        try {

            model.addAttribute("entityIdentifier", requestParameters.getDescription());

            Set<BaselineExpressionSearchResult> baselineExpressionSearchResults = baselineExpressionSearchService.query(geneQuery, requestParameters.getCondition(), requestParameters.isExactMatch());
            model.addAttribute("baselineCounts", baselineExpressionSearchResults);
            if (baselineExpressionSearchResults.size() == 1 & !requestParameters.hasCondition()) {
                model.addAttribute("singleBaselineSearchResult", true);
                model.addAttribute("species", baselineExpressionSearchResults.iterator().next().getSpecies());
            }

            // used to populate diff-heatmap-table
            DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);

            model.addAttribute("bioentities", bioentityExpressions);

            model.addAttribute("preferences", new DifferentialRequestPreferences());

            String globalSearchTerm = ebiGlobalSearchQueryBuilder.buildGlobalSearchTerm(geneQuery, requestParameters.getCondition());

            model.addAttribute("globalSearchTerm", globalSearchTerm);


        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + geneQuery + "'"));
        }

        return "bioEntities";
    }

    private Optional<String> getGeneIdRedirectString(String geneQuery, boolean isExactMatch) {

        if (!StringUtils.containsWhitespace(geneQuery) && geneQuery.toUpperCase().startsWith("REACT_")) {
            return Optional.of("redirect:/genesets/" + geneQuery);
        }

        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(geneQuery);

        if (bioentityProperty != null) {
            String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();
            return Optional.of("redirect:/" + bioentityPageName + "/" + geneId);
        }

        try {

            Optional<Set<String>> geneIdsOrSets = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, isExactMatch);

            if (geneIdsOrSets.isPresent() && geneIdsOrSets.get().size() == 1) {
                return Optional.of("redirect:/" + BioentityType.GENE.getBioentityPageName() + "/" + geneIdsOrSets.get().iterator().next());
            }

        } catch (HttpSolrServer.RemoteSolrException e) {
            LOGGER.error(e.getMessage(), e);
            throw new HttpSolrServer.RemoteSolrException(e.code(), geneQuery, e);
        }

        return Optional.absent();

    }

    @ExceptionHandler(value = {HttpSolrServer.RemoteSolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
