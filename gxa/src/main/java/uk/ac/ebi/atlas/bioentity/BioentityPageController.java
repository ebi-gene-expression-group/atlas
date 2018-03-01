package uk.ac.ebi.atlas.bioentity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityPropertyDao;
import uk.ac.ebi.atlas.bioentity.properties.BioEntityCardModelFactory;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.analytics.AnalyticsSearchService;
import uk.ac.ebi.atlas.solr.analytics.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.solr.analytics.differential.DifferentialAnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesInferrer;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BioentityPageController extends HtmlExceptionHandlingController {

    private BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private BioEntityCardModelFactory bioEntityCardModelFactory;

    protected SpeciesFactory speciesFactory;
    protected AnalyticsSearchService analyticsSearchService;
    protected DifferentialAnalyticsSearchService differentialAnalyticsSearchService;
    protected BioEntityPropertyDao bioentityPropertyDao;
    protected SpeciesInferrer speciesInferrer;

    @Inject
    public void setAnalyticsSearchService(AnalyticsSearchService analyticsSearchService) {
        this.analyticsSearchService = analyticsSearchService;
    }

    @Inject
    public void setBioEntityCardModelFactory(BioEntityCardModelFactory bioEntityCardModelFactory) {
        this.bioEntityCardModelFactory = bioEntityCardModelFactory;
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
    public void setSpeciesFactory(SpeciesFactory speciesFactory) {
        this.speciesFactory = speciesFactory;
    }

    @Inject
    public void setBioentityPropertyDao(BioEntityPropertyDao bioentityPropertyDao){
        this.bioentityPropertyDao = bioentityPropertyDao;
    }

    @Inject
    public void setSpeciesInferrer(SpeciesInferrer speciesInferrer) {
        this.speciesInferrer = speciesInferrer;
    }

    // identifier (gene) = an Ensembl identifier (gene, transcript, or protein) or a mirna identifier or an MGI term.
    // identifier (gene set) = a Reactome id, Plant Ontology or Gene Ontology accession or an InterPro term
    public String showBioentityPage(String identifier,
                                    String speciesReferenceName,
                                    String entityName,
                                    Model model,
                                    Set<String> experimentTypes,
                                    List<BioentityPropertyName> desiredOrderOfPropertyNames,
                                    Map<BioentityPropertyName, Set<String>> propertyValuesByType) {

        boolean hasDifferentialResults = ExperimentType.containsDifferential(experimentTypes);
        boolean hasBaselineResults = ExperimentType.containsBaseline(experimentTypes);

        if (!hasDifferentialResults && !hasBaselineResults) {
            model.addAttribute("searchDescription", identifier);
            return "no-results";
        }

        model.addAttribute("hasBaselineResults", hasBaselineResults);
        model.addAttribute("hasDifferentialResults", hasDifferentialResults);

        Species species = speciesFactory.create(speciesReferenceName);
        model.addAttribute("species", species.getName());

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
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
        model.addAllAttributes(bioEntityCardModelFactory.modelAttributes(identifier, species, desiredOrderOfPropertyNames,
                entityName, propertyValuesByType));


        return "search-results";
    }

    protected abstract Map<String, Object> pageDescriptionAttributes(String identifier, Species species, String entityName);

}
