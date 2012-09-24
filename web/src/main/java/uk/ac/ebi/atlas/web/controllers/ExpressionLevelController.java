package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.LoadTranscriptExpressionsCommand;
import uk.ac.ebi.atlas.model.TranscriptExpressionsList;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Set;

@Controller
@Scope("request")
public class ExpressionLevelController{

    public static final String DEMO_ACCESSION = "E-MTAB-513";

    private LoadTranscriptExpressionsCommand loadTranscriptExpressionsCommand;

    @Inject
    public ExpressionLevelController(LoadTranscriptExpressionsCommand loadTranscriptExpressionsCommand) {
        this.loadTranscriptExpressionsCommand = loadTranscriptExpressionsCommand;
    }

    private static final int DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED = 3;

    private int heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_EXPRESSIONS_TO_BE_HIGHLIGHTED;


    @RequestMapping("/experiment")
    public String showTranscriptExpressions(@ModelAttribute("preferences") @Valid Preferences preferences, BindingResult result, Model model){

        if (preferences.getRankingSize() != null) {
            loadTranscriptExpressionsCommand.setRankingSize(preferences.getRankingSize());
        }

        if (preferences.getCutoff() != null) {
            loadTranscriptExpressionsCommand.setCutoff(preferences.getCutoff());
        }

        if (preferences.getHeatmapMatrixSize() != null) {
            this.heatmapMatrixSize = preferences.getHeatmapMatrixSize();
        }


        TranscriptExpressionsList transcriptExpressions = loadTranscriptExpressionsCommand.apply(DEMO_ACCESSION);


        Set<String> transcriptsToBeHighlighted = transcriptExpressions.getTop(this.heatmapMatrixSize).getDistinctTranscriptIds();

        TranscriptExpressionsList heatmapExpressions = transcriptExpressions.subList(transcriptsToBeHighlighted);

        model.addAttribute("heatmapTranscripts", transcriptsToBeHighlighted);

        model.addAttribute("heatmapOrganismParts", transcriptExpressions.getDistinctOrganismParts(transcriptsToBeHighlighted));

        model.addAttribute("transcriptExpressions", transcriptExpressions);

        model.addAttribute("minExpressionLevel", heatmapExpressions.getMinExpressionLevel());

        model.addAttribute("maxExpressionLevel", heatmapExpressions.getMaxExpressionLevel());

        return "experiment";
    }


}
















