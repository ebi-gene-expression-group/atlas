package uk.ac.ebi.atlas.experimentpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPageController {
    @RequestMapping(value = "/hca", produces = "text/html;charset=UTF-8")
    public String getHCAlandingpage() {
        return "hca-landing-page";
    }
}