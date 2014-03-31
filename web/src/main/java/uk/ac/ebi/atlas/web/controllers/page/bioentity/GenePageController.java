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

package uk.ac.ebi.atlas.web.controllers.page.bioentity;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsSearchService;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsList;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;

@Controller
@Scope("request")
public class GenePageController extends BioEntityPageController {

    public static final String BIOENTITY_PROPERTY_NAME = "symbol";

    private String[] bioentityPropertyNames;

    private DiffAnalyticsSearchService diffAnalyticsSearchService;

    @Value("#{configuration['index.property_names.genepage']}")
    void setBioentityPropertyNames(String[] bioentityPropertyNames) {
        this.bioentityPropertyNames = bioentityPropertyNames;
    }

    @Inject
    void setDifferentialBioentityExpressionBuilder(DiffAnalyticsSearchService diffAnalyticsSearchService) {
        this.diffAnalyticsSearchService = diffAnalyticsSearchService;
    }

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        // throw ResourceNotFoundException, so we don't get a Solr SyntaxException later on
        checkIdentifierDoesNotContainColon(identifier);

        DiffAnalyticsList diffAnalyticsList =
                diffAnalyticsSearchService.query(Sets.newHashSet(identifier));

        model.addAttribute("bioentities", diffAnalyticsList);

        // setting FDR as cutoff
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        model.addAttribute("preferences", requestPreferences);

        return showBioentityPage(identifier, model);
    }

    private void checkIdentifierDoesNotContainColon(String identifier) {
        if (identifier.contains(":")) {
            throw new ResourceNotFoundException("No genes with identifier "+ identifier);
        }
    }

    @Override
    protected boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(BioEntityPageController.PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }

    @Override
    String[] getPagePropertyTypes() {
        return bioentityPropertyNames;
    }

    @Override
    String getBioentityPropertyName() {
        return BIOENTITY_PROPERTY_NAME;
    }

}