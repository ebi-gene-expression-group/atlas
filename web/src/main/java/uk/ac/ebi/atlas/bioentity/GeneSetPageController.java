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

import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.go.GoTermTrader;
import uk.ac.ebi.atlas.bioentity.go.PoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.utils.ReactomeClient;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.Set;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GeneSetPageController extends BioEntityPageController {

    private final SolrQueryService solrQueryService;

    private final BioEntityPropertyService bioEntityPropertyService;

    private final ReactomeClient reactomeClient;

    private final GoTermTrader goTermTrader;
    private final PoTermTrader poTermTrader;

    private final InterProTermTrader interProTermTrader;

    private String[] geneSetPagePropertyTypes;

    private SpeciesLookupService.Result speciesResult;

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] geneSetPagePropertyTypes) {
        this.geneSetPagePropertyTypes = geneSetPagePropertyTypes;
    }

    @Inject
    public GeneSetPageController(SolrQueryService solrQueryService, BioEntityPropertyService bioEntityPropertyService, ReactomeClient reactomeClient, GoTermTrader goTermTrader, PoTermTrader poTermTrader, InterProTermTrader interProTermTrader) {
        this.solrQueryService = solrQueryService;
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.reactomeClient = reactomeClient;
        this.goTermTrader = goTermTrader;
        this.poTermTrader = poTermTrader;
        this.interProTermTrader = interProTermTrader;
    }

    // identifier = Reactome, Plant Reactome, GO, or Interpro term
    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showBioentityPage(@PathVariable String  identifier, Model model) {
        checkIdentifierIsGeneSet(identifier);

        model.addAttribute("isGeneSet", true);

        speciesResult = speciesLookupService.fetchSpeciesForGeneSet(identifier);

        if (speciesResult.isEmpty()) {
            throw new ResourceNotFoundException("Bioentity " + identifier + " does not exist in Solr");
        }

        addBaselineResults(identifier, model);

        // load diff results in same way as BioentitiesSearchController
        String species = "";
        Optional<Set<String>> geneIdsResult = solrQueryService.expandGeneQueryIntoGeneIds(identifier, species, true);

        if (!geneIdsResult.isPresent() || geneIdsResult.get().isEmpty()) {
            throw new ResourceNotFoundException(identifier);
        }
        loadDifferentialResults(geneIdsResult.get(), model);

        return super.showBioentityPage(identifier, model, false);
    }

    private void addBaselineResults(String identifier, Model model) {
        if (speciesResult.isMultiSpecies()) {
            Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryExactMatchIntoGeneIdsAnySpecies(identifier);

            if (geneIds.isPresent()) {
                addBaselineCounts(geneIds.get(), model);
            }
        } else {
            String species = speciesResult.firstSpecies();

            Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryExactMatchIntoGeneIds(identifier, species);

            if (geneIds.isPresent()) {
                addBaselineResults(geneIds.get(), model);
            }

        }
    }

    @Override
    protected boolean isDisplayedInPropertyList(String propertyType) {
        return true;
    }

    @Override
    protected void initBioentityPropertyService(String identifier, Model model) {
        String species = speciesResult.isSingleSpecies() ? speciesResult.firstSpecies() : "";

        SortedSetMultimap<String, String> propertyValuesByType = TreeMultimap.create();

        ImmutableSetMultimap.Builder<Integer, GoPoTerm> builder = new ImmutableSetMultimap.Builder<>();
        ImmutableSetMultimap<Integer, GoPoTerm> goTermsByDepth = builder.build();
        ImmutableSetMultimap<Integer, GoPoTerm> poTermsByDepth = builder.build();

        identifier = identifier.toUpperCase();

        if (GeneSetUtil.isReactome(identifier)) {
            propertyValuesByType.put("reactome", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeClient.fetchPathwayNameFailSafe(identifier));
        } else if (GeneSetUtil.isGeneOntology(identifier)) {
            String termName = goTermTrader.getTermName(identifier);
            propertyValuesByType.put("go", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            goTermsByDepth = mapGoTermsByDepth(propertyValuesByType.get("go"));
        } else if (GeneSetUtil.isPlantOntology(identifier)) {
            String termName = poTermTrader.getTermName(identifier);
            propertyValuesByType.put("po", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            poTermsByDepth = mapPoTermsByDepth(propertyValuesByType.get("po"));
        } else if (GeneSetUtil.isInterPro(identifier)) {
            String term = interProTermTrader.getTerm(identifier);
            propertyValuesByType.put("interpro", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, term);
        } else if (GeneSetUtil.isPlantReactome(identifier)) {
            propertyValuesByType.put("plant_reactome", identifier);
        }

        SortedSet<String> names = Sets.newTreeSet();
        names.add(identifier);

        bioEntityPropertyService.init(species, propertyValuesByType, goTermsByDepth, poTermsByDepth, names, identifier);

        model.addAttribute("mainTitle", "Expression summary for <" + bioEntityPropertyService.getBioEntityDescription() + ">" +
                (StringUtils.isNotBlank(species) ? "-<" + species + "> " : ""));
    }

    @Override
    String fetchSpecies(String identifier){
        SpeciesLookupService.Result result = speciesLookupService.fetchSpeciesForGeneSet(identifier);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Species can't be determined for " + identifier);
        }
        return result.firstSpecies();
    }

    @Override
    String[] getPagePropertyTypes() {
        return geneSetPagePropertyTypes;
    }

    @Override
    String getBioentityPropertyName() {
        return null;
    }

    private void checkIdentifierIsGeneSet(String identifier) {
        if (!GeneSetUtil.isGeneSet(identifier)) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }


}