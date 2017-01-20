package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.*;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
public class HomeController {

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final ExperimentInfoListService experimentInfoListService;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader,ExperimentTrader experimentTrader){
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        experimentInfoListService = new ExperimentInfoListService(experimentTrader,
                ImmutableSet.of(RNASEQ_MRNA_BASELINE, PROTEOMICS_BASELINE, RNASEQ_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL, MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL));
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
        return "foundation-home";
    }

}
