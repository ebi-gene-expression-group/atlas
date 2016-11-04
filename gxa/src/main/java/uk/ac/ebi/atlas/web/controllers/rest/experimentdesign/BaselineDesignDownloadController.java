
package uk.ac.ebi.atlas.web.controllers.rest.experimentdesign;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class BaselineDesignDownloadController extends ExperimentDesignDownloadController<BaselineExperiment> {

    @Inject
    public BaselineDesignDownloadController(FileTsvReaderBuilder fileTsvReaderBuilder,ExperimentTrader experimentTrader) {
        super(fileTsvReaderBuilder,experimentTrader);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=RNASEQ_MRNA_BASELINE"})
    public void downloadExperimentDesign(@PathVariable String experimentAccession,
                                         @RequestParam(value = "accessKey",required = false) String accessKey,
                                         HttpServletResponse response) throws
            IOException {

        extractExperimentDesign(experimentAccession, response, accessKey);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params =
            {"type=PROTEOMICS_BASELINE"})
    public void downloadExperimentDesignProteomics(@PathVariable String experimentAccession,
                                                   @RequestParam(value = "accessKey",required = false) String accessKey,
                                                   HttpServletResponse response) throws
            IOException {

        extractExperimentDesign(experimentAccession, response, accessKey);

    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(BaselineExperiment experiment) {
        return experiment.getExperimentRunAccessions();
    }
}
















