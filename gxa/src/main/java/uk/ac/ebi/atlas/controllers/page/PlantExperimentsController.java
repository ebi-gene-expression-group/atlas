package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

// Ideally this shouldn’t be scoped to request but, if singleton, on application startup all experiments are going to
// be cached making init times way slower than necessary; especially in development. In production, experiments are
// already cached; the added cost of creating the bean for each HTTP request is a trade-off for a page that doesn’t get
// a lot of traffic.
@Controller
@Scope("request")
public class PlantExperimentsController extends HtmlExceptionHandlingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantExperimentsController.class);

    private ExpressionAtlasExperimentTrader experimentTrader;

    private Integer numberOfPlantExperiments;

    private SortedSetMultimap<String, String> baselineExperimentAccessionsBySpecies;
    private SortedMap<String, Integer> numDifferentialExperimentsBySpecies;

    private Map<String, String> experimentLinks = new HashMap<>();
    private Map<String, String> experimentDisplayNames = new HashMap<>();

    @Inject
    public PlantExperimentsController(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/plant/experiments",produces = "text/html;charset=UTF-8")
    public String getPlantExperimentsPage(Model model) {

        loadExperimentAccessionsBySpecies();

        model.addAttribute("baselineExperimentAccessionsBySpecies", baselineExperimentAccessionsBySpecies);
        model.addAttribute("numDifferentialExperimentsBySpecies", numDifferentialExperimentsBySpecies);
        model.addAttribute("experimentLinks", experimentLinks);
        model.addAttribute("experimentDisplayNames", experimentDisplayNames);
        model.addAttribute("numberOfPlantExperiments", numberOfPlantExperiments);

        model.addAttribute("mainTitle", "Plant experiments ");

        return "plants-landing-page";
    }

    @PostConstruct
    private void loadExperimentAccessionsBySpecies() {

        // Get number of all public plant experiments in Atlas
        numberOfPlantExperiments = 0;

        Comparator<String> keyComparator = String::compareTo;
        // experiments should be sorted by their display name, not accession
        Comparator<String> valueComparator = Comparator.comparing(o -> experimentDisplayNames.get(o));
        baselineExperimentAccessionsBySpecies = TreeMultimap.create(keyComparator, valueComparator);

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {
            try {
                Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
                int numberOfAssays = experiment.getAnalysedAssays().size();

                experimentDisplayNames.put(experimentAccession, experiment.getDisplayName() + " (" + numberOfAssays + " assays)");

                Species species = experiment.getSpecies();
                if (species.isPlant()) {
                    baselineExperimentAccessionsBySpecies.put(species.getName(), experimentAccession);
                    experimentLinks.put(experimentAccession + species.getName(), "");
                    numberOfPlantExperiments++;
                }

            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
            }
        }

        numDifferentialExperimentsBySpecies = new TreeMap<>();
        long start = System.currentTimeMillis();
        populateExperimentAccessionToSpecies(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
        populateExperimentAccessionToSpecies(ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL);
        populateExperimentAccessionToSpecies(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL);
        populateExperimentAccessionToSpecies(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);
        LOGGER.info("Differential experiments took: {} ms", System.currentTimeMillis() - start);
    }


    /**
     * Populates numDifferentialExperimentsBySpecies and numberOfPlantExperiments for a given experimentType
     * This is a part of a work-around until https://www.pivotaltracker.com/story/show/88885788 gets implemented.
     */
    private void populateExperimentAccessionToSpecies(ExperimentType experimentType) {
        for (String experimentAccession : experimentTrader.getPublicExperimentAccessions(experimentType)) {
            try {
                Species species = experimentTrader.getExperimentFromCache(experimentAccession, experimentType).getSpecies();

                if (species.isPlant()) {
                    Integer numSoFar = numDifferentialExperimentsBySpecies.get(species.getName());
                    numDifferentialExperimentsBySpecies.put(species.getName(), numSoFar == null ? 1: ++numSoFar);
                    numberOfPlantExperiments++;
                }

            } catch (RuntimeException | ExecutionException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
