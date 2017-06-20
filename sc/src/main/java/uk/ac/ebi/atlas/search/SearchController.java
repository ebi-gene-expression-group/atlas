package uk.ac.ebi.atlas.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

    @RequestMapping(value = "/search", produces = "text/html;charset=UTF-8")
    public String search() {
        return "search-results";
    }
}
