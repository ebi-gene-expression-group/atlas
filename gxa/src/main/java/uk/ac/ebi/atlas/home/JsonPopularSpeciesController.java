package uk.ac.ebi.atlas.home;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.card.CardModel;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class JsonPopularSpeciesController extends JsonExceptionHandlingController {

  private PopularSpeciesService popularSpeciesService;
  private CardModelFactory cardModelFactory;

  @Inject
  public JsonPopularSpeciesController(PopularSpeciesService popularSpeciesService,
                                      CardModelFactory cardModelFactory) {
    this.popularSpeciesService = popularSpeciesService;
    this.cardModelFactory = cardModelFactory;
  }

  @GetMapping(
          value = "/json/experiments/popular-species",
          produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String getPopularExperimentsByKingdom(
          @RequestParam Optional<String> kingdom,
          @RequestParam(defaultValue = "6") int limit) {

    List<PopularSpeciesInfo> speciesNameWithExperimentCount = kingdom.isPresent() ?
            popularSpeciesService.getPopularSpecies(kingdom.get(), limit) :
            popularSpeciesService.getPopularSpecies(limit);

    List<CardModel> cardModels = speciesNameWithExperimentCount
            .stream()
            .map(cardModelFactory::createAtlasHomePageSpeciesCard)
            .collect(Collectors.toList());

    return CardModelAdapter.serialize(cardModels).toString();
  }
}
