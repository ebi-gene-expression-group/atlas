package uk.ac.ebi.atlas.search;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.search.suggester.SolrSuggestionReactSelectAdapter;
import uk.ac.ebi.atlas.search.suggester.SuggesterService;

import static java.util.stream.Collectors.toList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public class AutocompleteController extends JsonExceptionHandlingController {
    protected static final int FEATURED_SPECIES = 0;

    private final SuggesterService suggesterService;
    private final FeaturedSpeciesService featuredSpeciesService;

    private final LazyReference<String> speciesSelectJson = new LazyReference<String>() {
        @Override
        protected String create() {
            return getter();
        }
    };

    public AutocompleteController(SuggesterService suggesterService,
                                  FeaturedSpeciesService featuredSpeciesService) {
        this.suggesterService = suggesterService;
        this.featuredSpeciesService = featuredSpeciesService;
    }

    @RequestMapping(value = "/json/suggestions",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species) {
        return GSON.toJson(
                suggesterService.fetchIdentifiers(query, species.split(",")).collect(toList()));
    }

    // If needed we can have request parameters to determine the format of the reply (for react-select or not) and
    // expose the highlight boolean as well
    @RequestMapping(value = "/json/suggestions/gene_ids",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String fetchGeneIdSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species) {
        return GSON.toJson(
                SolrSuggestionReactSelectAdapter.serialize(
                        suggesterService.fetchPropertiesWithoutHighlighting(query, species.split(","))));
    }

    @RequestMapping(value = "/json/suggestions/species",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String fetchTopSuggestions() {
        return speciesSelectJson.get();
    }

    private String getter() {
        ImmutableList<String> topSpeciesNames = featuredSpeciesService.getSpeciesNamesSortedByExperimentCount();

        return GSON.toJson(
                ImmutableMap.of(
                        "topSpecies", topSpeciesNames.subList(0, Math.min(topSpeciesNames.size(), FEATURED_SPECIES)),
                        "allSpecies", ImmutableList.sortedCopyOf(topSpeciesNames)));
    }
}
