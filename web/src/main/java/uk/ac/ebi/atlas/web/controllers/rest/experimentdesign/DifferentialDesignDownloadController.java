
package uk.ac.ebi.atlas.web.controllers.rest.experimentdesign;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
@Scope("request")
public class DifferentialDesignDownloadController extends ExperimentDesignDownloadController<DifferentialExperiment> {

    @Inject
    public DifferentialDesignDownloadController(FileTsvReaderBuilder fileTsvReaderBuilder) {
        super(fileTsvReaderBuilder);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=RNASEQ_MRNA_DIFFERENTIAL"})
    public void downloadRnaSeqExperimentDesign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        extractExperimentDesign(request, response);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv", params = {"type=MICROARRAY_ANY"})
    public void downloadMicroarrayExperimentDesign(HttpServletRequest request, HttpServletResponse response) throws IOException {

        extractExperimentDesign(request, response);

    }

    @Override
    protected Set<String> getAnalysedRowsAccessions(DifferentialExperiment experiment) {
        return experiment.getAssayAccessions();
    }
}
















