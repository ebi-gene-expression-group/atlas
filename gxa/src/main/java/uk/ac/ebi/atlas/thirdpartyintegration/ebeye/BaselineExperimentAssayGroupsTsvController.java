package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
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
import java.text.MessageFormat;

/**
 * Generates a tsv url with a List of all assayGroups_id details for all baseline experiments
 */
@Controller
@Scope("request")
public class BaselineExperimentAssayGroupsTsvController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExperimentAssayGroupsTsvController.class);

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
        BaselineExperimentAssayGroupsLines baselineExperimentAssayGroupsLines;
        response.setContentType("text/tab-separated-values");
        PrintWriter writer = response.getWriter();



        for (Experiment experiment : experimentTrader.getPublicExperiments(
                ExperimentType.RNASEQ_MRNA_BASELINE, ExperimentType.PROTEOMICS_BASELINE)) {
            try {
                baselineExperimentAssayGroupsLines = new BaselineExperimentAssayGroupsLines(
                        (BaselineExperiment) experiment);
                extractLinesToTSVFormat(baselineExperimentAssayGroupsLines, writer);
            } catch (RuntimeException e){
                LOGGER.error(MessageFormat.format("Failed when loading {0}, error: {1}", experiment.getAccession(), e));
                writer.write("Error while attempting to write "+experiment.getAccession()+", file incomplete!!!");
                break;
            }
        }
    }


    private void extractLinesToTSVFormat(BaselineExperimentAssayGroupsLines baselineExperimentAssayGroupsLines, PrintWriter writer ) throws IOException {

        for (String[] line : baselineExperimentAssayGroupsLines) {
            String lineTab = Joiner.on("\t").join(line);
            writer.write(lineTab + "\n");
        }
    }

}
