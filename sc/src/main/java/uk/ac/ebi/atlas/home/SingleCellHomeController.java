package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
public class SingleCellHomeController {

    ExperimentInfoListService experimentInfoListService;

    @Inject
    public SingleCellHomeController(ExperimentTrader experimentTrader){
        experimentInfoListService = new ExperimentInfoListService(experimentTrader,
                ImmutableSet.of(ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE));
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        model.addAttribute("experimentCount", experimentInfoListService.getCount());
        model.addAttribute("latestExperiments", experimentInfoListService.getLatest());
        return "home";
    }
}
