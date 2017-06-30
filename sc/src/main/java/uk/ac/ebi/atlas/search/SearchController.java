package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

@Controller
public class SearchController extends HtmlExceptionHandlingController {

    @RequestMapping(value = "/search", produces = "text/html;charset=UTF-8")
    public String search(@RequestParam(value = "geneId", required = false, defaultValue = "") String geneId,
                         Model model) {
        model.addAttribute("geneId", geneId);
        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));
        return "search-results";
    }

}
