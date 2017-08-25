package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.util.List;

@Controller
public class SingleCellExperimentController extends HtmlExceptionHandlingController {

    private final ExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();
    
    @Inject
    public SingleCellExperimentController(ExperimentTrader experimentTrader, DataFileHub dataFileHub) {
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
    }

    @RequestMapping(value = {"/experiments/{experimentAccession}", "/experiments/{experimentAccession}/**"})
    public String showExperimentPage(Model model,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(defaultValue = "") String accessKey) {
        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        model.addAllAttributes(experiment.getAttributes());

        model.addAttribute("content", gson.toJson(experimentPageContentForExperiment(experiment, accessKey)));

        return "experiment-page";
    }

    private JsonObject experimentPageContentForExperiment(final Experiment experiment, final String accessKey){
        JsonObject result = new JsonObject();

        // the client can't know that otherwise and it needs that!
        result.addProperty("experimentAccession", experiment.getAccession());
        result.addProperty("experimentType", experiment.getType().name());
        result.addProperty("accessKey", accessKey);
        result.addProperty("species", experiment.getSpecies().getReferenceName());

        JsonArray availableTabs = new JsonArray();
        // everything wants to have a t-SNE plot
        availableTabs.add(tSneTab());

//        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
//            availableTabs.add(
//                    experimentDesignTab(new ExperimentDesignTable(experiment).asJson(),
//                            ExperimentDesignFile.makeUrl(experiment.getAccession(), accessKey))
//            );
//        }

        availableTabs.add(
                customContentTab("multipart", "Supplementary Information", "sections", supplementaryInformationTabs(experiment, accessKey))
        );

        availableTabs.add(
                customContentTab("resources", "Downloads", "url",
                        new JsonPrimitive(ExternallyAvailableContentController.listResourcesUrl(
                                experiment.getAccession(), accessKey, ExternallyAvailableContent.ContentType.DATA)))
        );

        result.add("tabs", availableTabs);

        return result;
    }

    private JsonArray supplementaryInformationTabs(final Experiment experiment, final String accessKey) {
        JsonArray supplementaryInformationTabs = new JsonArray();
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.exists()){
            supplementaryInformationTabs.add(customContentTab("static-table", "Analysis Methods", "data",
                    formatTable(dataFileHub.getExperimentFiles(experiment.getAccession()).analysisMethods.get().readAll()
                            )
            ));
        }
        supplementaryInformationTabs.add(
                customContentTab("resources", "Resources", "url",
                        new JsonPrimitive(ExternallyAvailableContentController.listResourcesUrl(
                                experiment.getAccession(), accessKey, ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION)))
        );

        return supplementaryInformationTabs;
    }


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

    private JsonObject customContentTab(String tabType, String name, JsonObject props){
        JsonObject result = new JsonObject();
        result.addProperty("type", tabType);
        result.addProperty("name", name);
        result.add("props", props);
        return result;
    }

    private JsonObject tSneTab(){
        JsonObject props = new JsonObject();
        return customContentTab("tsneplot", "Results", props);
    }

//    private JsonObject experimentDesignTab(JsonObject table, String downloadUrl){
//        JsonObject props = new JsonObject();
//        props.add("table", table);
//        props.addProperty("downloadUrl", downloadUrl);
//        return customContentTab("experiment-design", "Experiment Design", props);
//    }

}
