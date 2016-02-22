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

package uk.ac.ebi.atlas.newbioentity;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

@Controller
@Scope("request")
public class NewGeneSetPageController extends NewBioentityPageController {

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showGeneSetPage(@PathVariable String identifier, Model model) {

        if (!GeneSetUtil.matchesGeneSetAccession(identifier)) {
            throw new ResourceNotFoundException("No gene set matching " + identifier);
        }

        bioentityPropertyServiceInitializer.initForGeneSetPage(bioEntityPropertyService, identifier);

        String species = GeneSetUtil.matchesReactomeID(identifier) ? bioEntityPropertyService.getSpecies() : "";

        model.addAttribute(
            "mainTitle",

            "Expression summary for " + bioEntityPropertyService.getBioEntityDescription() +
                (StringUtils.isNotBlank(species) ?
                    " - " + StringUtils.capitalize(species) :
                    "")
        );

        model.addAttribute("species", species);
        model.addAttribute("queryType", "geneSet");

        ImmutableSet<String> experimentTypes = analyticsIndexSearchDAO.fetchExperimentTypes(GeneQuery.create(identifier));
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

        return super.showBioentityPage(identifier, model, experimentTypes);
    }

    @RequestMapping(value = {"/genesets/{identifier:.*}/differentialFacets.json", "/rest/genesets/{identifier:.*}/differentialFacets.json"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(GeneQuery.create(identifier));
    }

    @RequestMapping(value = {"/genesets/{identifier:.*}/differentialResults.json", "/rest/genesets/{identifier:.*}/differentialResults.json"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(GeneQuery.create(identifier));
    }
}