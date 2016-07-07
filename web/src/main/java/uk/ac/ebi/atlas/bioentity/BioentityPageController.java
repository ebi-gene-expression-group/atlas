package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardProperties;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsIndexSearchDAO;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.web.OldGeneQuery;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class BioentityPageController {

    private static final String BIOENTITY_PROPERTY_NAME = "symbol";
    private static final String PROPERTY_TYPE_DESCRIPTION = "description";

    protected AnalyticsIndexSearchDAO analyticsIndexSearchDAO;
    protected BioentityPropertyServiceInitializer bioentityPropertyServiceInitializer;
    protected BioEntityPropertyService bioEntityPropertyService;
    protected BioEntityCardProperties bioEntityCardProperties;
    protected DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    protected BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    protected String[] propertyNames;

    @Inject
    public void setAnalyticsIndexSearchDAO(AnalyticsIndexSearchDAO analyticsIndexSearchDAO) {
        this.analyticsIndexSearchDAO = analyticsIndexSearchDAO;
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
    // If it is a MGI term, then will redirect to the gene query page
    public String showBioentityPage(String identifier, Model model, Set<String> experimentTypes) {

        if(identifier.startsWith("MGI:")){
            return "forward:/query?geneQuery=" + identifier;
        }

        if (ExperimentType.containsBaseline(experimentTypes)) {
            model.addAttribute("hasBaselineResults", true);
            model.addAttribute("jsonFacets", baselineAnalyticsSearchService.findFacetsForTreeSearch(OldGeneQuery.create(identifier)));
        } else {
            model.addAttribute("hasBaselineResults", false);
        }

        if (model.containsAttribute("searchDescription")) {
            model.addAttribute("isSearch", true);
        }

        if (model.containsAttribute("selectedSpecies")) {
            model.addAttribute("hasSelectedSpecies", true);
        }

        model.addAttribute("identifier", identifier);
        model.addAttribute("propertyNames", buildPropertyNamesByTypeMap());

        return "bioentities";
    }

    private Map<String, String> buildPropertyNamesByTypeMap() {
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

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }
}
