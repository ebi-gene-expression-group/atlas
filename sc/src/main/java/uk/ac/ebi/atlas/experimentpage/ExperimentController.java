package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.download.ExperimentFileLocationService;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ScxaExperimentTrader;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
public class ExperimentController extends HtmlExceptionHandlingController {
    private final ScxaExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();
    private final ExperimentFileLocationService experimentFileLocationService;
    private final SingleCellContentService singleCellContentService;
    private final ExperimentPageContentService experimentPageContentService;
    
    @Inject
    public ExperimentController(ScxaExperimentTrader experimentTrader, DataFileHub dataFileHub, ExperimentFileLocationService experimentFileLocationService, SingleCellContentService singleCellContentService, ExperimentPageContentService experimentPageContentService){
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
        this.experimentFileLocationService = experimentFileLocationService;
        this.singleCellContentService = singleCellContentService;
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
        JsonObject result = experimentPageContentService.getExperimentInformation(experiment, accessKey);

        JsonArray availableTabs = new JsonArray();

        availableTabs.add(
                customContentTab(
                        "t-sne-plot",
                        "Results",
                        experimentPageContentService.getTsnePlotModel()));

        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
            availableTabs.add(
                    customContentTab(
                            "experiment-design",
                            "Experiment Design",
                            experimentPageContentService.getExperimentDesignModel(experiment, accessKey))
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
                        experimentPageContentService.getDownloadsModel(experiment.getAccession(), accessKey))
        );

        result.add("tabs", availableTabs);

        return result;
    }

    // TODO: Move this to new ExperimentPageContentService class
    private JsonArray supplementaryInformationTabs(final Experiment experiment, final String accessKey) {
        JsonArray supplementaryInformationTabs = new JsonArray();
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.exists()){
            try (TsvStreamer tsvStreamer =
                         dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get()) {
                supplementaryInformationTabs.add(
                        customContentTab(
                                "static-table",
                                "Analysis Methods",
                                "data",
                                formatTable(tsvStreamer.get().collect(Collectors.toList()))));
            }
        }

        supplementaryInformationTabs.add(
                customContentTab(
                        "resources",
                        "Resources",
                        "data",
                        resourcesAsJson(experiment.getAccession(), accessKey,  ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION)
                ));

        return supplementaryInformationTabs;
    }

    private JsonArray resourcesAsJson(String experimentAccesssion, String accessKey, ExternallyAvailableContent.ContentType contentType) {
        JsonArray result = new JsonArray();

        List<ExternallyAvailableContent> contents = singleCellContentService.list(experimentAccesssion, accessKey, contentType);
        for(ExternallyAvailableContent content : contents){
            result.add(contentAsJson(content, experimentAccesssion, accessKey));
        }

        return result;
    }

    // TODO: Move this to ExternallyAvailableContent class as toJson() method
    private JsonObject contentAsJson(ExternallyAvailableContent content, String accession, String accessKey){
        JsonObject result = content.description.asJson();
        if("redirect".equals(content.uri.getScheme())){
            try {
                result.addProperty("url", new URL(content.uri.getSchemeSpecificPart()).toExternalForm());

            } catch (MalformedURLException e) {
                result.addProperty("url",
                        MessageFormat.format("{0}{1}",
                                content.uri.getSchemeSpecificPart(),
                                isNotEmpty(accessKey) ? "?accessKey="+accessKey : "")
                );
            }

        } else {
            result.addProperty("url",
                    MessageFormat.format("experiments-content/{0}/resources/{1}{2}",
                            accession, content.uri.toString(), isNotEmpty(accessKey)? "?accessKey="+accessKey : ""
                    ));
        }
        return result;
    }

    // TODO: Move to a new JSON Utils class
    private JsonArray formatTable(List<String []> rows){

        JsonArray result = new JsonArray();
        for (String[] row : rows) {
            //skip empty rows and other unexpected input
            if (row.length == 2) {
                result.add(twoElementArray(row[0], row[1]));
            }
        }

        return result;
    }

    // TODO: Move to a new JSON Utils class
    private JsonArray twoElementArray(String x, String y){
        JsonArray result = new JsonArray();
        result.add(new JsonPrimitive(x));
        result.add(new JsonPrimitive(y));
        return result;
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
