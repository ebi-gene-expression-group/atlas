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
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.LatestExperimentsDao;
import uk.ac.ebi.atlas.experiments.LatestExperimentsService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.services.PopularSpeciesInfo;
import uk.ac.ebi.atlas.species.services.PopularSpeciesService;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    protected Environment env;

    private static final int FEATURED_SPECIES = 6;
    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";
    private static final Random RANDOM = new Random();

    private static final Gson gson = new Gson();

    private final SpeciesPropertiesTrader speciesPropertiesTrader;
    private final PopularSpeciesService popularSpeciesService;
    private final LatestExperimentsService latestExperimentsService;

    @Inject
    public HomeController(SpeciesPropertiesTrader speciesPropertiesTrader,
                          PopularSpeciesService popularSpeciesService,
                          LatestExperimentsDao latestExperimentsDao,
                          ExpressionAtlasExperimentTrader experimentTrader) {
        this.speciesPropertiesTrader = speciesPropertiesTrader;
        this.popularSpeciesService = popularSpeciesService;
        this.latestExperimentsService =
                new LatestExperimentsService(
                        latestExperimentsDao, experimentTrader,
                        ImmutableSet.of(
                                ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                                ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                                ExperimentType.PROTEOMICS_BASELINE,
                                ExperimentType.RNASEQ_MRNA_BASELINE
        ));
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String getHome(Model model) {
        ImmutableMap.Builder<String, String> topSixSelectBuilder = ImmutableMap.builder();
        for (PopularSpeciesInfo popularSpeciesInfo: popularSpeciesService.getPopularSpecies(FEATURED_SPECIES)) {
            topSixSelectBuilder.put(popularSpeciesInfo.species(), StringUtils.capitalize(popularSpeciesInfo.species()));
        }
        model.addAttribute("topSixByExperimentCount", topSixSelectBuilder.build());

        model.addAttribute("separator", RANDOM.nextDouble() < 0.001 ? BEST_SEPARATOR : NORMAL_SEPARATOR);

        ImmutableMap.Builder<String, String> organismSelectBuilder = ImmutableMap.builder();
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            organismSelectBuilder.put(speciesProperties.referenceName(), StringUtils.capitalize(speciesProperties.referenceName()));
        }

        model.addAttribute("organisms", organismSelectBuilder.build());
        model.addAttribute("organismPath", ""); // Required by Spring form tag

        model.addAllAttributes(latestExperimentsService.fetchLatestExperimentsAttributes());



        model.addAttribute("speciesList", gson.toJson(popularSpeciesService.getPopularSpecies(FEATURED_SPECIES)));
        model.addAttribute("animalsList", gson.toJson(popularSpeciesService.getPopularSpecies("animals", FEATURED_SPECIES)));
        model.addAttribute("plantsList", gson.toJson(popularSpeciesService.getPopularSpecies("plants", FEATURED_SPECIES)));
        model.addAttribute("fungiList", gson.toJson(popularSpeciesService.getPopularSpecies("fungi", FEATURED_SPECIES)));

        model.addAttribute("organismPath", ""); // Required by Spring form tag

        return "home";
    }

}
