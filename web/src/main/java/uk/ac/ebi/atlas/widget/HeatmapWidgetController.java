package uk.ac.ebi.atlas.widget;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.baseline.AnatomogramFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageServiceFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.AssayGroupFactorViewModel;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineExperimentProfilesViewModelBuilder;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.solr.query.SpeciesLookupService;
import uk.ac.ebi.atlas.trader.SpeciesFactory;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public final class HeatmapWidgetController extends HeatmapWidgetErrorHandler {

    private AnatomogramFactory anatomogramFactory;
    private SpeciesLookupService speciesLookupService;

    private final BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final BaselineExperimentPageService baselineExperimentPageService;
    private final SpeciesFactory speciesFactory;
    private final FactorGroupingService factorGroupingService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Inject
    private HeatmapWidgetController(ApplicationProperties applicationProperties, SpeciesLookupService speciesLookupService,
                                    BaselineExperimentProfilesViewModelBuilder baselineExperimentProfilesViewModelBuilder,
                                    BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                    BaselineExperimentPageServiceFactory baselineExperimentPageServiceFactory,
                                    @Qualifier ("baselineProfileInputStreamFactory")BaselineProfileInputStreamFactory
                                                baselineProfileInputStreamFactory,
                                    SpeciesFactory speciesFactory,FactorGroupingService factorGroupingService) {
        this.anatomogramFactory = new AnatomogramFactory(applicationProperties);
        this.speciesLookupService = speciesLookupService;
        this.baselineExperimentProfilesViewModelBuilder = baselineExperimentProfilesViewModelBuilder;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(baselineProfileInputStreamFactory);
        this.speciesFactory = speciesFactory;
        this.factorGroupingService = factorGroupingService;
    }

    @RequestMapping(value = "/widgets/heatmap/referenceExperiment", params = "type=RNASEQ_MRNA_BASELINE", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String fetchReferenceExperimentProfilesJson(@ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                                       Model model, HttpServletRequest request, HttpServletResponse response) {
        BaselineExperiment experiment = (BaselineExperiment) request.getAttribute("experiment");

        baselineExperimentPageService.prepareRequestPreferencesAndHeaderData(experiment, preferences, model, request,true);
        try {
            baselineExperimentPageService.populateModelWithHeatmapData(experiment, preferences, model,request, true);
        } catch (GenesNotFoundException e) {
            throw new ResourceNotFoundException("No genes found matching query: '" + preferences.getGeneQuery() + "'");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "heatmap-data";
    }

    @RequestMapping(value = "/widgets/heatmap/baselineAnalytics", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String analyticsJson(@RequestParam(value = "geneQuery") SemanticQuery geneQuery,
                                @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
                                @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
                                @RequestParam(value = "propertyType", required = false) String propertyType,
                                @RequestParam(value = "source", required = false) String defaultQueryFactorType,
                                Model model, HttpServletRequest request) {

        //TODO: I don't think we should be doing this
        if(isBlank(speciesString)){
            Optional<String> maybeSpecies = speciesLookupService
                    .fetchFirstSpeciesByField(propertyType, geneQuery);
            if(maybeSpecies.isPresent()){
                speciesString = maybeSpecies.get();
            } else {
                throw new ResourceNotFoundException(
                        "Species can't be determined for " + propertyType + ":" + geneQuery.toJson());
            }
        }

        Species species= speciesFactory.create(speciesString);

        BaselineExperimentSearchResult searchResult =
                baselineAnalyticsSearchService.findExpressions(
                        geneQuery, conditionQuery, species, defaultQueryFactorType);

        populateModelWithMultiExperimentResults(
                request.getContextPath(), geneQuery, conditionQuery,species, searchResult, model);

        return "heatmap-data";
    }

    @RequestMapping(value = "/json/search/baselineResults", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String analyticsJson(@RequestParam(value = "query") SemanticQuery query,
                                @RequestParam(value = "species") String speciesString,
                                @RequestParam(value = "source") String defaultQueryFactorType,
                                Model model, HttpServletRequest request) {

        Species species = speciesFactory.create(speciesString);
        BaselineExperimentSearchResult searchResult = baselineAnalyticsSearchService.findExpressions(query, species, defaultQueryFactorType);
        populateModelWithMultiExperimentResults(request.getContextPath(), query, species, searchResult, model);

        return "heatmap-data";
    }

    private void populateModelWithMultiExperimentResults(
            String contextRoot, SemanticQuery geneQuery, SemanticQuery conditionQuery, Species species,
            BaselineExperimentSearchResult searchResult, Model model) {
        List<Factor> orderedFactors = Lists.newArrayList(searchResult.getFactorsAcrossAllExperiments());

        if (searchResult.containsFactorOfType("ORGANISM_PART")) {
            model.addAttribute(
                    "anatomogram",
                    anatomogramFactory.get("ORGANISM_PART", species, convert(orderedFactors), contextRoot));
        } else {
            model.addAttribute("anatomogram", gson.toJson(JsonNull.INSTANCE));
        }

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, orderedFactors, model);

        model.addAttribute("species", species.mappedName);
        model.addAttribute("isWidget", true);
        model.addAttribute("experiment", gson.toJson(JsonNull.INSTANCE));
        model.addAttribute("geneQuery", geneQuery);
        model.addAttribute("conditionQuery", conditionQuery);
    }

    private void populateModelWithMultiExperimentResults(
            String contextRoot, SemanticQuery query, Species species,
            BaselineExperimentSearchResult searchResult, Model model) {
        List<Factor> orderedFactors = Lists.newArrayList(searchResult.getFactorsAcrossAllExperiments());

        if (searchResult.containsFactorOfType("ORGANISM_PART")) {
            model.addAttribute(
                    "anatomogram",
                    anatomogramFactory.get("ORGANISM_PART", species, convert(orderedFactors), contextRoot));
        } else {
            model.addAttribute("anatomogram", gson.toJson(JsonNull.INSTANCE));
        }

        BaselineExperimentProfilesList experimentProfiles = searchResult.getExperimentProfiles();
        addJsonForHeatMap(experimentProfiles, orderedFactors, model);

        model.addAttribute("species", species.mappedName);
        model.addAttribute("isWidget", true);
        model.addAttribute("experiment", gson.toJson(JsonNull.INSTANCE));
        model.addAttribute("query", query);
    }

    private List<AssayGroupFactor> convert(List<Factor> orderedFactors) {
        ImmutableSortedSet.Builder<AssayGroupFactor> builder = ImmutableSortedSet.naturalOrder();

        for (Factor factor : orderedFactors) {
            builder.add( new AssayGroupFactor("none",factor));
        }

        return new ArrayList<>(builder.build());
    }

    private void addJsonForHeatMap(BaselineExperimentProfilesList baselineProfiles, List<Factor> orderedFactors, Model model) {
        if (baselineProfiles.isEmpty()) {
            return;
        }

        model.addAttribute("jsonColumnHeaders", gson.toJson(AssayGroupFactorViewModel.createList(convert(orderedFactors))));
        model.addAttribute("jsonColumnGroupings", gson.toJson(factorGroupingService.group(convert(orderedFactors))));
        model.addAttribute("jsonProfiles", gson.toJson(baselineExperimentProfilesViewModelBuilder.buildJson(baselineProfiles, orderedFactors)));
    }
}
