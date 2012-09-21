package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.LoadTranscriptExpressionsCommand;
import uk.ac.ebi.atlas.model.TranscriptExpressionsList;

import javax.inject.Inject;
import java.util.Set;

@Controller
@Scope("request")
public class ExpressionLevelController {

    public static final String DEMO_ACCESSION = "E-MTAB-513";

    private LoadTranscriptExpressionsCommand loadTranscriptExpressionsCommand;

    @Inject
    public ExpressionLevelController(LoadTranscriptExpressionsCommand loadTranscriptExpressionsCommand) {
        this.loadTranscriptExpressionsCommand = loadTranscriptExpressionsCommand;
    }

    private static final int DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED = 3;

    private int heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED;


    @RequestMapping("/experiment")
    public String showTranscriptExpressions(@RequestParam(value = "rankingSize", required = false) Integer rankingSize,
                                       @RequestParam(value = "cutoff", required = false) Double cutoff,
                                       @RequestParam(value = "heatmapMatrixSize", required = false) Integer heatmapMatrixSize,
                                       Model model) {

        if (rankingSize != null) {
            loadTranscriptExpressionsCommand.setRankingSize(rankingSize);
        }

        if (cutoff != null) {
            loadTranscriptExpressionsCommand.setCutoff(cutoff);
        }

        if (heatmapMatrixSize != null) {
            this.heatmapMatrixSize = heatmapMatrixSize;
        }


        TranscriptExpressionsList transcriptExpressions = loadTranscriptExpressionsCommand.apply(DEMO_ACCESSION);


        Set<String> transcriptsToBeHighlighted = transcriptExpressions.getTop(this.heatmapMatrixSize).getDistinctTranscriptIds();

        model.addAttribute("heatmapTranscripts", transcriptsToBeHighlighted);

        model.addAttribute("heatmapOrganismParts", transcriptExpressions.getDistinctOrganismParts(transcriptsToBeHighlighted));

        model.addAttribute("transcriptExpressions", transcriptExpressions);


        return "experiment";
    }

}
















