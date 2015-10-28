package uk.ac.ebi.atlas.experiments;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 24/03/15.
 */
@Controller
@Scope("request")
public class ExperimentsController {

    @RequestMapping(value = "/experiments", method = RequestMethod.GET, produces = "application/json")
    public String getExperimentsListParameters(
            @RequestParam(value = "experimentType", required = false) String experimentType,
            @RequestParam(value = "kingdom", required = false) String kingdom,
            @RequestParam(value = "organism", required = false) String organism,
            Model model) {

        model.addAttribute("experimentType", experimentType);
        model.addAttribute("kingdom", kingdom);
        model.addAttribute("organism", organism);

        model.addAttribute("mainTitle", "Experiments ");

        return "experiments";
    }

}
