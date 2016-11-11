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
import uk.ac.ebi.atlas.model.analyticsindex.ExperimentDataPoint;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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

        return super.showBioentityPage(identifier, species,entityNames.iterator().next(), model, experimentTypes,
                ExperimentDataPoint.bioentityPropertyNames,propertyValuesByType);
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
