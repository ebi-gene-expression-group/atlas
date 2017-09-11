package uk.ac.ebi.atlas.bioentity;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

@Controller
public class GenePageController extends BioentityPageController {

    @RequestMapping(value = "/genes/{identifier:.*}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String showGenePage(@PathVariable String identifier, Model model) {
        if(identifier.toUpperCase().startsWith("MGI")){
            Set<String> correspondingEnsemblIdentifiers = bioentityPropertyDao.fetchGeneIdsForPropertyValue
                    (BioentityPropertyName.MGI_ID, identifier);
            if(correspondingEnsemblIdentifiers.size() > 0){
                return MessageFormat.format("redirect:/genes/{0}", correspondingEnsemblIdentifiers.iterator().next());
            }
        }

        Map<BioentityPropertyName, Set<String>> propertyValuesByType = bioentityPropertyDao.fetchGenePageProperties(identifier);
        Set<String> symbols =
                bioentityPropertyDao.fetchPropertyValuesForGeneId(identifier, BioentityPropertyName.SYMBOL);
        String geneName = symbols == null || symbols.isEmpty()
                ? ""
                : Joiner.on("/").join(symbols);

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, "", geneName, model, experimentTypes,
                BioEntityCardProperties.bioentityPropertyNames, propertyValuesByType);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName) {
        String s = "Expression summary for "
                + (StringUtils.isEmpty(entityName) ? identifier : entityName)
                + " - " + species.getName();

        return ImmutableMap.of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "gene," + identifier + "," + species.getName()
        );
    }
}
