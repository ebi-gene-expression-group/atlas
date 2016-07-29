
package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Controller
// if we make it singleton it gets initialized during deployment, that means deployment become slow
@Scope("request")
public class BaselineExperimentsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentsController.class);

    private ExperimentTrader experimentTrader;

    private SortedSetMultimap<String, String> experimentAccessionsBySpecies;

    private Map<String, String> experimentLinks = new HashMap<>();

    private Map<String, String> experimentDisplayNames = new HashMap<>();

    @Inject
    public BaselineExperimentsController(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping("/baseline/experiments")
    public String getBaselineExperimentsPage(Model model) {

        loadExperimentAccessionsBySpecies();

        model.addAttribute("experimentAccessionsBySpecies", experimentAccessionsBySpecies);
        model.addAttribute("experimentLinks", experimentLinks);
        model.addAttribute("experimentDisplayNames", experimentDisplayNames);

        model.addAttribute("mainTitle", "Baseline expression experiments ");

        return "baseline-experiments";
    }

    @PostConstruct
    private void loadExperimentAccessionsBySpecies() {

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {
            String displayName = null;
            try {
                displayName = experimentTrader.getPublicExperiment(experimentAccession).getDisplayName();
            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
                displayName = experimentAccession;
            }

            int numberOfAssays = ((BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession)).getExperimentRunAccessions().size();

            experimentDisplayNames.put(experimentAccession, displayName + " (" + numberOfAssays + " assays)");
        }

        Comparator<String> keyComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Services review: Alvis' edict for Homo sapiens experiments to always come up on top of baseline landing page
                if (o1.equals("Homo sapiens") && !o2.equals("Homo sapiens"))
                    return -1;
                else if (o2.equals("Homo sapiens") && !o1.equals("Homo sapiens"))
                    return 1;
                else
                    return o1.compareTo(o2);
            }
        };
        // experiments should be sorted by their display name, not accession
        Comparator<String> valueComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Services review: Alvis' edict for proteomics experiments to always come up at the bottom of
                // the list of experiments within each species
                if (o1.indexOf("-PROT-") != -1 && o2.indexOf("-PROT-") == -1)
                    return 1;
                else if (o2.indexOf("-PROT-") != -1 && o1.indexOf("-PROT-") == -1)
                    return -1;
                else
                    return experimentDisplayNames.get(o1).compareTo(experimentDisplayNames.get(o2));
            }
        };
        experimentAccessionsBySpecies = TreeMultimap.create(keyComparator, valueComparator);

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {

            try {
                BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);
                experimentAccessionsBySpecies.put(experiment.getSpeciesString(), experimentAccession);
                experimentLinks.put(experimentAccession + experiment.getSpeciesString(), "");
            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
            }

        }

    }


}