package uk.ac.ebi.atlas.landingpage;

import com.google.gson.JsonArray;
import org.springframework.http.MediaType;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.card.CardModel;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class LandingCardController  extends HtmlExceptionHandlingController {

    private final ScxaExperimentTrader experimentTrader;
    private final ExperimentAttributesService experimentAttributesService;
    private final ScxaExperimentDao experimentDao;


    @Inject
    public LandingCardController(ScxaExperimentTrader experimentTrader,
                                 ExperimentAttributesService experimentAttributesService,
                                 ScxaExperimentDao experimentDao) {
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
        this.experimentDao = experimentDao;
    }


    @RequestMapping(value = {"/json/landingpage/{experimentAccession}"},
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String showExperimentPage(Model model,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(defaultValue = "") String accessKey) {
        Set<String> allLikeExperiment = experimentDao.getExperimentByGroupAccession(experimentAccession);

        List<Experiment<Cell>> allExperiment = allLikeExperiment.stream()
                .map(likeExperiment -> experimentTrader.getExperiment(likeExperiment, accessKey))
                .collect(Collectors.toList());

        List<Map<String, Object>> attributes = allExperiment.stream()
                .map(experiment -> experimentAttributesService.getAttributes(experiment))
                .collect(Collectors.toList());

        List<CardModel> cardModels = attributes
                .stream()
                .map(CardModelFactory::createLandingPageCard)
                .collect(Collectors.toList());

        return CardModelAdapter.serialize(cardModels).toString();
    }

}