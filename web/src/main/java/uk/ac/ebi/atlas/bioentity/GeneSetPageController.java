package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.Map;

import static uk.ac.ebi.atlas.bioentity.GeneSetUtil.matchesReactomeID;

@Controller
@Scope("request")
public class GeneSetPageController extends BioentityPageController {

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showGeneSetPage(@PathVariable String identifier,
                                  @RequestParam(value = "organism", required = false, defaultValue = "") String
                                          speciesString,
                                  Model model) {
        bioentityPropertyServiceInitializer.initForGeneSetPage(bioEntityPropertyService, identifier);

        Species species = speciesFactory.create(matchesReactomeID(identifier)? speciesLookupService.fetchSpeciesForGeneSet(identifier).or(""): speciesString);

        model.addAttribute("species", species.originalName);

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(SemanticQuery.create
                (identifier), species);

        return super.showBioentityPage(identifier, species,identifier, model, experimentTypes);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String description){
        String speciesString = matchesReactomeID(identifier) ? species.originalName : "";
        String s = "Expression summary for " + description +
                (StringUtils.isNotBlank(speciesString) ?
                        " - " + StringUtils.capitalize(speciesString) :
                        "");
        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "geneset,"+identifier
        );
    }

}