
package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Controller
public class ExperimentDesignDownloadController<T extends Experiment> {

    private final ExperimentTrader experimentTrader;
    private final ExperimentDesignDownloadService experimentDesignDownloadService;


    @Inject
    public ExperimentDesignDownloadController(DataFileHub dataFileHub, ExperimentTrader experimentTrader) {
        this.experimentDesignDownloadService = new ExperimentDesignDownloadService(dataFileHub);
        this.experimentTrader =experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/experiment-design.tsv")
    protected void extractExperimentDesign(String experimentAccession, HttpServletResponse response, String
            accessKey) throws IOException {


        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "-experiment-design.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        experimentDesignDownloadService.writeLines(experimentAccession,
                experimentTrader.getExperiment(experimentAccession,accessKey).getAnalysedRowsAccessions(),
                response.getWriter());
    }

}