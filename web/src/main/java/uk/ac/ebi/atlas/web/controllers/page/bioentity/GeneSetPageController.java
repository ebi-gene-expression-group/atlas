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
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.utils.ReactomeBiomartClient;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GeneSetPageController extends BioEntityPageController {

    private SolrQueryService solrQueryService;

    private BioEntityPropertyService bioEntityPropertyService;

    private ReactomeBiomartClient reactomeBiomartClient;

    private String[] geneSetPagePropertyTypes;

    private ApplicationProperties applicationProperties;


    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] geneSetPagePropertyTypes) {
        this.geneSetPagePropertyTypes = geneSetPagePropertyTypes;
    }

    @Inject
    public GeneSetPageController(SolrQueryService solrQueryService, BioEntityPropertyService bioEntityPropertyService, ReactomeBiomartClient reactomeBiomartClient) {
        this.solrQueryService = solrQueryService;
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.reactomeBiomartClient = reactomeBiomartClient;
    }

    @Inject
    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showBioentityPage(@PathVariable String identifier, Model model) {
        //when we query for genesets the bioentity page must
        //not display Differential Expression panel so we just need to invoke parent controller (that handles baseline expressions)

        model.addAttribute("isGeneSet", true);

        checkIdentifierNotFound(identifier);

        return super.showBioentityPage(identifier, model);
    }

    @Override
    protected boolean isDisplayedInPropertyList(String propertyType) {
        return true;
    }

    @Override
    protected void initBioentityPropertyService(String identifier) {
        String trimmedIdentifier = identifier.replaceAll("\"", "");
        String species = solrQueryService.getSpeciesForPropertyValue(trimmedIdentifier);

        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();
        propertyValuesByType.put("reactome", trimmedIdentifier.toUpperCase());
        propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeBiomartClient.fetchPathwayNameFailSafe(trimmedIdentifier));
        SortedSet<String> names = Sets.newTreeSet();
        names.add(trimmedIdentifier);
        bioEntityPropertyService.init(species, propertyValuesByType, names, trimmedIdentifier);
    }

    @Override
    protected String hasBaselineExperimentForSpecies(String identifier){
        String trimmedIdentifier = identifier.replaceAll("\"", "");
        String species = solrQueryService.getSpeciesForPropertyValue(trimmedIdentifier);
        String experimentAccession = applicationProperties.getBaselineWidgetExperimentAccessionBySpecies(species);

        return experimentAccession;
    }

    @Override
    String[] getPagePropertyTypes() {
        return geneSetPagePropertyTypes;
    }

    @Override
    String getBioentityPropertyName() {
        return null;
    }

    private void checkIdentifierNotFound(String identifier) {
        String pattern = "IPR" + "(\\d)+";
        if (!identifier.startsWith("REACT_") && !identifier.startsWith("GO:") && !identifier.matches(pattern)) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

}