package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;

import javax.inject.Inject;

@Controller
public class HomeController {

    private SpeciesPropertiesTrader speciesPropertiesTrader;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
    }

    @RequestMapping(value = "/home")
    public String getHomePage(Model model) {
        ImmutableMap.Builder<String, String> organismSelectBuilder = ImmutableMap.builder();
        organismSelectBuilder.put("", "Any");
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            organismSelectBuilder.put(speciesProperties.referenceName(), StringUtils.capitalize(speciesProperties.referenceName()));
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
