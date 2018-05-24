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
import uk.ac.ebi.atlas.solr.bioentities.query.SolrBioentitiesSuggesterService;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public class AutocompleteController extends JsonExceptionHandlingController {
    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";

    protected static final int FEATURED_SPECIES = 0;

    private final SolrBioentitiesSuggesterService suggesterService;
    private final SpeciesFactory speciesFactory;
    private final FeaturedSpeciesService featuredSpeciesService;
    private final LazyReference<String> speciesSelectJson = new LazyReference<String>() {
        @Override
        protected String create() {
            return getter();
        }
    };

    public AutocompleteController(SolrBioentitiesSuggesterService suggesterService,
                                  SpeciesFactory speciesFactory,
                                  FeaturedSpeciesService featuredSpeciesService) {
        this.suggesterService = suggesterService;
        this.speciesFactory = speciesFactory;
        this.featuredSpeciesService = featuredSpeciesService;
    }

    @RequestMapping(value = "/json/suggestions",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "20") int suggestCount) {

        String[] splitSpecies = species.split(",");
        return GSON.toJson(
                Arrays.stream(splitSpecies)
                    .map(speciesFactory::create)
                    .flatMap(speciesObject ->
                            suggesterService.fetchBioentitySuggestions(
                                    query, speciesObject, suggestCount / splitSpecies.length)
                                    .stream())
                    .collect(toImmutableList()));

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
                        "allSpecies", ImmutableList.sortedCopyOf(topSpeciesNames),
                        "separator", ThreadLocalRandom.current().nextDouble() > 0.999 ? BEST_SEPARATOR : NORMAL_SEPARATOR));
    }
}
