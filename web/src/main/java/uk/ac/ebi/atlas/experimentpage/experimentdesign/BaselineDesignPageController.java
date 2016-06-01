package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignPageController extends ExperimentDesignPageRequestHandler<BaselineExperiment> {

    private final FastQCReportUtil fastQCReportUtil;

    @Inject
    public BaselineDesignPageController(DownloadURLBuilder downloadURLBuilder, ArrayDesignTrader arrayDesignTrader,
                                            ExperimentTrader experimentTrader,FastQCReportUtil fastQCReportUtil){
        super(downloadURLBuilder,arrayDesignTrader,experimentTrader);
        this.fastQCReportUtil = fastQCReportUtil;
    }


    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showRnaSeqExperimentDesign(@PathVariable String experimentAccession,
                                             @RequestParam(value = "accessKey",required = false) String accessKey,
                                              Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("type", ExperimentType.RNASEQ_MRNA_BASELINE);
        return handleRequest(experimentAccession,model, request, accessKey);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=PROTEOMICS_BASELINE"})
    public String showProteomicsExperimentDesign(@PathVariable String experimentAccession,Model model,
                                                 @RequestParam(value = "accessKey",required = false) String accessKey,
                                                 HttpServletRequest request) throws IOException {
        model.addAttribute("type", ExperimentType.PROTEOMICS_BASELINE);
        return handleRequest(experimentAccession,model, request, accessKey);
    }

    @Override
    protected void extendModel(Model model, BaselineExperiment experiment, String experimentAccession) {
        //This is necessary for adding functionality to the QC button
        Set<Factor> organisms = experiment.getExperimentalFactors().getDefaultFilterFactors();
        String species = experiment.getFirstOrganism();

        if(!organisms.isEmpty()) {
            for (Factor factor : organisms) {
                if (factor.getType().equals("ORGANISM")) {
                    species = factor.getValue();
                }
            }
        }

        try {
            if (fastQCReportUtil.hasFastQC(experimentAccession, species)) {
                fastQCReportUtil.buildFastQCIndexHtmlPath(experimentAccession, species);
                model.addAttribute("species", species);
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException("Species could not be found");
        }
    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
        return experiment.getExperimentRunAccessions();
    }
}