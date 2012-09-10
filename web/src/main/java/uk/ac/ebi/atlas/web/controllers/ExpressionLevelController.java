package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.LoadExpressionLevelsCommand;
import uk.ac.ebi.atlas.model.TranscriptExpression;

import javax.inject.Inject;
import java.util.List;

@Controller
@Scope("request")
public class ExpressionLevelController {

    public static final String DEMO_ACCESSION = "E-MTAB-513";
    private LoadExpressionLevelsCommand loadExpressionLevelsCommand;

    @Inject
    public ExpressionLevelController(LoadExpressionLevelsCommand loadExpressionLevelsCommand) {
        this.loadExpressionLevelsCommand = loadExpressionLevelsCommand;
    }

    @RequestMapping("/experiment")
    public String showExpressionLevels(@RequestParam(value = "dataFileURL", required = false) String dataFileURL, Model model) {

        return showExpressionLevels(dataFileURL, DEMO_ACCESSION, model);

    }

    @RequestMapping("/experiment/{experimentAccession}")
    public String showExpressionLevels(@RequestParam(value = "dataFileURL", required = false) String dataFileURL,
                                       @PathVariable("experimentAccession") String experimentAccession,
                                       Model model) {

        if (dataFileURL != null) {
            loadExpressionLevelsCommand.setDataFileURL(dataFileURL);
        }

        List<TranscriptExpression> transcriptExpressions;

        if (experimentAccession == null) {
            experimentAccession = DEMO_ACCESSION;
        }

        transcriptExpressions = loadExpressionLevelsCommand.apply(experimentAccession);

        model.addAttribute("expressions", transcriptExpressions);

        return "experiment";
    }
}
















