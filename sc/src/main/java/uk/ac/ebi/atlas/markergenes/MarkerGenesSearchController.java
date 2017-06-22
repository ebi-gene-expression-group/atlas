package uk.ac.ebi.atlas.markergenes;

import com.google.gson.Gson;
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
    public String blah(@PathVariable String geneId) {
        return gson.toJson(markerGenesSearchService.searchMarkerGenesByGeneId(geneId));
    }
}
