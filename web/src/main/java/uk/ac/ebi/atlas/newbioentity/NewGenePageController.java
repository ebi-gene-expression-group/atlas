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
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;

@Controller
@Scope("request")
public class NewGenePageController extends NewBioentityPageController {

    private SolrQueryService solrQueryService;
    private static final String GENES = "genes";

    @Inject
    public NewGenePageController(SolrQueryService solrQueryService) {
        super();
        this.solrQueryService = solrQueryService;
    }

    @Value("#{configuration['index.property_names.genepage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {
        if (!isSingleGene(identifier)) {
            throw new ResourceNotFoundException("No gene matching " + identifier);
        }

        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, propertyNames);

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " + StringUtils.capitalize(bioEntityPropertyService.getSpecies()));
        model.addAttribute("queryType", "gene");

        ImmutableSet<String> experimentTypes = analyticsIndexSearchDAO.fetchExperimentTypes(identifier);
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

        return super.showBioentityPage(identifier, model, experimentTypes);
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}/differentialFacets.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForIdentifier(GeneQuery.create(identifier));
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}/differentialResults.json", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForIdentifier(GeneQuery.create(identifier));
    }

    private boolean isSingleGene(String identifier) {
        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(identifier);
        String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();
        return bioentityPageName.equalsIgnoreCase(GENES);
    }
}
