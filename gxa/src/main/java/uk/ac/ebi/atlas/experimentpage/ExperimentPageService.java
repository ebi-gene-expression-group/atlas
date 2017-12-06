package uk.ac.ebi.atlas.experimentpage;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.utils.URIBuilder;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.tracks.GenomeBrowserController;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                callbackLink(
                        MessageFormat.format("experiments/{0}", experiment.getAccession()),
                        accessKey,
                        Pair.of("geneQuery", requestPreferences.getGeneQuery().toUrlEncodedJson())
                ).toString()
        );
        /*
        Fix me maybe if you're working on genome browsers anyway?
        The widget needs to know about this URL, and also genomeBrowsers in the config.
        Consider looping here over genome browser names, and adding the individual URLs that each produce a good callback- perhaps genome_browser_<name>.
        Then we can have simpler code and also tests or the form "all provided urls are valid links".
         */
        urls.addProperty("genome_browsers",
                callbackLink(
                        MessageFormat.format(GenomeBrowserController.REDIRECT_URL_TEMPLATE, experiment.getAccession()),
                        accessKey
                ).toString()
        );
        urls.addProperty("download",
                experimentDownloadLink(experiment, accessKey, requestPreferences).toString()
        );
        if (experiment.getType().isRnaSeqBaseline()) {
            theOnlyGeneInResults.ifPresent(gene ->
                    urls.addProperty("gene_specific_results",
                            geneSpecificResultsLink(experiment, gene, accessKey, requestPreferences).toString())
            );
        }

        experimentDescription.add("urls", urls);

        experimentDescription.addProperty("type", experiment.getType().getDescription());
        experimentDescription.addProperty("description", experiment.getDescription());
        experimentDescription.addProperty("species", experiment.getSpecies().getName());

        return experimentDescription;
    }

    private URI callbackLink(String urlBase, String accessKey, Pair<String, String>... params) {
        try {
            URIBuilder builder = new URIBuilder(urlBase);
            for (Pair<String, String> p : params) {
                builder.addParameter(p.getLeft(), p.getRight());
            }
            if (StringUtils.isNotBlank(accessKey)) {
                builder.addParameter("accessKey", accessKey);
            }
            return builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private URI callbackLinkWithRequestPreferences(String urlBase, ExperimentType experimentType, String accessKey, ExperimentPageRequestPreferences requestPreferences) {
        try {
            URIBuilder builder = new URIBuilder(urlBase);
            for (Map.Entry<String, String> e : BeanUtils.describe(requestPreferences).entrySet()) {
                if (e.getKey().equals("class")  // name of the class from reflection on the object - we don't want that
                        || e.getKey().startsWith("default") // the bean has these for Spring to use as default values
                        || e.getKey().equals("selectedColumnIds") //Quirk / bug of BeanUtils - picks up first value only
                        ) {
                    continue;
                }
                builder.addParameter(e.getKey(), e.getValue());
            }
            builder.addParameter(
                    "selectedColumnIds",
                    Joiner.on(",").join(requestPreferences.getSelectedColumnIds())
            );
            if (StringUtils.isNotBlank(accessKey)) {
                builder.addParameter("accessKey", accessKey);
            }
            // routing in ExperimentDownloadController relies on this
            builder.addParameter("type", experimentType.getParent().name().toUpperCase());
            return builder.build();

        } catch (URISyntaxException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    URI experimentDownloadLink(Experiment experiment, String accessKey, ExperimentPageRequestPreferences requestPreferences) {
        return callbackLinkWithRequestPreferences(
                ExperimentDownloadController.url
                        .replace("{experimentAccession}", experiment.getAccession())
                        .replace("{experimentType}", experiment.getType().getParent().name().toUpperCase())
                , experiment.getType(), accessKey, requestPreferences
        );
    }

    URI geneSpecificResultsLink(Experiment experiment, String gene,
                                String accessKey, ExperimentPageRequestPreferences requestPreferences) {
        return callbackLinkWithRequestPreferences(
                MessageFormat.format(
                        "json/experiments/{0}/genes/{1}",
                        experiment.getAccession(), gene
                ), experiment.getType(), accessKey, requestPreferences
        );
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
