package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;

@Controller
public class ExperimentController extends HtmlExceptionHandlingController {
    private final ScxaExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();
    private final ExperimentPageContentService experimentPageContentService;
    
    @Inject
    public ExperimentController(ScxaExperimentTrader experimentTrader, DataFileHub dataFileHub, ExperimentPageContentService experimentPageContentService){
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
        this.experimentPageContentService = experimentPageContentService;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/**"},
                    produces = "text/html;charset=UTF-8")
    public String showExperimentPage(Model model,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(defaultValue = "") String accessKey) {

        Experiment<?> experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("content", gson.toJson(experimentPageContentForExperiment(experiment, accessKey)));

        return "experiment-page";
    }

    private JsonObject experimentPageContentForExperiment(final Experiment experiment, final String accessKey){
        JsonObject result = experimentPageContentService.getExperimentInformationAsJson(experiment, accessKey);

        JsonArray availableTabs = new JsonArray();

        availableTabs.add(
                customContentTab(
                        "t-sne-plot",
                        "Results",
                        experimentPageContentService.getTsnePlotDataAsJson()));

        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
            availableTabs.add(
                    customContentTab(
                            "experiment-design",
                            "Experiment Design",
                            experimentPageContentService.getExperimentDesignAsJson(experiment, accessKey))
            );
        }

        availableTabs.add(
                customContentTab(
                        "supplementary-information",
                        "Supplementary Information",
                        "sections",
                        supplementaryInformationTabs(experiment, accessKey))
        );

        availableTabs.add(
                customContentTab(
                        "resources",
                        "Downloads",
                        "data",
                        experimentPageContentService.getDownloadsAsJson(experiment.getAccession(), accessKey))
        );

        result.add("tabs", availableTabs);

        return result;
    }

    private JsonArray supplementaryInformationTabs(final Experiment experiment, final String accessKey) {
        JsonArray supplementaryInformationTabs = new JsonArray();
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.exists()){
                supplementaryInformationTabs.add(
                        customContentTab(
                                "static-table",
                                "Analysis Methods",
                                "data",
                                experimentPageContentService.getAnalysisMethodsAsJson(experiment.getAccession())));
            }

        supplementaryInformationTabs.add(
                customContentTab(
                        "resources",
                        "Resources",
                        "data",
                        experimentPageContentService.getResourcesAsJson(experiment.getAccession(), accessKey,  ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION)
                ));

        return supplementaryInformationTabs;
    }

    private JsonObject customContentTab(String tabType, String name, String onlyPropName, JsonElement value){
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
