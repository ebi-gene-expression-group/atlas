package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentpage.baseline.profiles.BaselineExperimentProfilesListSerializer;
import uk.ac.ebi.atlas.experimentpage.baseline.profiles.BaselineExperimentProfilesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerReplicateProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.stream.BaselineTranscriptProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.validation.Valid;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonBaselineGeneInExperimentController extends JsonExperimentController {
    private final BaselineExperimentProfilesService BaselineExperimentProfilesService;
    private final BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory;

    public JsonBaselineGeneInExperimentController(ExperimentTrader experimentTrader,
                                                  BaselineExperimentProfilesService BaselineExperimentProfilesService,
                                                  BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory) {
        super(experimentTrader);
        this.BaselineExperimentProfilesService = BaselineExperimentProfilesService;
        this.baselineTranscriptProfileStreamFactory = baselineTranscriptProfileStreamFactory;
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/genes/{geneId:.+}", // Gene IDs may contain dots!
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqDataForOneGeneInExperiment(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) {

        BaselineExperiment experiment =
                (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        JsonObject result = new JsonObject();
        result.add("config", config(experiment, preferences));

        BaselineRequestContext<ExpressionUnit.Absolute.Rna> requestContext =
                new BaselineRequestContext<>(preferences, experiment);
        result.add("columnHeaders", columnHeaders(requestContext));

        GeneProfilesList<BaselineProfile> geneExpression =
                BaselineExperimentProfilesService.getGeneProfiles(
                        experimentAccession, requestContext.getDataColumnsToReturn(), preferences, geneId);

        // We set cutoff to 0.0, which happens to do "do not read for kryo files" for transcript expression
        preferences.setCutoff(0.0);
        GeneProfilesList<BaselineExpressionPerReplicateProfile> transcriptExpression =
                baselineTranscriptProfileStreamFactory.getAllMatchingProfiles(
                        experiment,
                        requestContext,
                        ImmutableSet.of(geneId));

        if (!transcriptExpression.isEmpty()) {
            result.add(
                    "transcriptExpression",
                    BaselineExperimentProfilesListSerializer.serialize(transcriptExpression, requestContext));
        }
        if (!geneExpression.isEmpty()) {
            result.add(
                    "geneExpression",
                    BaselineExperimentProfilesListSerializer.serialize(geneExpression, requestContext));
        }

        return GSON.toJson(result);
    }

    private JsonArray columnHeaders(BaselineRequestContext<?> requestContext) {
        JsonArray columnHeaders = new JsonArray();
        for (AssayGroup assayGroup : requestContext.getDataColumnsToReturn()) {
            JsonObject header = assayGroup.toJson();
            header.addProperty("name", requestContext.displayNameForColumn(assayGroup));
            columnHeaders.add(header);
        }
        return columnHeaders;
    }

    /*
     I am not sure if any of these properties are necessary, or useful
     we use the "cutoff", which otherwise doesn't do anything but got selected in the UI, to draw the user something visual
    */
    private JsonObject config(BaselineExperiment experiment, ExperimentPageRequestPreferences preferences) {
        JsonObject config = GSON.toJsonTree(preferences).getAsJsonObject();
        config.addProperty("cutoff", preferences.getCutoff());
        config.addProperty("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        config.addProperty("species", experiment.getSpecies().getName());
        config.addProperty("disclaimer", experiment.getDisclaimer());
        return config;
    }
}
