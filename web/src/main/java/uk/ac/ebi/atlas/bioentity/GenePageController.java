package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    @Value("#{configuration['index.property_names.genepage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {
        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, propertyNames);

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " + StringUtils.capitalize(bioEntityPropertyService.getSpecies()));
        model.addAttribute("queryType", "gene");

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(identifier);

        return super.showBioentityPage(identifier, model, experimentTypes);
    }

    @RequestMapping(value ={"/json/genes/{identifier:.*}/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForIdentifier(identifier);
    }

    @RequestMapping(value ={"/json/genes/{identifier:.*}/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForIdentifier(identifier);
    }
}
