package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesignTable;
import uk.ac.ebi.atlas.model.experiment.baseline.Cell;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Controller
public class ExperimentController extends HtmlExceptionHandlingController {
    private final ScxaExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;
    private final ExperimentPageContentService experimentPageContentService;
    private final ExperimentAttributesService experimentAttributesService;

    @Inject
    public ExperimentController(ScxaExperimentTrader experimentTrader, DataFileHub dataFileHub,
                                ExperimentPageContentService experimentPageContentService,
                                ExperimentAttributesService experimentAttributesService) {
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
        this.experimentPageContentService = experimentPageContentService;
        this.experimentAttributesService = experimentAttributesService;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/**"},
                    produces = "text/html;charset=UTF-8")
    public String showExperimentPage(Model model,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(defaultValue = "") String accessKey) {
        Experiment<Cell> experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        model.addAllAttributes(experimentAttributesService.getAttributes(experiment));

        model.addAttribute("content", GSON.toJson(experimentPageContentForExperiment(experiment, accessKey)));

        return "experiment-page";
    }

    private JsonObject experimentPageContentForExperiment(final Experiment<Cell> experiment, final String accessKey) {
        JsonObject result = new JsonObject();

        result.addProperty("experimentAccession", experiment.getAccession());
        result.addProperty("accessKey", accessKey);
        result.addProperty("species", experiment.getSpecies().getReferenceName());
        result.addProperty("disclaimer", experiment.getDisclaimer());

        JsonArray availableTabs = new JsonArray();

        availableTabs.add(
                customContentTab(
                        "results",
                        "Results",
                        experimentPageContentService.getTsnePlotData(experiment.getAccession())));

        if (dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()) {
            availableTabs.add(
                    customContentTab(
                            "experiment-design",
                            "Experiment Design",
                            experimentPageContentService.getExperimentDesign(
                                    experiment.getAccession(),
                                    new ExperimentDesignTable(experiment).asJson(),
                                    accessKey))
            );
        }

        availableTabs.add(
                customContentTab(
                        "supplementary-information",
                        "Supplementary Information",
                        "sections",
                        supplementaryInformationTabs(experiment))
        );

        availableTabs.add(
                customContentTab(
                        "resources",
                        "Downloads",
                        "data",
                        experimentPageContentService.getDownloads(experiment.getAccession(), accessKey))
        );

        result.add("tabs", availableTabs);

        return result;
    }

    private JsonArray supplementaryInformationTabs(final Experiment experiment) {
        JsonArray supplementaryInformationTabs = new JsonArray();
        if (dataFileHub.getSingleCellExperimentFiles(experiment.getAccession()).softwareUsed.exists()) {
                supplementaryInformationTabs.add(
                        customContentTab(
                                "static-table",
                                "Analysis Methods",
                                "data",
                                experimentPageContentService.getAnalysisMethods(experiment.getAccession())));
            }

        return supplementaryInformationTabs;
    }

    private JsonObject customContentTab(String tabType, String name, String onlyPropName, JsonElement value) {
        JsonObject props =  new JsonObject();
        props.add(onlyPropName, value);
        return customContentTab(tabType, name, props);
    }

    private JsonObject customContentTab(String tabType, String name, JsonObject props) {
        JsonObject result = new JsonObject();
        result.addProperty("type", tabType);
        result.addProperty("name", name);
        result.add("props", props);
        return result;
    }
}
