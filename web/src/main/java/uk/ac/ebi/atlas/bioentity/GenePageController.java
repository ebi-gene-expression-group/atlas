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
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDAO;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Scope("request")
public class NewGenePageController {

    private static final String BIOENTITY_PROPERTY_NAME = "symbol";
    private static final String PROPERTY_TYPE_DESCRIPTION = "description";

    private AnalyticsSearchDAO analyticsSearchDAO;
    private BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer;
    private BioEntityPropertyService bioEntityPropertyService;
    private BioEntityCardProperties bioEntityCardProperties;
    private DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    @Value("#{configuration['index.property_names.genepage']}")
    String[] genePropertyNames;

    @Inject
    public NewGenePageController(AnalyticsSearchDAO analyticsSearchDAO,
                                 BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer,
                                 BioEntityPropertyService bioEntityPropertyService,
                                 BioEntityCardProperties bioEntityCardProperties,
                                 DifferentialAnalyticsSearchService differentialAnalyticsSearchService,
                                 BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.analyticsSearchDAO = analyticsSearchDAO;
        this.bioentityPropertyServiceInitializer = bioentityPropertyServiceInitializer;
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.bioEntityCardProperties = bioEntityCardProperties;
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    // identifier = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // If it is a transcript/protein/mirna ID, the corresponding gene page will display
    // If it is an MGI term, then will redirect to the gene query page
    @RequestMapping(value = "/new/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        if (!analyticsSearchDAO.isValidBioentityIdentifier(identifier)) {
            throw new ResourceNotFoundException("No gene matching " + identifier);
        }

        ImmutableSet<String> experimentTypes = analyticsSearchDAO.fetchExperimentTypes(identifier);
        if (ExperimentType.containsDifferential(experimentTypes)) {
            model.addAttribute("hasDifferentialResults", true);
            model.addAttribute("jsonDifferentialGeneQueryFacets", differentialAnalyticsSearchService.fetchDifferentialGeneQueryFacetsAsJson(GeneQuery.create(identifier)));
            model.addAttribute("jsonDifferentialGeneQueryResults", differentialAnalyticsSearchService.fetchDifferentialGeneQueryResultsAsJson(GeneQuery.create(identifier)));
        } else {
            model.addAttribute("hasDifferentialResults", false);
        }
        if (ExperimentType.containsBaseline(experimentTypes)) {
            model.addAttribute("hasBaselineResults", true);
            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(GeneQuery.create(identifier)));
        } else {
            model.addAttribute("hasBaselineResults", false);
        }

        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));


        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, genePropertyNames);

        model.addAttribute("identifier", identifier);
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());
        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " + StringUtils.capitalize(bioEntityPropertyService.getSpecies()));

        return "new-bioentities";
    }

    private Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyName : genePropertyNames) {
            if (isDisplayedInPropertyList(propertyName)) {
                result.put(propertyName, bioEntityCardProperties.getPropertyName(propertyName));
            }
        }
        return result;
    }

    protected boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }


}