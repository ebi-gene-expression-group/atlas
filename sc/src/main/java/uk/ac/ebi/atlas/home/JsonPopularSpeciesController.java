package uk.ac.ebi.atlas.home;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.species.services.PopularSpeciesInfo;
import uk.ac.ebi.atlas.species.services.PopularSpeciesService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class JsonPopularSpeciesController extends JsonExceptionHandlingController {

    private PopularSpeciesService popularSpeciesService;

    @Inject
    public JsonPopularSpeciesController(PopularSpeciesService popularSpeciesService) {
        this.popularSpeciesService = popularSpeciesService;
    }

    @GetMapping(
            value = "/json/experiments/popularSpecies",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getPopularExperimentsByKingdom(
            @RequestParam Optional<String> kingdom,
            @RequestParam(defaultValue = "6") int limit) {
        List<PopularSpeciesInfo> speciesNameWithExperimentCount = kingdom.isPresent() ?
                popularSpeciesService.getPopularSpecies(kingdom.get(), limit) :
                popularSpeciesService.getPopularSpecies(limit);

        List<CardModel> cardModels = speciesNameWithExperimentCount
                .stream()
                .map(CardModelFactory::create)
                .collect(Collectors.toList());

        return CardModelAdapter.serialize(cardModels).toString();
    }
}
