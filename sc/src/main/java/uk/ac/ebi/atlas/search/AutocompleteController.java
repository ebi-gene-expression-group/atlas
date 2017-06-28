package uk.ac.ebi.atlas.search;

import com.atlassian.util.concurrent.LazyReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.solr.query.SolrBioentitiesSuggesterService;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.species.services.PopularSpeciesService;

import javax.inject.Inject;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@Scope("request")
public class AutocompleteController extends JsonExceptionHandlingController {

    private static final int FEATURED_SPECIES = 6;
    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";

    private final SolrBioentitiesSuggesterService suggesterService;
    private final PopularSpeciesService popularSpeciesService;
    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final SpeciesFactory speciesFactory;
    private final LazyReference<String> speciesSelectJson = new LazyReference<String>() {
        @Override
        protected String create() throws Exception {
            return getter();
        }
    };

    @Inject
    public AutocompleteController(SolrBioentitiesSuggesterService suggesterService,
                                  PopularSpeciesService popularSpeciesService,
                                  SpeciesPropertiesTrader speciesPropertiesTrader,
                                  SpeciesFactory speciesFactory) {
        this.suggesterService = suggesterService;
        this.popularSpeciesService = popularSpeciesService;
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.speciesFactory = speciesFactory;
    }

    @RequestMapping(value = "/json/suggestions", method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "species", required = false, defaultValue = "") String species,
            @RequestParam(value = "suggestCount", required = false, defaultValue = "15") int suggestCount) {

        return new Gson().toJson(suggesterService.fetchBioentitySuggestions(query, speciesFactory.create(species), suggestCount));
    }

    @RequestMapping(value = "/json/suggestions/species", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String fetchTopSuggestions() {
        return speciesSelectJson.get();
    }

    private String getter() {
        JsonArray topSixSpecies = new JsonArray();
        popularSpeciesService.getPopularSpecies(FEATURED_SPECIES)
                .forEach(psi -> topSixSpecies.add(StringUtils.capitalize(psi.species())));

        JsonArray allSpecies = new JsonArray();
        speciesPropertiesTrader.getAll()
                .forEach(sp -> allSpecies.add(StringUtils.capitalize(sp.referenceName())));

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("topSpecies", topSixSpecies);
        jsonObject.add("allSpecies", allSpecies);
        jsonObject.addProperty("separator", ThreadLocalRandom.current().nextDouble() > 0.999 ? BEST_SEPARATOR : NORMAL_SEPARATOR);

        return gson.toJson(jsonObject);
    }

}
