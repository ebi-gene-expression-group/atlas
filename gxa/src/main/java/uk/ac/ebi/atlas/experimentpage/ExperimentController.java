package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.servlet.http.HttpServletRequest;

public class ExperimentController extends ExperimentPageController{

    private final ExperimentTrader experimentTrader;
    private final ApplicationProperties applicationProperties;
    private final DataFileHub dataFileHub;
    private static final Gson gson = new Gson();

    public ExperimentController(ExperimentTrader experimentTrader,
                                ApplicationProperties applicationProperties,
                                DataFileHub dataFileHub){
        this.experimentTrader = experimentTrader;
        this.applicationProperties = applicationProperties;
        this.dataFileHub = dataFileHub;
    }

    @RequestMapping(value = {"/fexperiments/{experimentAccession}", "/fexperiments/{experimentAccession}/*"})
    public String showExperimentPage(Model model,
                                     HttpServletRequest request,
                                     @PathVariable String experimentAccession,
                                     @RequestParam(required = false) String accessKey) {


        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));
        model.addAttribute("atlasHost", applicationProperties.buildAtlasHostURL(request));

        model.addAttribute("content", gson.toJson(
                experimentPageContentForExperiment(
                        experimentTrader.getExperiment(experimentAccession, accessKey)
                )
        ));

        return "foundation-experiment-page";
    }

    JsonObject experimentPageContentForExperiment(Experiment experiment){
        JsonObject result = new JsonObject();

        // the client can't know that otherwise and it needs that!
        result.addProperty("experimentType", experiment.getType().name());

        JsonArray availableTabs = new JsonArray();
        // everything wants to have a heatmap
        availableTabs.add(customContentTab("heatmap", "Heatmap"));
        if(dataFileHub.getExperimentFiles(experiment.getAccession()).experimentDesign.exists()){
            availableTabs.add(customContentTab("experiment-design", "Experiment Design"));
        }
        // and so on ! :)

        /*
        Some of these will be generic - ones that just show a picture.
        Some will always be there and I'm not sure if it's not too complicated to set the order up here
        but I like the loose coupling and simplicity.
        */

        result.add("tabs", availableTabs);

        return result;
    }

    JsonObject customContentTab(String tabName, String displayName){
        JsonObject result = new JsonObject();
        result.addProperty("type", tabName);
        result.addProperty("displayName", displayName);
        result.add("props", new JsonObject());
        return result;
    }

}
