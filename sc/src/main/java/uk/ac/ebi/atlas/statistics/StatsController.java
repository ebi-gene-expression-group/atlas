package uk.ac.ebi.atlas.statistics;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

import javax.inject.Inject;

@ControllerAdvice
public class StatsController extends HtmlExceptionHandlingController {

    private StatsService statsService;

    @Inject
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @ModelAttribute
    public void addExperimentStats(Model model) {
        model.addAllAttributes(statsService.getStatistics());
    }
}
