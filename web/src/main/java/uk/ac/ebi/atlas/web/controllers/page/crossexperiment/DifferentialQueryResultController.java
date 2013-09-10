package uk.ac.ebi.atlas.web.controllers.page.crossexperiment;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope("request")
public class DifferentialQueryResultController {

    @RequestMapping(value = "/query")
    public String showResultPage(@RequestParam (required = false) String condition, Model model) {

        return "bioEntities";
    }
}
