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

package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.commands.BaselineBioentityCountsService;
import uk.ac.ebi.atlas.commands.GeneQueryDifferentialService;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.dao.BaselineExperimentResult;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Controller
@Scope("prototype")
public class BioentitiesQueryController {

    private GeneQueryDifferentialService geneQueryDifferentialService;
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    private BaselineBioentityCountsService baselineBioentityCountsService;

    @Inject
    public BioentitiesQueryController(GeneQueryDifferentialService geneQueryDifferentialService, BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer, BaselineBioentityCountsService baselineBioentityCountsService) {
        this.geneQueryDifferentialService = geneQueryDifferentialService;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
        this.baselineBioentityCountsService = baselineBioentityCountsService;
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("bioEntities");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @RequestMapping(value = "/query")
    public String showGeneQueryResultPage(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, BindingResult result) {

        checkState(requestParameters.hasGeneQuery() || requestParameters.hasCondition(), "Please specify gene query or condition!");

        String geneQuery = requestParameters.getGeneQuery();
        try {

            model.addAttribute("entityIdentifier", requestParameters.getDescription());

            Set<BaselineExperimentResult> baselineCounts = baselineBioentityCountsService.query(requestParameters);
            model.addAttribute("baselineCounts", baselineCounts);

            // used to populate diff-heatmap-table
            DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

            model.addAttribute("bioentities", bioentityExpressions);

            model.addAttribute("preferences", new DifferentialRequestPreferences());

            List<String> geneQueryTerms = bioentityPropertyValueTokenizer.split(geneQuery);

            String globalSearchTerm = buildGlobalSearchTerm(geneQueryTerms, requestParameters.getCondition());

            model.addAttribute("globalSearchTerm", globalSearchTerm);


        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + geneQuery + "'"));
        }

        return "bioEntities";
    }

    public String buildGlobalSearchTerm(List<String> geneQueryTerms, String condition) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!geneQueryTerms.isEmpty()) {

            boolean moreThanOneQueryTermAndCondition = geneQueryTerms.size() > 1 && condition != null;

            if (moreThanOneQueryTermAndCondition) {
                stringBuilder.append("(");
            }

            stringBuilder.append(Joiner.on(" OR ").join(geneQueryTerms));

            if (moreThanOneQueryTermAndCondition) {
                stringBuilder.append(")");
            }

            if (condition != null) {
                stringBuilder.append(" AND ");
            }
        }

        if (condition != null) {
            stringBuilder.append(condition);
        }

        return stringBuilder.toString();
    }


}
