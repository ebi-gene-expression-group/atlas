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

import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import java.util.Set;

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

        // throw ResourceNotFoundException, so we don't get a Solr SyntaxException later on
        checkIdentifierDoesNotContainColon(identifier);

        Set<String> ensemblIDs = solrQueryService.fetchGeneIdentifiersFromSolr(identifier, "ensgene", true, "mirbase_id");
        if (ensemblIDs.size() == 1) {
            // if identifer is mirbase ID with one ensgene result, then use the ensgene identifier which has more info
            model.addAttribute("originalSearchTerm", identifier);
            identifier = ensemblIDs.iterator().next();
        } else if (ensemblIDs.size() > 0) {
            // if identifer is mirbase ID with more than one ensgene result,
            // then add all the ensembl IDs for the widget to display all ensembl gene IDs in the baseline results heatmap
            model.addAttribute("ensemblIdentifiersForMiRNA", "+" + Joiner.on("+").join(ensemblIDs));
        }

        loadDifferentialResults(identifier, model);

        return showBioentityPage(identifier, model, true);
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