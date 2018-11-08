package uk.ac.ebi.atlas.landingpage;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.card.CardModel;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
public class JsonLandingSpeciesCardController extends HtmlExceptionHandlingController {

    private final ScxaExperimentTrader experimentTrader;
    private final ExperimentAttributesService experimentAttributesService;
    private final ScxaExperimentDao scxaExperimentDao;

    @Inject
    public JsonLandingSpeciesCardController(ScxaExperimentTrader experimentTrader,
                                     ExperimentAttributesService experimentAttributesService,
                                     ScxaExperimentDao scxaExperimentDao) {
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
        this.scxaExperimentDao = scxaExperimentDao;
    }

    @RequestMapping(value = {"/json/landingpage/species/{species}"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String showExperimentPage(@PathVariable String species,
                                     @RequestParam(defaultValue = "") String accessKey) {
        Set<String> allSpeciesExperiment = scxaExperimentDao.getExperimentBySpecies(species);

        List<Experiment<Cell>> allExperiment = allSpeciesExperiment.stream()
                .map(speciesExperiment -> experimentTrader.getExperiment(speciesExperiment, accessKey))
                .collect(Collectors.toList());

        List<Map<String, Object>> attributes = allExperiment.stream()
                .map(experiment -> experimentAttributesService.getAttributes(experiment))
                .collect(Collectors.toList());

        List<Pair<String, Optional<String>>> content = attributes.stream()
                .map(attribute ->
                        Pair.of(String.valueOf(attribute.get("experimentAccession")),
                                Optional.of(getExperimentPageUrlByAccession(String.valueOf(attribute.get("experimentAccession"))))))
                .collect(Collectors.toList());

        List<CardModel> cardModels = new ArrayList<>();
        cardModels.add(CardModelFactory.createLandingPageCard(attributes.get(0), content));

        return CardModelAdapter.serialize(cardModels).toString();
    }

    private static String getExperimentPageUrlByAccession(String accession) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(accession)
                .encode()
                .toUriString();
    }

}
