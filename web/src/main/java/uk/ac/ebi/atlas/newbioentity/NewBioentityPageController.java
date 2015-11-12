package uk.ac.ebi.atlas.newbioentity;

import com.google.common.collect.Maps;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchDAO;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 22/10/15.
 */
public abstract class NewBioentityPageController {

    private static final String BIOENTITY_PROPERTY_NAME = "symbol";
    private static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected AnalyticsSearchDAO analyticsSearchDAO;
    protected BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer;
    protected BioEntityPropertyService bioEntityPropertyService;
    protected BioEntityCardProperties bioEntityCardProperties;
    protected DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    protected BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    protected String[] propertyNames;

    @Inject
    public void setAnalyticsSearchDAO(AnalyticsSearchDAO analyticsSearchDAO) {
        this.analyticsSearchDAO = analyticsSearchDAO;
    }

    @Inject
    public void setBioentityPropertyServiceInitializer(BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer) {
        this.bioentityPropertyServiceInitializer = bioentityPropertyServiceInitializer;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setBioEntityCardProperties(BioEntityCardProperties bioEntityCardProperties) {
        this.bioEntityCardProperties = bioEntityCardProperties;
    }

    @Inject
    public void setDifferentialAnalyticsSearchService(DifferentialAnalyticsSearchService differentialAnalyticsSearchService) {
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
    }

    @Inject
    public void setBaselineAnalyticsSearchService(BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    // identifier (gene) = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // identifier (gene set) = a Reactome id, Plant Ontology or Gene Ontology accession or an InterPro term
    // If it is an MGI term, then will redirect to the gene query page
    public String showBioentityPage(String identifier, Model model, Set<String> experimentTypes) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        if (ExperimentType.containsDifferential(experimentTypes)) {
            model.addAttribute("hasDifferentialResults", true);
            model.addAttribute("jsonDifferentialGeneQueryFacets", differentialAnalyticsSearchService.fetchDifferentialSearchFacetsAsJson(GeneQuery.create(identifier)));
            model.addAttribute("jsonDifferentialGeneQueryResults", differentialAnalyticsSearchService.fetchDifferentialSearchResultsAsJson(GeneQuery.create(identifier)));
        } else {
            model.addAttribute("hasDifferentialResults", false);
        }
        if (ExperimentType.containsBaseline(experimentTypes)) {
            model.addAttribute("hasBaselineResults", true);
            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(GeneQuery.create(identifier)));
        } else {
            model.addAttribute("hasBaselineResults", false);
        }

        model.addAttribute("hasBaselineResults", ExperimentType.containsBaseline(experimentTypes));

        if (model.containsAttribute("searchDescription")) {
            model.addAttribute("isSearch", true);
        }

        if (model.containsAttribute("selectedSpecies")) {
            model.addAttribute("hasSelectedSpecies", true);
        }

        model.addAttribute("identifier", identifier);
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "new-bioentities";
    }

    protected Map<String, String> buildPropertyNamesByTypeMap() {
        LinkedHashMap<String, String> result = Maps.newLinkedHashMap();
        for (String propertyName : propertyNames) {
            if (isDisplayedInPropertyList(propertyName)) {
                result.put(propertyName, bioEntityCardProperties.getPropertyName(propertyName));
            }
        }
        return result;
    }

    private boolean isDisplayedInPropertyList(String propertyType) {
        return !propertyType.equals(PROPERTY_TYPE_DESCRIPTION) && !propertyType.equals(BIOENTITY_PROPERTY_NAME);
    }
}
