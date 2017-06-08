package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.ExperimentInfoListService;
import uk.ac.ebi.atlas.species.services.SpeciesInfoListService;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.Random;

import static uk.ac.ebi.atlas.model.experiment.ExperimentType.*;

@Controller
public class HomeController extends HtmlExceptionHandlingController {

    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";
    private static final Random RANDOM = new Random();

    private static final Gson gson = new Gson();

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final ExperimentInfoListService experimentInfoListService;
    private final SpeciesInfoListService speciesInfoListService;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader, ExperimentTrader experimentTrader){
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        experimentInfoListService = new ExperimentInfoListService(experimentTrader,
                ImmutableSet.of(RNASEQ_MRNA_BASELINE, PROTEOMICS_BASELINE, RNASEQ_MRNA_DIFFERENTIAL,
                        MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL, MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL));
        speciesInfoListService = new SpeciesInfoListService(speciesPropertiesTrader, experimentInfoListService);
    }

    @RequestMapping(value = "/home")
    public String getHome(Model model) {
        ImmutableMap.Builder<String, String> topSixSelectBuilder = ImmutableMap.builder();
        for (String speciesName: speciesInfoListService.getTopSixSpecies()) {
            topSixSelectBuilder.put(speciesName, StringUtils.capitalize(speciesName));
        }
        model.addAttribute("topSixByExperimentCount", topSixSelectBuilder.build());

        model.addAttribute("separator", RANDOM.nextDouble() < 0.001 ? BEST_SEPARATOR : NORMAL_SEPARATOR);

        ImmutableMap.Builder<String, String> organismSelectBuilder = ImmutableMap.builder();
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            organismSelectBuilder.put(speciesProperties.referenceName(), StringUtils.capitalize(speciesProperties.referenceName()));
        }

        model.addAttribute("organisms", organismSelectBuilder.build());
        model.addAttribute("organismPath", ""); // Required by Spring form tag

        model.addAllAttributes(experimentInfoListService.getLatestExperimentsListAttributes());

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        model.addAttribute("speciesList", gson.toJson(speciesInfoListService.getTopSixSpeciesByExperimentCount()));
        model.addAttribute("animalsList", gson.toJson(speciesInfoListService.getFilterByKingdom("animals")));
        model.addAttribute("plantsList", gson.toJson(speciesInfoListService.getFilterByKingdom("plants")));
        model.addAttribute("fungiList", gson.toJson(speciesInfoListService.getFilterByKingdom("fungi")));

        model.addAttribute("organismPath", ""); // Required by Spring form tag

        return "foundation-home";
    }

}
