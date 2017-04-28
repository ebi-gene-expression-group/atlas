package uk.ac.ebi.atlas.widget;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.experimentpage.baseline.AnatomogramFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.grouping.FactorGroupingService;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Controller
@Scope("request")
public final class JsonBaselineExperimentsController extends JsonExceptionHandlingController {

    private final AnatomogramFactory anatomogramFactory;
    private final SpeciesInferrer speciesInferrer;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final FactorGroupingService factorGroupingService;
    private final HeatmapDataToJsonService heatmapDataToJsonService;

    @Inject
    private JsonBaselineExperimentsController(SpeciesInferrer speciesInferrer,
                                              BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                              FactorGroupingService factorGroupingService,
                                              HeatmapDataToJsonService heatmapDataToJsonService) {
        this.anatomogramFactory = new AnatomogramFactory();
        this.speciesInferrer = speciesInferrer;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.factorGroupingService = factorGroupingService;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
    }

    @RequestMapping(
            value = "/widgets/heatmap/baselineAnalytics",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @Deprecated
    public String analyticsJson() {
        return "forward:/json/baseline_experiments";
    }

    @RequestMapping(
            value = "/json/baseline_experiments",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String jsonBaselineExperiments(
            @RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
            @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
            HttpServletRequest request, Model model) {

        Species species = speciesInferrer.inferSpecies(geneQuery, conditionQuery, speciesString);

        if (!species.isUnknown() && StringUtils.isBlank(source)) {
            source = species.getDefaultQueryFactorType();
        }

        BaselineExperimentProfilesList experimentProfiles =
                baselineAnalyticsSearchService.findExpressions(geneQuery, conditionQuery, species, source);

        model.addAttribute("geneQuery", geneQuery.toUrlEncodedJson());
        model.addAttribute("conditionQuery", conditionQuery.toUrlEncodedJson());

        JsonObject result = new JsonObject();
        List<FactorAcrossExperiments> dataColumns = experimentProfiles.getFactorsAcrossExperiments();

        result.add(
                "anatomogram",
                anatomogramFactory.get(source, species,
                        FluentIterable.from(dataColumns).transformAndConcat(
                                new Function<FactorAcrossExperiments, Iterable<? extends OntologyTerm>>() {
                                    @Nullable
                                    @Override
                                    public Iterable<? extends OntologyTerm>
                                    apply(@Nullable FactorAcrossExperiments factorAcrossExperiments) {
                                        return factorAcrossExperiments.getValueOntologyTerms();
                                    }
                                }
                        )
                ).or(JsonNull.INSTANCE)
        );

        if(!experimentProfiles.isEmpty()){
            result.add("columnHeaders", constructColumnHeaders(dataColumns));

            result.add("columnGroupings", factorGroupingService.group(source, dataColumns));

            result.add(
                    "profiles",
                    new ExternallyViewableProfilesList<>(
                            experimentProfiles, provideLinkToProfile(geneQuery), dataColumns).asJson());
        }

        model.addAttribute("species", species.getReferenceName());
        model.addAttribute("isWidget", true);

        result.add("config", heatmapDataToJsonService.configAsJsonObject(request, model.asMap()));
        return gson.toJson(result);
    }

    private Function<BaselineExperimentProfile, URI> provideLinkToProfile(SemanticQuery geneQuery){
        try {
            final URI experimentsLocation = new URI("experiments/");
            final Map<String, String> params = ImmutableMap.of("geneQuery", geneQuery.toUrlEncodedJson());
            return new Function<BaselineExperimentProfile, URI>() {
                @Nullable
                @Override
                public URI apply(@Nullable BaselineExperimentProfile baselineExperimentProfile) {
                    return experimentsLocation.resolve(baselineExperimentProfile.getId()+
                            "?"+ Joiner.on("&").withKeyValueSeparator("=").join(params.entrySet())
                    );
                }
            };
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonArray constructColumnHeaders(List<FactorAcrossExperiments> dataColumnsToReturn){
        JsonArray result = new JsonArray();

        for(FactorAcrossExperiments dataColumnDescriptor: dataColumnsToReturn){
            result.add(dataColumnDescriptor.toJson());
        }

        return result;
    }
}
