package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSetMultimap;
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
import uk.ac.ebi.atlas.bioentity.go.GoPoTermTrader;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.OntologyTerm;
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

    private final GoPoTermTrader goPoTermTrader;

    private final InterProTrader interProTermTrader;

    private String[] geneSetPagePropertyTypes;

    private SpeciesLookupService.Result speciesResult;

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] geneSetPagePropertyTypes) {
        this.geneSetPagePropertyTypes = geneSetPagePropertyTypes;
    }

    @Inject
    public GeneSetPageController(SolrQueryService solrQueryService, BioEntityPropertyService bioEntityPropertyService, ReactomeClient reactomeClient, GoPoTermTrader goPoTermTrader, InterProTrader interProTermTrader) {
        this.solrQueryService = solrQueryService;
        this.bioEntityPropertyService = bioEntityPropertyService;
        this.reactomeClient = reactomeClient;
        this.goPoTermTrader = goPoTermTrader;
        this.interProTermTrader = interProTermTrader;
    }

    // identifier = Reactome, Plant Reactome, GO, or Interpro term
    @RequestMapping(value = "/old/genesets/{identifier:.*}")
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
        String species = speciesResult.isMultiSpecies()
                ? ""
                : speciesResult.firstSpecies();

        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(identifier, species, true);
        if (geneIds.isPresent()) {
            addBaselineCounts(geneIds.get(), model);
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

        ImmutableSetMultimap.Builder<Integer, OntologyTerm> builder = new ImmutableSetMultimap.Builder<>();
        ImmutableSetMultimap<Integer, OntologyTerm> goTermsByDepth = builder.build();
        ImmutableSetMultimap<Integer, OntologyTerm> poTermsByDepth = builder.build();

        identifier = identifier.toUpperCase();

        if (GeneSetUtil.matchesReactomeID(identifier)) {
            propertyValuesByType.put("reactome", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, reactomeClient.fetchPathwayNameFailSafe(identifier));
        } else if (GeneSetUtil.matchesGeneOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("go", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            goTermsByDepth = mapGoPoTermsByDepth(propertyValuesByType.get("go"));
        } else if (GeneSetUtil.matchesPlantOntologyAccession(identifier)) {
            String termName = goPoTermTrader.getTerm(identifier).name();
            propertyValuesByType.put("po", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, termName);
            poTermsByDepth = mapGoPoTermsByDepth(propertyValuesByType.get("po"));
        } else if (GeneSetUtil.matchesInterProAccession(identifier)) {
            String term = interProTermTrader.getTermName(identifier);
            propertyValuesByType.put("interpro", identifier);
            propertyValuesByType.put(BioEntityPropertyService.PROPERTY_TYPE_DESCRIPTION, term);
        } else if (GeneSetUtil.matchesPlantReactomeID(identifier)) {
            propertyValuesByType.put("plant_reactome", identifier);
        }

        SortedSet<String> names = Sets.newTreeSet();
        names.add(identifier);

        bioEntityPropertyService.init(species, propertyValuesByType, goTermsByDepth, poTermsByDepth, names, identifier);

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getBioEntityDescription() +
                (StringUtils.isNotBlank(species) ? " - " + StringUtils.capitalize(species) : ""));
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
        if (!GeneSetUtil.matchesGeneSetAccession(identifier)) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }


}