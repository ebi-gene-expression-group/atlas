package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties.BIOENTITY_PROPERTY_NAMES;

@Controller
public class GenePageController extends BioentityPageController {
    @RequestMapping(value = "/genes/{identifier:.*}", produces = "text/html;charset=UTF-8")
    public String showGenePage(@PathVariable String identifier, Model model) {
        if (identifier.toUpperCase().startsWith("MGI")) {
            Set<String> correspondingEnsemblIdentifiers =
                    bioentityPropertyDao.fetchGeneIdsForPropertyValue(BioentityPropertyName.MGI_ID, identifier);
            if (correspondingEnsemblIdentifiers.size() > 0) {
                return MessageFormat.format("redirect:/genes/{0}", correspondingEnsemblIdentifiers.iterator().next());
            }
        }

        Species speciesReferenceName = speciesInferrer.inferSpeciesForGeneQuery(SemanticQuery.create(identifier));

        String geneName =
                bioentityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL).stream()
                        .collect(Collectors.joining("/"));

        Map<BioentityPropertyName, Set<String>> propertyValuesByType =
                bioentityPropertyDao.fetchGenePageProperties(identifier);

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(
                identifier,
                speciesReferenceName.getName(),
                geneName,
                model,
                experimentTypes,
                BIOENTITY_PROPERTY_NAMES,
                propertyValuesByType);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName) {
        String s = "Expression summary for "
                + (StringUtils.isEmpty(entityName) ? identifier : entityName)
                + " - " + species.getName();

        return ImmutableMap.of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "gene, " + identifier + ", " + species.getName()
        );
    }
}
