
package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.BioentityProperty;
import uk.ac.ebi.atlas.solr.BioentityType;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.OldGeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;

@Controller
@Scope("request")
public class GenePageController extends BioentityPageController {

    private SolrQueryService solrQueryService;
    private static final String GENES = "genes";

    @Inject
    public GenePageController(SolrQueryService solrQueryService) {
        super();
        this.solrQueryService = solrQueryService;
    }

    @Value("#{configuration['index.property_names.genepage']}")
    void setGenePagePropertyTypes(String[] propertyNames) {
        this.propertyNames = propertyNames;
    }

    @RequestMapping(value = "/genes/{identifier:.*}")
    public String showGenePage(@PathVariable String identifier, Model model) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        if (!isSingleGene(identifier)) {
            throw new ResourceNotFoundException("No gene matching " + identifier);
        }

        bioentityPropertyServiceInitializer.initForGenePage(bioEntityPropertyService, identifier, propertyNames);

        model.addAttribute("mainTitle", "Expression summary for " + bioEntityPropertyService.getEntityName() + " - " + StringUtils.capitalize(bioEntityPropertyService.getSpecies()));
        model.addAttribute("queryType", "gene");

        ImmutableSet<String> experimentTypes = analyticsIndexSearchDAO.fetchExperimentTypes(identifier);
        model.addAttribute("hasDifferentialResults", ExperimentType.containsDifferential(experimentTypes));

        return super.showBioentityPage(identifier, model, experimentTypes);
    }

    @RequestMapping(value ={"/json/genes/{identifier:.*}/differentialFacets"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonFacets(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialFacetsForIdentifier(OldGeneQuery.create(identifier));
    }

    @RequestMapping(value ={"/json/genes/{identifier:.*}/differentialResults"}, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fetchDifferentialJsonResults(@PathVariable String identifier) {
        return differentialAnalyticsSearchService.fetchDifferentialResultsForIdentifier(OldGeneQuery.create(identifier));
    }

    private boolean isSingleGene(String identifier) {
        BioentityProperty bioentityProperty = solrQueryService.findBioentityIdentifierProperty(identifier);

        if (bioentityProperty == null) {
            return false;
        } else {
            String bioentityPageName = BioentityType.get(bioentityProperty.getBioentityType()).getBioentityPageName();
            return bioentityPageName.equalsIgnoreCase(GENES);
        }
    }
}
