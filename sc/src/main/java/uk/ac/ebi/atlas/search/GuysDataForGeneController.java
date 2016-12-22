package uk.ac.ebi.atlas.search;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.resource.SingleCellFileHub;
import uk.ac.ebi.atlas.search.analyticsindex.AnalyticsSearchService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Controller
public class GuysDataForGeneController { // give me a better class name when I become something more generic

    private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final AnalyticsSearchService analyticsSearchService;

    private final Species SPECIES_IN_GUYS_EXPERIMENT;

    private final Map<String, String> guysIdentifiersPerEnsemblId;

    @Inject
    public GuysDataForGeneController(AnalyticsSearchService analyticsSearchService, SpeciesFactory speciesFactory,
                                     SingleCellFileHub singleCellFileHub) throws URISyntaxException, IOException {
        this.analyticsSearchService = analyticsSearchService;
        this.SPECIES_IN_GUYS_EXPERIMENT = speciesFactory.create("mus musculus");
        ImmutableMap.Builder<String, String> b = new ImmutableMap.Builder<>();

        for(String[] bits: singleCellFileHub.getGuysIdentifiers().get().readAll()){
            if(bits.length == 2){
                b.put(bits[1],bits[0]);
            } else {
                throw new IllegalStateException("Bad line in file: "+ Arrays.deepToString(bits));
            }
        }
        guysIdentifiersPerEnsemblId = b.build();
    }


    @RequestMapping(value = "/json/experiments/{experimentAccession}/expression")
    @ResponseBody
    public String baselineExperimentData(@PathVariable String experimentAccession,
                                         @RequestParam(value = "query", defaultValue = "") SemanticQuery query,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return gson.toJson(baselineExperimentDataAsArray(query));
    }

    JsonObject baselineExperimentDataAsArray(SemanticQuery query){
        return transformGuysData(getGuysData(getGuysIdentifiers(query)));
    }

    private Set<String> getGuysIdentifiers(SemanticQuery query){
        ImmutableSet.Builder<String> b = ImmutableSet.builder();
        for(String bioentityIdentifier: analyticsSearchService.searchBioentityIdentifiers(query, SemanticQuery.create(), SPECIES_IN_GUYS_EXPERIMENT.getReferenceName())){
            if(guysIdentifiersPerEnsemblId.containsKey(bioentityIdentifier)){
                b.add(guysIdentifiersPerEnsemblId.get(bioentityIdentifier));
            }
        }
        return b.build();
    }

    private URL buildUrl(Collection<String> guysIdentifiers){
        Validate.notEmpty(guysIdentifiers);
        StringBuilder sb = new StringBuilder("http://scb.sanger.ac.uk/api/sample_data" +
                "?experiment_id=1&mean_expression=1&pivot=1&variable=bgplvm_2d_0&variable=bgplvm_2d_1&variable=day");
        for(String guysIdentifier: guysIdentifiers){
            sb.append(MessageFormat.format("&gene_id={0}", guysIdentifier));
        }

        try {
            return new URL(sb.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonArray getGuysData(Collection<String> guysIdentifiers){
        try {
            return guysIdentifiers.size()>0 ?
                    gson.fromJson(IOUtils.toString(
                            buildUrl(guysIdentifiers), Charset.forName("UTF-8")), JsonArray.class)
                    : new JsonArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private JsonObject transformGuysData(JsonArray array){
        JsonObject result = new JsonObject();
        for(JsonElement e: array){
            String key = e.getAsJsonObject().get("sample").getAsString();
            result.add(key, e.getAsJsonObject().get("Expression"));
        }
        return result;
    }

}
