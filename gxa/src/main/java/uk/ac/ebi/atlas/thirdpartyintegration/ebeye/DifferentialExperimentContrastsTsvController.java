package uk.ac.ebi.atlas.thirdpartyintegration.ebeye;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;

@Controller
@Scope("request")
public class DifferentialExperimentContrastsTsvController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DifferentialExperimentContrastsTsvController.class);

    private ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    public DifferentialExperimentContrastsTsvController(ExpressionAtlasExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/api/contrastdetails.tsv", method = RequestMethod.GET)
    public void generateTsvFormat(HttpServletResponse response) throws IOException {
        getAllDifferentialExperimentsContrastLines(response);
    }

    private void getAllDifferentialExperimentsContrastLines(HttpServletResponse response) throws IOException {
        DifferentialExperimentContrastLines diffExperimentContrastLines;
        DifferentialExperiment differentialExperiment;

        Set<String> microArrayExpAccessions = new HashSet<>(experimentTrader.getMicroarrayExperimentAccessions());
        Set<String> rnaSeqDiffExpAccessions = new HashSet<>(experimentTrader.getRnaSeqDifferentialExperimentAccessions());

        List<String> allExperimentsList = new ArrayList<>();
        allExperimentsList.addAll(microArrayExpAccessions);
        allExperimentsList.addAll(rnaSeqDiffExpAccessions);
        response.setContentType("text/tab-separated-values");
        PrintWriter writer = response.getWriter();

        for(String experimentAccession : allExperimentsList){
            try {
                differentialExperiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(experimentAccession);
                diffExperimentContrastLines = new DifferentialExperimentContrastLines(differentialExperiment);
                extractLinesToTSVFormat(diffExperimentContrastLines, writer);
            } catch (RuntimeException e){
                LOGGER.error(MessageFormat.format("Failed when loading {0}, error: {1}", experimentAccession, e));
                writer.write("Error while attempting to write "+experimentAccession+", file incomplete!!!");
                break;
            }
        }
    }

    private void extractLinesToTSVFormat(DifferentialExperimentContrastLines diffExperimentContrastLines,PrintWriter writer) throws IOException {

        for (String[] line : diffExperimentContrastLines) {
            String lineTab = Joiner.on("\t").join(line);
            writer.write(lineTab + "\n");
        }
    }

}
