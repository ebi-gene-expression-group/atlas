package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentpage.LinkToGene;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerReplicateProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.profiles.stream.BaselineTranscriptProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.validation.Valid;


@RestController
public class JsonBaselineGeneInExperimentController extends JsonExperimentController {
    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private final RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory;
    private final BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory;

    @Inject
    public JsonBaselineGeneInExperimentController(ExperimentTrader experimentTrader,
                                                  BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory,
                                                  RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory) {
        super(experimentTrader);
        this.baselineTranscriptProfileStreamFactory = baselineTranscriptProfileStreamFactory;
        this.rnaSeqBaselineProfileStreamFactory = rnaSeqBaselineProfileStreamFactory;
    }


    @RequestMapping(value = "/json/experiments/{experimentAccession}/genes/{geneId}",
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqDataForOneGeneInExperiment(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) {

        /*
        This code accesses expression files in a somewhat messy way.
        We want all data, no matter the cutoff - so we "set request all data" for gene expression files
        This is actually "all but zeros" so it will read from Kryo files
        Then we set cutoff to 0.0, which happens to do "do not read for kryo files" for transcript expression

        Kick this all out and replace with Just Right Solr queries when the time comes.
         */
        BaselineRequestPreferences.setRequestAllData(preferences);

        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);

        BaselineRequestContext<ExpressionUnit.Absolute.Rna> requestContext = new BaselineRequestContext<>(preferences, experiment);

        JsonObject result = new JsonObject();
        result.add("config", config(experiment, preferences));
        result.add("columnHeaders", columnHeaders(requestContext));

        GeneProfilesList<BaselineProfile> geneExpression =
                rnaSeqBaselineProfileStreamFactory.getAllMatchingProfiles(
                        experiment,
                        requestContext,
                        ImmutableSet.of(geneId)
                );

        preferences.setCutoff(0.0);
        GeneProfilesList<BaselineExpressionPerReplicateProfile> transcriptExpression =
                baselineTranscriptProfileStreamFactory.getAllMatchingProfiles(
                        experiment,
                        requestContext,
                        ImmutableSet.of(geneId)
                );

        if (!transcriptExpression.isEmpty()) {
            result.add("transcriptExpression", toJson(transcriptExpression, requestContext));
        }
        if (!geneExpression.isEmpty()) {
            result.add("geneExpression", toJson(geneExpression, requestContext));
        }

        return gson.toJson(result);
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

    private JsonObject toJson(GeneProfilesList<?> profiles, BaselineRequestContext<ExpressionUnit.Absolute.Rna> requestContext) {
        return new ExternallyViewableProfilesList<>(
                profiles,
                new LinkToGene<>(),
                requestContext.getDataColumnsToReturn(),
                p -> requestContext.getExpressionUnit()

        ).asJson();
    }

    /*
     I am not sure if any of these properties are necessary, or useful
     potentially: use the "cutoff", which otherwise doesn't do anything but got selected in the UI, to draw the user something visual
    */
    private JsonObject config(BaselineExperiment experiment, ExperimentPageRequestPreferences preferences) {
        JsonObject config = gson.toJsonTree(preferences, JsonObject.class).getAsJsonObject();
        config.addProperty("cutoff", preferences.getCutoff());
        config.addProperty("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        config.addProperty("species", experiment.getSpecies().getName());
        config.addProperty("disclaimer", experiment.getDisclaimer());
        return config;
    }
}
