package uk.ac.ebi.atlas.widget;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchService;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.LinkToBaselineProfile;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Controller
@Scope("request")
public final class JsonBaselineExperimentsController extends JsonExceptionHandlingController {

    public static final String url = "/json/baseline_experiments";
    private final AnatomogramFactory anatomogramFactory;
    private final SpeciesInferrer speciesInferrer;
    private final BaselineAnalyticsSearchService baselineAnalyticsSearchService;
    private final FactorGroupingService factorGroupingService;

    @Inject
    private JsonBaselineExperimentsController(SpeciesInferrer speciesInferrer,
                                              BaselineAnalyticsSearchService baselineAnalyticsSearchService,
                                              FactorGroupingService factorGroupingService) {
        this.anatomogramFactory = new AnatomogramFactory();
        this.speciesInferrer = speciesInferrer;
        this.baselineAnalyticsSearchService = baselineAnalyticsSearchService;
        this.factorGroupingService = factorGroupingService;
    }

    @RequestMapping(
            value = "/widgets/heatmap/baselineAnalytics",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @Deprecated
    public String analyticsJson() {
        return "forward:"+url;
    }

    @RequestMapping(
            value = url,
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String jsonBaselineExperiments(
            @RequestParam(value = "geneQuery", required = false, defaultValue = "") SemanticQuery geneQuery,
            @RequestParam(value = "conditionQuery", required = false, defaultValue = "") SemanticQuery conditionQuery,
            @RequestParam(value = "source", required = false) String source,
            @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
            Model model) {
        Preconditions.checkState(!(SemanticQuery.isEmpty(geneQuery) && SemanticQuery.isEmpty(conditionQuery)),
                "Please specify a gene query or a condition query");

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
                    ExternallyViewableProfilesList.createForExperimentProfiles(geneQuery, experimentProfiles, dataColumns).asJson());
        }

        model.addAttribute("species", species.getReferenceName());

        result.add("config", configAsJsonObject(model.asMap()));
        return gson.toJson(result);
    }

    private JsonArray constructColumnHeaders(List<FactorAcrossExperiments> dataColumnsToReturn){
        JsonArray result = new JsonArray();

        for(FactorAcrossExperiments dataColumnDescriptor: dataColumnsToReturn){
            result.add(dataColumnDescriptor.toJson());
        }

        return result;
    }

    //see also: similar method in ExperimentPageService
    JsonObject configAsJsonObject(Map<String, Object> model) {
        JsonObject config = new JsonObject();
        config.addProperty("geneQuery", getOrDefault(model, "query", get(model, "geneQuery")));
        config.addProperty("conditionQuery", get(model, "conditionQuery"));
        config.addProperty("species", get(model, "species"));
        config.add("resources", getAsJsonSerializable(model, "resources", new JsonObject()));
        config.addProperty("columnType", get(model, "queryFactorName").toLowerCase()); //TODO this looks broken - never populated, and the frontend has to default to "Experimental Condition"
        config.addProperty("disclaimer", get(model, "disclaimer"));
        return config;
    }

    private JsonElement getAsJsonSerializable(Map<String, Object> model, String key, JsonElement defaultValue) {
        if(model.containsKey(key)) {
            return gson.toJsonTree(model.get(key));
        } else {
            return defaultValue;
        }
    }

    private String getOrDefault(Map<String, Object> model, String key, String defaultValue) {
        if (model.containsKey(key)) {
            return model.get(key).toString();
        } else {
            return defaultValue;
        }
    }
    private String get(Map<String, Object> model, String key){
        return getOrDefault(model, key, "");
    }
}
