
package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.DifferentialDesignRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class ExperimentDesignPageRequestHandler{

    private final ExperimentTrader experimentTrader;

    @Inject
    public ExperimentDesignPageRequestHandler(ExperimentTrader experimentTrader){
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public String showRnaSeqExperimentDesign(@PathVariable String experimentAccession,
                                             @RequestParam(value = "accessKey",required = false) String accessKey,
                                             @ModelAttribute("preferences")@Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        return handleRequest(experimentAccession,model, request, accessKey, preferences.getSelectedContrast());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/experiments/{experimentAccession}/experiment-design", params = {"type=MICROARRAY_ANY"})
    public String showMicroarrayExperimentDesign(@PathVariable String experimentAccession,
                                                 @RequestParam(value = "accessKey",required = false) String accessKey,
                                                 @ModelAttribute("preferences") @Valid DifferentialDesignRequestPreferences preferences
            , Model model, HttpServletRequest request) throws IOException {

        return handleRequest(experimentAccession,model, request, accessKey, preferences.getSelectedContrast());
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showRnaSeqExperimentDesign(@PathVariable String experimentAccession,
                                             @RequestParam(value = "accessKey",required = false) String accessKey,
                                             Model model, HttpServletRequest request) throws IOException {
        return handleRequest(experimentAccession,model, request, accessKey, "");
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=PROTEOMICS_BASELINE"})
    public String showProteomicsExperimentDesign(@PathVariable String experimentAccession,Model model,
                                                 @RequestParam(value = "accessKey",required = false) String accessKey,
                                                 HttpServletRequest request) throws IOException {
        return handleRequest(experimentAccession,model, request, accessKey, "");
    }

    public String handleRequest(String experimentAccession, Model model, HttpServletRequest request, String accessKey, String selectedContrast) {
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);

        // add general experiment attributes to model

        model.addAllAttributes(new DownloadURLBuilder(experimentAccession).dataDownloadUrls(request.getRequestURI()));

        model.addAllAttributes(experiment.getAttributes());

        if(experiment instanceof DifferentialExperiment){
            model.addAllAttributes(((DifferentialExperiment)experiment).getDifferentialAttributes(selectedContrast));
        }

        model.addAttribute("preferences", new ExperimentDesignPageRequestPreferences(selectedContrast));

        return "experiment-experiment-design";
    }

    static class ExperimentDesignPageRequestPreferences extends BaselineRequestPreferences {

        private final String selectedContrast;

        ExperimentDesignPageRequestPreferences(String selectedContrast) {
            this.selectedContrast = selectedContrast;
        }


        /*Fixes the experiment-design magic Spring form element*/
        public String getSelectedContrast(){
            return selectedContrast;
        }
    }

}
















