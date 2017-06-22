package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

@Controller
public class SearchController extends HtmlExceptionHandlingController {

    @RequestMapping(value = "/search", produces = "text/html;charset=UTF-8")
    public String search() {
        return "search-results";
    }
}
