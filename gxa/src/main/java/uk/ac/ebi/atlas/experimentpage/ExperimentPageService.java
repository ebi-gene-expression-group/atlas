package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.tracks.GenomeBrowserController;
import uk.ac.ebi.atlas.utils.StringUtil;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ExperimentPageService {

    protected final Gson gson = new Gson();

    protected Map<String, JsonElement> payloadAttributes(Experiment experiment,
                                                         String accessKey, ExperimentPageRequestPreferences requestPreferences, Optional<String> theOnlyGeneInResults) {
        Map<String, JsonElement> result = new HashMap<>();

        result.put("experiment", experimentDescription(experiment, accessKey, requestPreferences, theOnlyGeneInResults));
        result.put("config", config(experiment, requestPreferences));
        return result;
    }

    private JsonElement experimentDescription(Experiment experiment,
                                              String accessKey, ExperimentPageRequestPreferences requestPreferences, Optional<String> theOnlyGeneInResults) {

        JsonObject experimentDescription = new JsonObject();
        experimentDescription.addProperty("accession", experiment.getAccession());
        experimentDescription.addProperty("type", experiment.getType().getDescription());

        JsonObject urls = new JsonObject();

        urls.addProperty("main_page",
                MessageFormat.format(
                        "experiments/{0}?geneQuery={1}",
                        experiment.getAccession(), requestPreferences.getGeneQuery().toUrlEncodedJson())
                        + (isBlank(accessKey) ? "" : "&accessKey=" + accessKey)
        );
        urls.addProperty("genome_browsers",
                GenomeBrowserController.redirectUrl(experiment.getAccession(), accessKey)
        );
        urls.addProperty("download",
                ExperimentDownloadController.getUrl(experiment.getAccession(), accessKey, experiment.getType(), requestPreferences)
        );

        theOnlyGeneInResults.ifPresent(gene -> {
            try {
                if (! experiment.getType().isRnaSeqBaseline()){
                    /*
                    We only support that for now - see JsonBaselineGeneInExperimentController.java
                     */
                    return;
                }
                urls.addProperty("gene_specific_results", geneSpecificResultsLink(experiment, gene, accessKey, requestPreferences).toString());
            } catch (URISyntaxException | IllegalAccessException | NoSuchMethodException | InvocationTargetException  e) {
                throw new RuntimeException(e);
            }
        } );
        experimentDescription.add("urls", urls);

        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());

        return experimentDescription;
    }

    URI geneSpecificResultsLink(Experiment experiment, String gene,
                                        String accessKey, ExperimentPageRequestPreferences requestPreferences) throws URISyntaxException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        URIBuilder builder = new URIBuilder(MessageFormat.format(
                    "json/experiments/{0}/genes/{1}",
                    experiment.getAccession(), gene
        ));
        for(Map.Entry<String, String> e: BeanUtils.describe(requestPreferences).entrySet()) {
            if(e.getKey().equals("class") || e.getKey().startsWith("default")){
                continue;
            }
            builder.addParameter(e.getKey(), e.getValue());
        }
        if(StringUtils.isNotBlank(accessKey)){
            builder.addParameter("accessKey", accessKey);
        }
        builder.addParameter("type", experiment.getType().name());
        return builder.build();
    }


    public static Optional<String> getTheOnlyId(List<? extends Profile> profiles) {
        return profiles.size() == 1 ? Optional.of(profiles.get(0).getId()) : Optional.empty();
    }

    private JsonObject config(Experiment<?> experiment, ExperimentPageRequestPreferences preferences) {
        JsonObject config = new JsonObject();
        config.addProperty("geneQuery", preferences.getGeneQuery().toUrlEncodedJson());
        config.addProperty("species", experiment.getSpecies().getName());
        config.add("genomeBrowsers", gson.toJsonTree(experiment.getGenomeBrowserNames()));
        config.addProperty("disclaimer", experiment.getDisclaimer());
        //only for the multiexperiment heatmap
        config.addProperty("columnType", "");
        config.addProperty("conditionQuery", "");
        return config;
    }
}
