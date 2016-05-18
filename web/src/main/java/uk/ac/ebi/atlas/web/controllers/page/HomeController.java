
package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.trader.cache.OrganismsCache;

import javax.inject.Inject;
import java.util.ArrayList;

@Controller
@Scope("singleton")
public class HomeController {

    private OrganismsCache organismsCache;

    @Inject
    public HomeController(OrganismsCache organismsCache) {
        this.organismsCache = organismsCache;
    }

    @RequestMapping(value = "/home"
                   )
    public String getHomePage(Model model) {
        model.addAttribute("dummyPath", "");

        ArrayList<String> organismSelection =  new ArrayList<>();
        organismSelection.add("Any"); organismSelection.addAll(organismsCache.getOrganismsList());
        model.addAttribute("organisms", organismSelection);
        return "home";
    }
}
