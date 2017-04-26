package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.bioentity.geneset.GeneSetPropertyService;
import uk.ac.ebi.atlas.controllers.BioentityNotFoundException;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;

import javax.inject.Inject;
import java.util.Map;

import static uk.ac.ebi.atlas.bioentity.geneset.GeneSetUtil.matchesReactomeID;

@Controller
@Scope("request")
public class GeneSetPageController extends BioentityPageController {

    private GeneSetPropertyService geneSetPropertyService;

    @Inject
    public void setGeneSetPropertyService(GeneSetPropertyService geneSetPropertyService) {
        this.geneSetPropertyService = geneSetPropertyService;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}", produces = "text/html;charset=UTF-8")
    public String showGeneSetPage(@PathVariable String identifier,
                                  @RequestParam(value = "organism", required = false) String speciesString,
                                  Model model) {

        Species species = speciesFactory.create(matchesReactomeID(identifier) ? speciesLookupService.fetchSpeciesForGeneSet(identifier).or("") : speciesString);

        model.addAttribute("species", species.getName());

        ImmutableSet<String> experimentTypes =
                analyticsSearchService.fetchExperimentTypes(
                        SemanticQuery.create(identifier), species.getReferenceName());

        if (experimentTypes.isEmpty()) {
            throw new BioentityNotFoundException("Gene set <em>" + identifier + "</em> not found.");
        }

        return super.showBioentityPage(identifier, species, identifier, model, experimentTypes,
                GeneSetPropertyService.all, geneSetPropertyService.propertyValuesByType(identifier));
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName){
        String speciesString = matchesReactomeID(identifier) ? species.getName() : "";
        String s = "Expression summary for " + (StringUtils.isEmpty(entityName) ? identifier : entityName) +
                (StringUtils.isNotBlank(speciesString) ?
                        " - " + StringUtils.capitalize(speciesString) : "");
        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "geneset,"+identifier
        );
    }

}