package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String showExpressionLevels(@RequestParam(value = "rankingSize", required = false) Integer rankingSize,
                                       @RequestParam(value = "rpkmCutOff", required = false) Double rpkmCutOff,
                                       Model model) {

        if (rankingSize != null) {
            loadExpressionLevelsCommand.setRankingSize(rankingSize);
        }

        if (rpkmCutOff != null) {
            loadExpressionLevelsCommand.setRpkmCutOff(Double.valueOf(rpkmCutOff));
        }

        List<TranscriptExpression> transcriptExpressions;

        transcriptExpressions = loadExpressionLevelsCommand.apply(DEMO_ACCESSION);

        model.addAttribute("expressions", transcriptExpressions);

        return "experiment";
    }
}
















