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

package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

@Controller
@Scope("request")
public class GenePageController extends BioEntityPageController {

    public static final String BIOENTITY_PROPERTY_NAME = "symbol";

    private String[] bioentityPropertyNames;

    @Value("#{configuration['index.property_names.genepage']}")
    void setBioentityPropertyNames(String[] bioentityPropertyNames) {
        this.bioentityPropertyNames = bioentityPropertyNames;
    }

    // identifier = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // If it is a transcript/protein/mirna ID, the corresponding gene page will display
    // If it is an MGI term, then will redirect to the gene query page
    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        if (!isSingleGene(identifier)) {
            throw new ResourceNotFoundException("No gene matching " + identifier);
        }

        // throw ResourceNotFoundException, so we don't get a Solr SyntaxException later on
        checkIdentifierDoesNotContainColon(identifier);

        model.addAttribute("disableGeneLinks", true);

        model.addAttribute("isSingleGene", true);

        addBaselineResults(ImmutableSet.of(identifier), model);

        loadDifferentialResults(identifier, model);

        return showBioentityPage(identifier, model, true);
    }

    private boolean isSingleGene(String identifier) {
        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(identifier);

        return bioentityProperty != null;
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