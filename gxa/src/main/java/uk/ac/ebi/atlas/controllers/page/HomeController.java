package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.*;
import uk.ac.ebi.atlas.species.SpeciesInfoListService;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
public class HomeController {

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final ExperimentInfoListService experimentInfoListService;
    private final SpeciesInfoListService speciesInfoListService;
    private Gson gson = new Gson();

    @Autowired
    private Environment env;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader, ExperimentTrader experimentTrader){
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        experimentInfoListService = new ExperimentInfoListService(experimentTrader,
                ImmutableSet.of(RNASEQ_MRNA_BASELINE, PROTEOMICS_BASELINE, RNASEQ_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL, MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL));
        speciesInfoListService = new SpeciesInfoListService(speciesPropertiesTrader, experimentInfoListService);
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
    public String getFoundationHomePage(Model model) {
        model.addAllAttributes(experimentInfoListService.getLatestExperimentsListAttributes());

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        model.addAttribute("speciesList", gson.toJson(speciesInfoListService.getBrowseBySpecies()));
        model.addAttribute("animalsList", gson.toJson(speciesInfoListService.getFilterByKingdom("animals")));
        model.addAttribute("plantsList", gson.toJson(speciesInfoListService.getFilterByKingdom("plants")));
        model.addAttribute("fungiList", gson.toJson(speciesInfoListService.getFilterByKingdom("fungi")));

        model.addAttribute("organismPath", ""); // Required by Spring form tag

        return "foundation-home";
    }

}
