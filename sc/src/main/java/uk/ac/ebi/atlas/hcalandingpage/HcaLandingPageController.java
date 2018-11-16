package uk.ac.ebi.atlas.hcalandingpage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HcaLandingPageController {

    @GetMapping(value = "/alpha/hca", produces = "text/html;charset=UTF-8")
    public String getHCAlandingpage() {
        return "hca-landing-page";
    }
}

