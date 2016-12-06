package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;

@Controller
public class HomeController {

    private SpeciesPropertiesTrader speciesTrader;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesTrader) {
        this.speciesTrader = speciesTrader;
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        ImmutableMap.Builder<String, String> organismSelectBuilder = ImmutableMap.builder();
        organismSelectBuilder.put("", "Any");
        for (String organism : speciesTrader.getAllPretty()) {
            organismSelectBuilder.put(organism, organism);
        }
        model.addAttribute("organisms", organismSelectBuilder.build());
        model.addAttribute("organismPath", ""); // Required by Spring form tag
        return "home";
    }

    @RequestMapping(value = "/fhome")
    public String getFoundationHomePage() {
        return "foundation-home";
    }

}
