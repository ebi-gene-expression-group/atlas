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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.solr.query.SolrClient;
import uk.ac.ebi.atlas.utils.ReactomeBiomartClient;

import javax.inject.Inject;
import java.util.List;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GeneSetPageController extends BioEntityPageController {

    private SolrClient solrClient;

    private BioEntityPropertyService bioEntityPropertyService;

    private ReactomeBiomartClient reactomeBiomartClient;

    private String[] geneSetPagePropertyTypes;

    @Value("#{configuration['index.types.genesetpage']}")
    void setGenePagePropertyTypes(String[] geneSetPagePropertyTypes) {
        this.geneSetPagePropertyTypes = geneSetPagePropertyTypes;
    }

    @Inject
    public GeneSetPageController(SolrClient solrClient, BioEntityPropertyService bioEntityPropertyService, ReactomeBiomartClient reactomeBiomartClient) {
        this.solrClient = solrClient;
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.reactomeBiomartClient = reactomeBiomartClient;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showBioentityPage(@PathVariable String identifier, Model model) {
        model.addAttribute("isGeneSet", true);
        return super.showBioentityPage(identifier, model);
    }

    @Override
    protected boolean isDisplayedInPropertyList(String propertyType) {
        return true;
    }

    @Override
    protected void initBioentityPropertyService(String identifier) {
        String trimmedIdentifier = identifier.replaceAll("\"", "");
        String species = solrClient.getSpeciesForPropertyValue(trimmedIdentifier);

        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();
        propertyValuesByType.put("reactome", trimmedIdentifier.toUpperCase());
        propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeBiomartClient.fetchPathwayName(trimmedIdentifier));
        SortedSet<String> names = Sets.newTreeSet();
        names.add(trimmedIdentifier);
        bioEntityPropertyService.init(species, propertyValuesByType, names, trimmedIdentifier);
    }

    @Override
    List<String> getPagePropertyTypes() {
        return Lists.newArrayList(geneSetPagePropertyTypes);
    }

    @Override
    String getBioentityPropertyName() {
        return null;
    }


}