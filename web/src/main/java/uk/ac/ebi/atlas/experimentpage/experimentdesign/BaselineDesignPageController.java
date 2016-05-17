package uk.ac.ebi.atlas.experimentpage.experimentdesign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.fastqc.FastQCReportUtil;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignPageController extends ExperimentDesignPageRequestHandler<BaselineExperiment> {

    private FastQCReportUtil fastQCReportUtil;

    @Inject
    public void setFastQCReportUtil(FastQCReportUtil fastQCReportUtil) {
        this.fastQCReportUtil = fastQCReportUtil;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=RNASEQ_MRNA_BASELINE"})
    public String showRnaSeqExperimentDesign(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("type", ExperimentType.RNASEQ_MRNA_BASELINE);
        return handleRequest(model, request);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design", params = {"type=PROTEOMICS_BASELINE"})
    public String showProteomicsExperimentDesign(Model model, HttpServletRequest request) throws IOException {
        model.addAttribute("type", ExperimentType.PROTEOMICS_BASELINE);
        return handleRequest(model, request);
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