package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.species.services.PopularSpeciesService;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;

@Controller
public class HomeController extends HtmlExceptionHandlingController {

    private final LatestExperimentsService latestExperimentsService;

    @Inject
    public HomeController(LatestExperimentsDao latestExperimentsDao, ScxaExperimentTrader experimentTrader) {
        latestExperimentsService = new LatestExperimentsService(latestExperimentsDao, experimentTrader);
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        model.addAllAttributes(latestExperimentsService.fetchLatestExperimentsAttributes());
        return "home";
    }
}
