
package uk.ac.ebi.atlas.controllers.page;

import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;


@Controller
// if we make it singleton it gets initialized during deployment, that means deployment becomes slow
@Scope("request")
public class PlantExperimentsController {
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

    @RequestMapping("/plant/experiments")
    public String getBaselineExperimentsPage(Model model) {

        loadExperimentAccessionsBySpecies();

        model.addAttribute("baselineExperimentAccessionsBySpecies", baselineExperimentAccessionsBySpecies);
        model.addAttribute("numDifferentialExperimentsBySpecies", numDifferentialExperimentsBySpecies);
        model.addAttribute("experimentLinks", experimentLinks);
        model.addAttribute("experimentDisplayNames", experimentDisplayNames);
        model.addAttribute("numberOfPlantExperiments", numberOfPlantExperiments);

        model.addAttribute("mainTitle", "Plant experiments ");

        return "plant-experiments";
    }

    @PostConstruct
    private void loadExperimentAccessionsBySpecies() {

        // Get number of all public plant experiments in Atlas
        numberOfPlantExperiments = 0;

        Comparator<String> keyComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        // experiments should be sorted by their display name, not accession
        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return experimentDisplayNames.get(o1).compareTo(experimentDisplayNames.get(o2));
            }
        };
        baselineExperimentAccessionsBySpecies = TreeMultimap.create(keyComparator, valueComparator);

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {
            try {
                int numberOfAssays = 0;
                Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
                if (experiment.getType() == ExperimentType.RNASEQ_MRNA_BASELINE || experiment.getType() == ExperimentType.PROTEOMICS_BASELINE) {
                    numberOfAssays = ((BaselineExperiment) experiment).getExperimentRunAccessions().size();
                }
                else if (experiment.getType() == ExperimentType.MICROARRAY_ANY || experiment.getType() == ExperimentType.RNASEQ_MRNA_DIFFERENTIAL) {
                    numberOfAssays = ((DifferentialExperiment) experiment).getAssayAccessions().size();
                }
                experimentDisplayNames.put(experimentAccession, experiment.getDisplayName() + " (" + numberOfAssays + " assays)");

                Species species = experiment.getSpecies();
                if (species.isPlant()) {
                    baselineExperimentAccessionsBySpecies.put(species.originalName, experimentAccession);
                    experimentLinks.put(experimentAccession + species.originalName, "");
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
                    Integer numSoFar = numDifferentialExperimentsBySpecies.get(species.originalName);
                    numDifferentialExperimentsBySpecies.put(species.originalName,numSoFar == null ? 1: ++numSoFar);
                    numberOfPlantExperiments++;
                }

            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}