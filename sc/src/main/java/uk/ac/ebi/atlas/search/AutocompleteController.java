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

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@Scope("request")
public class AutocompleteController extends JsonExceptionHandlingController {
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
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {
        return GSON.toJson(
                suggesterService.fetchBioentitySuggestions(query, speciesFactory.create(species), suggestCount));
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
