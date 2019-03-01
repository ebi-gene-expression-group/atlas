package uk.ac.ebi.atlas.hcalandingpage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.ExperimentSearchService;

import static uk.ac.ebi.atlas.hcalandingpage.JsonHcaLandingPageController.HCA_ACCESSION_PATTERN;

@Controller
public class HcaLandingPageController extends HtmlExceptionHandlingController {
    private ExperimentSearchService experimentSearchService;

    public HcaLandingPageController(ExperimentSearchService experimentSearchService) {
        this.experimentSearchService = experimentSearchService;
    }

    @GetMapping(value = "/alpha/hca", produces = "text/html;charset=UTF-8")
    public String getHCAlandingpage(Model model) {
        model.addAttribute(
                "hcaExperimentsCount",
                experimentSearchService.searchPublicExperimentsByAccession(HCA_ACCESSION_PATTERN).size());
        model.addAttribute(
                "humanExperimentsCount",
                experimentSearchService.searchPublicExperimentsBySpecies("Homo sapiens").size());

        return "hca-landing-page";
    }
}

