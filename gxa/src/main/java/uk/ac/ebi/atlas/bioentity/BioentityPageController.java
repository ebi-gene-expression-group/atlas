package uk.ac.ebi.atlas.bioentity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyService;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.analyticsindex.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BioentityPageController {

    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;

    protected AnalyticsSearchService analyticsSearchService;
    protected BioEntityPropertyService bioEntityPropertyService;
    protected SpeciesLookupService speciesLookupService;
    protected DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    protected BioEntityPropertyDao bioentityPropertyDao;
    private Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();


    protected SpeciesFactory speciesFactory;

    protected String[] propertyNames;

    @Inject
    public void setAnalyticsSearchService(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @Inject
    public void setBioEntityPropertyService(BioEntityPropertyService bioEntityPropertyService) {
        this.bioEntityPropertyService = bioEntityPropertyService;
    }

    @Inject
    public void setDifferentialAnalyticsSearchService(DifferentialAnalyticsSearchService differentialAnalyticsSearchService) {
        this.differentialAnalyticsSearchService = differentialAnalyticsSearchService;
    }

    @Inject
    public void setBaselineAnalyticsSearchService(BaselineAnalyticsSearchService baselineAnalyticsSearchService) {
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
    }

    @Inject
    public void setSpeciesLookupService(SpeciesLookupService speciesLookupService){
        this.speciesLookupService = speciesLookupService;
    }
    @Inject
    public void setSpeciesFactory(SpeciesFactory speciesFactory) {
        this.speciesFactory = speciesFactory;
    }

    @Inject
    public void setBioentityPropertyDao(BioEntityPropertyDao bioentityPropertyDao){
        this.bioentityPropertyDao = bioentityPropertyDao;
    }

    // identifier (gene) = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // identifier (gene set) = a Reactome id, Plant Ontology or Gene Ontology accession or an InterPro term
    public String showBioentityPage(String identifier, Species species, String entityName, Model model, Set<String>
            experimentTypes, List<BioentityPropertyName> desiredOrderOfPropertyNames, Map<BioentityPropertyName, Set<String>>propertyValuesByType){

        boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
        boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

        if (!hasDifferentialResults && !hasBaselineResults) {
            return "empty-search-page";
        }

        model.addAttribute("hasBaselineResults", hasBaselineResults);
        model.addAttribute("hasDifferentialResults", hasDifferentialResults);

        if (hasBaselineResults) {
            model.addAttribute("jsonFacets", gson.toJson(baselineAnalyticsSearchService.findFacetsForTreeSearch
                    (SemanticQuery.create(identifier), species)));
        }

        model.addAllAttributes(pageDescriptionAttributes(identifier, species, entityName));
        /*
        TODO write a geneQuery that uniquely identifies the resource - I think it will be category:symbol or so.
        TODO For now the callback might match slightly too much which is a bug.
         */
        model.addAttribute("geneQuery", SemanticQuery.create(identifier).toUrlEncodedJson());
        model.addAllAttributes(bioEntityPropertyService.modelAttributes(identifier, species,desiredOrderOfPropertyNames,
                entityName, propertyValuesByType));

        return "bioentities";
    }

    protected abstract Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String
            entityName);


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("search-error");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }
}
