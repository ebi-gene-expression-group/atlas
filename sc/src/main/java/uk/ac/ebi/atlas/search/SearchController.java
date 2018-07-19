package uk.ac.ebi.atlas.search;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Controller
public class SearchController extends HtmlExceptionHandlingController {
    private static final String SEARCH_ENDPOINT = "/search";

    @RequestMapping(value = SEARCH_ENDPOINT, method = RequestMethod.POST)
    public String parseJsonAsRequestParamsAndRedirect(
            @RequestParam(value = "geneQuery") String geneQuery,
            @RequestParam(value = "species", defaultValue = "") String species) {
        JsonObject geneQueryObject = GSON.fromJson(geneQuery, JsonObject.class);

        String getSearchUrl = UriComponentsBuilder
                .newInstance()
                .path(SEARCH_ENDPOINT)
                .queryParam(geneQueryObject.get("category").getAsString(), geneQueryObject.get("term").getAsString())
                .queryParam("species", species)
                .build()
                .toUriString();

        return "redirect:" + getSearchUrl;

    }

    @RequestMapping(value = SEARCH_ENDPOINT, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String getSearch(@RequestParam MultiValueMap<String, String> requestParams,
                            Model model) {
        String endpoint =
                UriComponentsBuilder
                        .newInstance()
                        .path("json/search")
                        .queryParams(requestParams)
                        .build()
                        .toUriString();

        model.addAttribute("endpoint", endpoint);

        return "gene-search-results";
    }

}
