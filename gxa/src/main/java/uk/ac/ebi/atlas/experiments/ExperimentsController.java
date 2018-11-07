package uk.ac.ebi.atlas.experiments;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

@Controller
public class ExperimentsController extends HtmlExceptionHandlingController {

    @RequestMapping(value = "/experiments", produces = "text/html;charset=UTF-8")
    public String getExperimentsListParameters(
            @RequestParam(value = "experimentType", required = false) String experimentType,
            @RequestParam(value = "kingdom", required = false) String kingdom,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "experimentSet", required = false) String experimentSet,
            Model model) {

        model.addAttribute("experimentType", experimentType);
        model.addAttribute("kingdom", kingdom);
        model.addAttribute("species", species);
        model.addAttribute("experimentSet", experimentSet);

        model.addAttribute("mainTitle", "Experiments ");

        return "experiments";
    }

}
