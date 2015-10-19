package uk.ac.ebi.atlas.search.analyticsindex.bioentityInformation;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.bioentity.go.GoPoTerm;
import uk.ac.ebi.atlas.bioentity.interpro.InterProTermTrader;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDAO;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.utils.ReactomeClient;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.SortedSet;

@Controller
@Scope("request")
public class SearchGeneSetController extends SearchBioentityController {

    private final ReactomeClient reactomeClient;

    private final InterProTermTrader interProTermTrader;

    private SpeciesLookupService.Result speciesResult;

    private String[] geneSetPagePropertyTypes;

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] geneSetPagePropertyTypes) {
        this.geneSetPagePropertyTypes = geneSetPagePropertyTypes;
    }

    @Inject
    public SearchGeneSetController(AnalyticsSearchDAO analyticsSearchDAO, ReactomeClient reactomeClient,
                                   InterProTermTrader interProTermTrader) {
        super(analyticsSearchDAO);
        this.reactomeClient = reactomeClient;
        this.interProTermTrader = interProTermTrader;
    }

    // identifier = Reactome, Plant Reactome, GO, or Interpro term
    @RequestMapping(value = "/search/genesets/{identifier:.*}")
    public String showGeneSetInformation(@PathVariable String  identifier, Model model, RedirectAttributes redirectAttributes) throws IOException, SolrServerException {

        GeneQuery geneQuery = GeneQuery.create(identifier);
        model.addAttribute("geneQuery", geneQuery);
        model.addAttribute("searchDescription", geneQuery.description());

        speciesResult = speciesLookupService.fetchSpeciesForGeneSet(identifier);

        if (speciesResult.isEmpty()) {
            throw new ResourceNotFoundException("Bioentity " + identifier + " does not exist in Solr");
        }


        return showBioentityPage(geneQuery, model);
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

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getBioEntityDescription() +
                (StringUtils.isNotBlank(species) ? " - " + StringUtils.capitalize(species) : ""));
    }

    @Override
    public String[] getPagePropertyTypes() {
        return geneSetPagePropertyTypes;
    }

    @Override
    public String getBioentityPropertyName() {
        return null;
    }

}
