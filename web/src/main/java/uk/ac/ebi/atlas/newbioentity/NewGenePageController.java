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
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

@Controller
@Scope("request")
public class NewGenePageController extends NewBioentityPageController {

    @Value("#{configuration['index.property_names.genepage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        if (!analyticsSearchDAO.isValidBioentityIdentifier(identifier)) {
            throw new ResourceNotFoundException("No gene matching " + identifier);
        }

        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, propertyNames);

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " + StringUtils.capitalize(bioEntityPropertyService.getSpecies()));

        ImmutableSet<String> experimentTypes = analyticsSearchDAO.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, model, experimentTypes);
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}/differentialFacets.json", method = RequestMethod.GET, produces = "application/json")
    public String fetchDifferentialJsonFacets(@PathVariable String identifier) {
        return "some_json";
    }

    @RequestMapping(value = "/new/genes/{identifier:.*}/differentialResults.json", method = RequestMethod.GET, produces = "application/json")
    public String fetchDifferentialJsonResults(@PathVariable String identifier) {
        return "some_other_json";
    }

}