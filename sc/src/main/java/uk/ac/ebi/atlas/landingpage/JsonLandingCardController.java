package uk.ac.ebi.atlas.landingpage;

        import org.springframework.http.MediaType;

        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.RestController;

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

        import java.util.List;
        import java.util.Map;
        import java.util.Set;
        import java.util.stream.Collectors;


@RestController
public class JsonLandingCardController extends HtmlExceptionHandlingController {

    private final ScxaExperimentTrader experimentTrader;
    private final ExperimentAttributesService experimentAttributesService;
    private final ScxaExperimentDao scxaExperimentDao;

    @Inject
    public JsonLandingCardController(ScxaExperimentTrader experimentTrader,
                                     ExperimentAttributesService experimentAttributesService,
                                     ScxaExperimentDao scxaExperimentDao) {
        this.experimentTrader = experimentTrader;
        this.experimentAttributesService = experimentAttributesService;
        this.scxaExperimentDao = scxaExperimentDao;
    }

    @RequestMapping(value = {"/json/landingpage/{experimentAccession}"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String showExperimentPage(@PathVariable String experimentAccession,
                                     @RequestParam(defaultValue = "") String accessKey) {
        Set<String> allLikeExperiment = scxaExperimentDao.getExperimentByGroupAccession(experimentAccession);

        List<Experiment<Cell>> allExperiment = allLikeExperiment.stream()
                .map(likeExperiment -> experimentTrader.getExperiment(likeExperiment, accessKey))
                .collect(Collectors.toList());

        List<Map<String, Object>> attributes = allExperiment.stream()
                .map(experiment -> experimentAttributesService.getAttributes(experiment))
                .collect(Collectors.toList());

        List<CardModel> cardModels = attributes.stream()
                .map(CardModelFactory::createLandingPageCard)
                .collect(Collectors.toList());

        return CardModelAdapter.serialize(cardModels).toString();
    }

}