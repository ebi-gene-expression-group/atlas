package uk.ac.ebi.atlas.controllers.page;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// Ideally this shouldn’t be scoped to request but, if singleton, on application startup all experiments are going to
// be cached making init times way slower than necessary; especially in development. In production, experiments are
// already cached; the added cost of creating the bean for each HTTP request is a trade-off for a page that doesn’t get
// a lot of traffic.
@Controller
@Scope("request")
public class BaselineExperimentsController extends HtmlExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentsController.class);

    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    public BaselineExperimentsController(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/baseline/experiments",produces = "text/html;charset=UTF-8")
    public String getBaselineExperimentsPage(Model model) {

        Map<String, String> experimentDisplayNames = new HashMap<>();

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {
            String displayName;
            try {
                displayName = experimentTrader.getPublicExperiment(experimentAccession).getDisplayName();
            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline
                // because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
                displayName = experimentAccession;
            }

            int numberOfAssays = experimentTrader.getPublicExperiment(experimentAccession).getAnalysedAssays().size();

            experimentDisplayNames.put(experimentAccession, displayName + " (" + numberOfAssays + " assays)");
        }

        Comparator<String> keyComparator = (o1, o2) -> {
            if (o1.equals("Homo sapiens") && !o2.equals("Homo sapiens"))
                return -1;
            else if (o2.equals("Homo sapiens") && !o1.equals("Homo sapiens"))
                return 1;
            else
                return o1.compareTo(o2);
        };
        // experiments should be sorted by their display name, not accession
        Comparator<String> valueComparator = (o1, o2) -> {
            // Services review: Alvis' edict for proteomics experiments to always come up at the bottom of
            // the list of experiments within each species
            if (o1.contains("-PROT-") && !o2.contains("-PROT-"))
                return 1;
            else if (o2.contains("-PROT-") && !o1.contains("-PROT-"))
                return -1;
            else
                return experimentDisplayNames.get(o1).compareTo(experimentDisplayNames.get(o2));
        };
        SortedSetMultimap<String, String> experimentAccessionsBySpecies =
                TreeMultimap.create(keyComparator, valueComparator);

        Map<String, String> experimentLinks = new HashMap<>();

        for (String experimentAccession : experimentTrader.getAllBaselineExperimentAccessions()) {

            try {
                BaselineExperiment experiment =
                        (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);
                experimentAccessionsBySpecies.put(experiment.getSpecies().getName(), experimentAccession);
                experimentLinks.put(experimentAccession + experiment.getSpecies().getName(), "");
            } catch (RuntimeException e) {
                // we don't want the entire application to crash just because one condensedSdrf file may be offline
                // because a curator is modifying it
                LOGGER.error(e.getMessage(), e);
            }

        }

        model.addAttribute("experimentAccessionsBySpecies", experimentAccessionsBySpecies);
        model.addAttribute("experimentLinks", experimentLinks);
        model.addAttribute("experimentDisplayNames", experimentDisplayNames);

        model.addAttribute("mainTitle", "Baseline expression experiments ");

        return "baseline-landing-page";
    }

}
