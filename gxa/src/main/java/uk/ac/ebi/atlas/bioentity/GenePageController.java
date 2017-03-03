package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier,
                               @RequestParam(value = "foundation", required = false) String foundationKey,
                               Model model) {
        if(identifier.toUpperCase().startsWith("MGI")){
            Set<String> correspondingEnsemblIdentifiers = bioentityPropertyDao.fetchGeneIdsForPropertyValue
                    (BioentityPropertyName.MGI_ID, identifier);
            if(correspondingEnsemblIdentifiers.size() > 0){
                return MessageFormat.format("redirect:/genes/{0}", correspondingEnsemblIdentifiers.iterator().next());
            }
        }

        Species species = speciesFactory.create(speciesLookupService.fetchSpeciesForBioentityId(identifier).or(""));
        model.addAttribute("species", species.getName());

        Map<BioentityPropertyName, Set<String>> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier);
        Set<String> symbols =
                bioentityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);
        String geneName = symbols == null || symbols.isEmpty()
                ? ""
                : Joiner.on("/").join(symbols);


        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, species, geneName, model, experimentTypes,
                BioEntityCardProperties.bioentityPropertyNames, propertyValuesByType, foundationKey);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName) {
        String s = "Expression summary for "
                + (StringUtils.isEmpty(entityName) ? identifier : entityName)
                + " - " + species.getName();

        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "gene," + identifier + "," + species.getName()
        );
    }
}
