package uk.ac.ebi.atlas.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingleCellHomeController {

    @RequestMapping(value = "/home")
    public String getHomePage() {
        return "home";
    }
}
