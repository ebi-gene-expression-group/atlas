package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class DifferentialDesignDownloadController extends ExperimentDesignDownloadController<DifferentialExperiment> {

    @Inject
    public DifferentialDesignDownloadController(DataFileHub dataFileHub,
                                                ExperimentTrader experimentTrader) {
        super(dataFileHub, experimentTrader);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public void downloadRnaSeqExperimentDesign(@PathVariable String experimentAccession,
                                               @RequestParam(value = "accessKey",required = false) String accessKey, HttpServletResponse response) throws IOException {

        extractExperimentDesign(experimentAccession, response,accessKey);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=MICROARRAY_ANY"})
    public void downloadMicroarrayExperimentDesign(@PathVariable String experimentAccession,
                                                   @RequestParam(value = "accessKey",required = false) String accessKey, HttpServletResponse response) throws IOException {

        extractExperimentDesign(experimentAccession, response, accessKey);

    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(DifferentialExperiment experiment) {
        return experiment.getAssayAccessions();
    }
}
















