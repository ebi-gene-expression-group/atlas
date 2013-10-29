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
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.DifferentialBioentityExpressionsBuilder;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.SearchRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@Scope("prototype")
public class BioentitiesQueryController {

    private SolrQueryService solrQueryService;
    private DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder;
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer;

    @Inject
    public BioentitiesQueryController(SolrQueryService solrQueryService, DifferentialBioentityExpressionsBuilder differentialBioentityExpressionsBuilder, BioentityPropertyValueTokenizer bioentityPropertyValueTokenizer) {
        this.solrQueryService = solrQueryService;
        this.differentialBioentityExpressionsBuilder = differentialBioentityExpressionsBuilder;
        this.bioentityPropertyValueTokenizer = bioentityPropertyValueTokenizer;
    }

    @RequestMapping(value = "/query")
    public String showConditionsResultPage(@RequestParam (value="condition", required = true) String condition, Model model) {
        model.addAttribute("entityIdentifier", condition);

        DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.withCondition(condition).build();

        model.addAttribute("bioentities", bioentityExpressions);

        model.addAttribute("preferences", new DifferentialRequestPreferences());

        return "bioEntities";
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public String handleException(Exception e) {
        return "bioEntities";
    }

    @RequestMapping(value = "/query", params = {"geneQuery"})
    public String showGeneQueryResultPage(@Valid SearchRequest params, Model model, BindingResult result) {
        String geneQuery = params.getGeneQuery();

        model.addAttribute("entityIdentifier", geneQuery);

        List<String> identifiers = bioentityPropertyValueTokenizer.split(geneQuery);

        //ToDo: we probably don't need to do this (next 3 lines of code) anymore
        Set<String> ensemblIDs = solrQueryService.findGenesFromMirBaseIDs(identifiers);

        if (ensemblIDs.size() > 0) {
            model.addAttribute("ensemblIdentifiersForMiRNA", "+" + Joiner.on("+").join(ensemblIDs));
        }

        try {
            String species = "";  // search across any species

            //resolve any gene keywords to identifiers
            GeneQueryResponse geneQueryResponse = solrQueryService.findGeneIdsOrSets(geneQuery,
                                params.isExactMatch(),
                                species,
                                params.isGeneSetMatch());

            Collection<String> resolvedGeneIds = geneQueryResponse.getAllGeneIds();

            if (resolvedGeneIds.size() == 0) throw new GenesNotFoundException();

            // used to populate diff-heatmap-table
            DifferentialBioentityExpressions bioentityExpressions = differentialBioentityExpressionsBuilder.withGeneIdentifiers(Sets.newHashSet(resolvedGeneIds)).build();

            model.addAttribute("bioentities", bioentityExpressions);

            model.addAttribute("preferences", new DifferentialRequestPreferences());

            model.addAttribute("globalSearchTerm", Joiner.on(" OR ").join(identifiers) );


        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + geneQuery + "'"));
        }

        return "bioEntities";
    }

}
