package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import java.util.Map;

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
        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, propertyNames);

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, SpeciesFactory.NULL, model, experimentTypes);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier) {
        String s = "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " +
                StringUtils.capitalize(bioEntityPropertyService.getSpecies());

        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "gene," + identifier + "," + bioEntityPropertyService.getSpecies()
        );
    }
}
