package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class GeneSetPageController extends BioentityPageController {

    @Value("#{configuration['index.property_names.genesetpage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genesets/{identifier:.*}")
    public String showGeneSetPage(@PathVariable String identifier, Model model) {
        bioentityPropertyServiceInitializer.initForGeneSetPage(bioEntityPropertyService, identifier);

        String species;
        if (!model.containsAttribute("species")) {
            species = GeneSetUtil.matchesReactomeID(identifier) ? bioEntityPropertyService.getSpecies() : "";
            model.addAttribute("species", species);
        } else {
            species = (String) model.asMap().get("species");
        }

        model.addAttribute("queryType", "geneSet");

        ImmutableSet<String> experimentTypes = analyticsSearchService.fetchExperimentTypes(GeneQuery.create(identifier), species);

        return super.showBioentityPage(identifier, species, model, experimentTypes);
    }

    @Override
    protected Map<String, Object> pageDescriptionAttributes(String identifier){
        String species = GeneSetUtil.matchesReactomeID(identifier) ? bioEntityPropertyService.getSpecies() : "";
        String s = "Expression summary for " + bioEntityPropertyService.getBioEntityDescription() +
                (StringUtils.isNotBlank(species) ?
                        " - " + StringUtils.capitalize(species) :
                        "");
        return ImmutableMap.<String, Object>of(
                "mainTitle", s,
                "pageDescription", s,
                "pageKeywords", "geneset,"+identifier
        );
    }

    @RequestMapping(value = {"/json/genesets/{identifier:.*}/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@PathVariable String identifier,
                                              @Valid GeneQuerySearchRequestParameters requestParameters) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForSearch(GeneQuery.create(identifier), requestParameters.getOrganism());
    }

    @RequestMapping(value = {"/json/genesets/{identifier:.*}/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@PathVariable String identifier,
                                               @Valid GeneQuerySearchRequestParameters requestParameters) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForSearch(GeneQuery.create(identifier), requestParameters.getOrganism());
    }
}