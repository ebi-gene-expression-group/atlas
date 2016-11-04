package uk.ac.ebi.atlas.bioentity;

import uk.ac.ebi.atlas.model.Species;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SortedSetMultimap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.SortedSet;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    @Value("#{configuration['index.property_names.genepage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier,
                               Model model) {

        Species species = speciesFactory.create(GeneSetUtil.matchesReactomeID(identifier)? speciesLookupService.fetchSpeciesForBioentityId(identifier).or(""): "");
        model.addAttribute("species", species.originalName);

        SortedSetMultimap<String, String> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties
                (identifier, propertyNames);
        SortedSet<String> entityNames = propertyValuesByType.get("symbol");
        if (entityNames.isEmpty()) {
            entityNames.add(identifier);
        }

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, species,entityNames.first(), model, experimentTypes,propertyValuesByType);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName) {
        String s = "Expression summary for " + entityName + " - " +
                StringUtils.capitalize(species.originalName);

        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "gene," + identifier + "," + species.originalName
        );
    }
}
