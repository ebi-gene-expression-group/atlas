package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import com.google.common.base.Joiner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 * Date: 09/01/2014
 *
 * Generates a tsv url with a List of all assayGroups_id details for all baseline experiments
 *
 */
@Controller
@Scope("request")
public class BaselineExperimentAssayGroupsTsvController {

    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    public BaselineExperimentAssayGroupsTsvController(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/api/assaygroupsdetails.tsv", method = RequestMethod.GET)
    public void generateTsvFormat(HttpServletResponse response) throws IOException {
        getAllBaselineExperimentsAssayGroups(response);
    }

    private void getAllBaselineExperimentsAssayGroups(HttpServletResponse response) throws IOException {
        BaselineExperiment baselineExperiment;
        BaselineExperimentAssayGroupsLines baselineExperimentAssayGroupsLines;


        for (String experimentAccession : experimentTrader.getBaselineExperimentAccessions()) {
            baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccession);
            baselineExperimentAssayGroupsLines = new BaselineExperimentAssayGroupsLines(baselineExperiment);
            extractLinesToTSVFormat(baselineExperimentAssayGroupsLines, response);

        }
    }


    private void extractLinesToTSVFormat(BaselineExperimentAssayGroupsLines baselineExperimentAssayGroupsLines, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();

        response.setContentType("text/tab-separated-values");

        Iterator<String[]> lines = baselineExperimentAssayGroupsLines.iterator();

        while (lines.hasNext()){
            String[] line = lines.next();
            String lineTab = Joiner.on("\t").join(line);
            writer.write(lineTab + "\n");
        }
    }

}
