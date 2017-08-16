package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.LatestExperimentsDao;
import uk.ac.ebi.atlas.experiments.LatestExperimentsService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.SingleCellAtlasExperimentTrader;

import javax.inject.Inject;

@Controller
public class SingleCellHomeController extends HtmlExceptionHandlingController {

    private final LatestExperimentsService latestExperimentsService;

    @Inject
    public SingleCellHomeController(LatestExperimentsDao latestExperimentsDao,
                                    SingleCellAtlasExperimentTrader experimentTrader) {
        latestExperimentsService =
                new LatestExperimentsService(
                        latestExperimentsDao, experimentTrader,
                        ImmutableSet.of(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE));
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        model.addAllAttributes(latestExperimentsService.fetchLatestExperimentsAttributes());
        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));
        return "home";
    }
}
