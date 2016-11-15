
package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public abstract class ExperimentDesignDownloadController<T extends Experiment> {

    private final ExperimentTrader experimentTrader;
    private final ExperimentDesignDownloadService experimentDesignDownloadService;


    public ExperimentDesignDownloadController(DataFileHub dataFileHub, ExperimentTrader experimentTrader) {
        this.experimentDesignDownloadService = new ExperimentDesignDownloadService(dataFileHub);
        this.experimentTrader =experimentTrader;
    }

    protected void extractExperimentDesign(String experimentAccession, HttpServletResponse response, String
            accessKey) throws IOException {


        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "-experiment-design.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        experimentDesignDownloadService.writeLines(experimentAccession,
                getAnalysedRowsAccessions((T) experimentTrader.getExperiment(experimentAccession,accessKey)),
                response.getWriter());
    }

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);

}