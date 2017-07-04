package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.summary.AssayGroupSummaryBuilder;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.SingleCellExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@Controller
public class SingleCellExperimentPageController extends HtmlExceptionHandlingController {

    private final ExperimentTrader experimentTrader;

    private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();


    @Inject
    public SingleCellExperimentPageController(SingleCellExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}")
    public String baselineExperimentData(@PathVariable String experimentAccession,
                                         @RequestParam(value = "geneId", required = false, defaultValue = "") String geneId,
                                         @RequestParam(value = "k", required = false, defaultValue = "") Integer K,
                                         @RequestParam(value = "clusterId", required = false, defaultValue = "") String clusterId,
                                         Model model) {

        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        model.addAllAttributes(experiment.getAttributes());

        String howManySamples = MessageFormat.format("{0} single cells",
                experiment.getExperimentDesign().getAllRunOrAssay().size());

        String updates = MessageFormat.format("Last updated: {0}",experiment.buildExperimentInfo().getLastUpdate());

        model.addAttribute("experimentAccession", experimentAccession);
        model.addAttribute("displayName", experiment.getDisplayName());
        model.addAttribute("messagesAboutCells", ImmutableList.of(howManySamples, updates));
        model.addAttribute("species", experiment.getSpecies().getName());

        model.addAttribute("resourcesVersion", env.getProperty("resources.version"));

        switch (experimentAccession.toUpperCase()) {
            case "E-MTAB-2865":
                model.addAttribute("datasetVersion", "32203_points");
                return "experiment-spatial";
            case "E-MTAB-4388":
                return "experiment-page";
//                return "experiment-reference-plot";
            case "E-MTAB-5061":
                return "experiment-tsne-plot";
            default:
                return "experiment-base";
        }
    }


    @RequestMapping(value = "/json/experiments/{experimentAccession}")
    @ResponseBody
    public String baselineExperimentData(@PathVariable String experimentAccession,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        return gson.toJson(experimentTrader.getPublicExperiment(experimentAccession).getAttributes());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_types")
    @ResponseBody
    public String baselineExperimentAssays(@PathVariable String experimentAccession,
                                          HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        return gson.toJson(experiment.getDataColumnDescriptors());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_types/{assayGroupId}")
    @ResponseBody
    public String baselineExperimentAssay(@PathVariable String experimentAccession,
                                          @PathVariable String assayGroupId,
                                         HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);

        return gson.toJson(new AssayGroupSummaryBuilder()
                .withExperimentDesign(experiment.getExperimentDesign())
                .forAssayGroup(experiment.getDataColumnDescriptor(assayGroupId))
                .build());

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/cell_ids/{cellId}")
    @ResponseBody
    public String baselineExperimentCell(@PathVariable String experimentAccession,
                                          @PathVariable String cellId,
                                          HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        return gson.toJson(experimentTrader.getPublicExperiment
                (experimentAccession).getExperimentDesign().getSampleCharacteristicsValues(cellId));

    }

}
