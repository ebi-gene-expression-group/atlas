package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class MarkerGenesSearchController {

    private final MarkerGenesSearchService markerGenesSearchService;
    private final Gson gson = new Gson();

    @Inject
    public MarkerGenesSearchController(MarkerGenesSearchService markerGenesSearchService) {
        this.markerGenesSearchService = markerGenesSearchService;
    }

    @RequestMapping(value = "/json/markerGenes/{geneId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String jsonMarkerGenes(@PathVariable String geneId) {
        ImmutableList<ImmutablePair<MarkerGeneProfile, String>> results =
                markerGenesSearchService.searchMarkerGenesByGeneId(geneId);

        JsonArray markerProfiles = new JsonArray();
        results.forEach(pair -> {
            JsonObject markerProfile = new JsonObject();
            markerProfile.addProperty("experimentAccession", pair.getLeft().experimentAccession());
            markerProfile.addProperty("k", pair.getLeft().k());

            JsonArray clusters = new JsonArray();
            pair.getLeft().clusters().forEach(clusterPair -> {
                JsonObject cluster = new JsonObject();
                cluster.addProperty("clusterId", clusterPair.getLeft());
                cluster.addProperty("p", clusterPair.getRight());
                clusters.add(cluster);
            });
            markerProfile.add("clusters", clusters);
            markerProfile.addProperty("url", pair.getRight());

            markerProfiles.add(markerProfile);
        });

        return gson.toJson(markerProfiles);
    }

}
