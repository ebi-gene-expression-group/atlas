package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.species.SpeciesPropertiesTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Random;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Controller
public class HomeController {
    @Autowired
    protected Environment env;

    private static final int FEATURED_SPECIES = 6;
    private static final String NORMAL_SEPARATOR = "━━━━━━━━━━━━━━━━━";
    private static final String BEST_SEPARATOR = "(╯°□°）╯︵ ┻━┻";
    private static final double EASTER_EGG_PROBABILITY = 0.001;
    private static final Random RANDOM = new Random();

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

    @RequestMapping(value = "/home", produces = "text/html;charset=UTF-8")
    public String getHome(Model model) {
        ImmutableMap.Builder<String, String> topSixSelectBuilder = ImmutableMap.builder();
        for (PopularSpeciesInfo popularSpeciesInfo: popularSpeciesService.getPopularSpecies(FEATURED_SPECIES)) {
            topSixSelectBuilder.put(
                    popularSpeciesInfo.species(), StringUtils.capitalize(popularSpeciesInfo.species()));
        }
        model.addAttribute("topSixByExperimentCount", topSixSelectBuilder.build());

        model.addAttribute(
                "separator", RANDOM.nextDouble() < EASTER_EGG_PROBABILITY ? BEST_SEPARATOR : NORMAL_SEPARATOR);

        ImmutableMap.Builder<String, String> speciesSelectBuilder = ImmutableMap.builder();
        for (SpeciesProperties speciesProperties : speciesPropertiesTrader.getAll()) {
            speciesSelectBuilder.put(
                    speciesProperties.referenceName(), StringUtils.capitalize(speciesProperties.referenceName()));
        }

        model.addAttribute("species", speciesSelectBuilder.build());
        model.addAttribute("speciesPath", ""); // Required by Spring form tag

        model.addAllAttributes(latestExperimentsService.fetchLatestExperimentsAttributes());

        model.addAttribute(
                "speciesList", GSON.toJson(popularSpeciesService.getPopularSpecies(FEATURED_SPECIES)));
        model.addAttribute(
                "animalsList", GSON.toJson(popularSpeciesService.getPopularSpecies("animals", FEATURED_SPECIES)));
        model.addAttribute(
                "plantsList", GSON.toJson(popularSpeciesService.getPopularSpecies("plants", FEATURED_SPECIES)));
        model.addAttribute(
                "fungiList", GSON.toJson(popularSpeciesService.getPopularSpecies("fungi", FEATURED_SPECIES)));

        model.addAttribute("speciesPath", ""); // Required by Spring form tag

        return "home";
    }
}
