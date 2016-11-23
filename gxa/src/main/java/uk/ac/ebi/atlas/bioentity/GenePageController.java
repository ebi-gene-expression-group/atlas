package uk.ac.ebi.atlas.bioentity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.model.Species;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.Map;
import java.util.Set;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier,
                               Model model) {

        Species species = speciesFactory.create(GeneSetUtil.matchesReactomeID(identifier)? speciesLookupService.fetchSpeciesForBioentityId(identifier).or(""): "");
        model.addAttribute("species", species.originalName);

        Map<BioentityPropertyName, Set<String>> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier);
        Set<String> entityNames = propertyValuesByType.get(BioentityPropertyName.SYMBOL);
        if (entityNames == null || entityNames.isEmpty()) {
            entityNames = ImmutableSet.of(identifier);
        }

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, species, entityNames.iterator().next(), model, experimentTypes,
                BioEntityCardProperties.bioentityPropertyNames, propertyValuesByType);
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
