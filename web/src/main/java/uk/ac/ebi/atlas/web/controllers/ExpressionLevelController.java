package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.LoadExpressionLevelsCommand;
import uk.ac.ebi.atlas.model.TranscriptExpressionsList;

import javax.inject.Inject;
import java.util.Set;

@Controller
@Scope("request")
public class ExpressionLevelController {

    public static final String DEMO_ACCESSION = "E-MTAB-513";

    private LoadExpressionLevelsCommand loadExpressionLevelsCommand;

    @Inject
    public ExpressionLevelController(LoadExpressionLevelsCommand loadExpressionLevelsCommand) {
        this.loadExpressionLevelsCommand = loadExpressionLevelsCommand;
    }

    private static final int DEFAULT_NUMBER_OF_TOP_EXPRESSION_LEVELS_TO_BE_HIGHLIGHTED = 3;

    private int heatmapMatrixSize = DEFAULT_NUMBER_OF_TOP_EXPRESSION_LEVELS_TO_BE_HIGHLIGHTED;


    @RequestMapping("/experiment")
    public String showExpressionLevels(@RequestParam(value = "rankingSize", required = false) Integer rankingSize,
                                       @RequestParam(value = "rpkmCutOff", required = false) Double rpkmCutOff,
                                       @RequestParam(value = "heatmapMatrixSize", required = false) Integer heatmapMatrixSize,
                                       Model model) {

        if (rankingSize != null) {
            loadExpressionLevelsCommand.setRankingSize(rankingSize);
        }

        if (rpkmCutOff != null) {
            loadExpressionLevelsCommand.setRpkmCutOff(rpkmCutOff);
        }

        if (heatmapMatrixSize != null) {
            this.heatmapMatrixSize = heatmapMatrixSize;
        }


        TranscriptExpressionsList transcriptExpressions = loadExpressionLevelsCommand.apply(DEMO_ACCESSION);


        Set<String> transcriptsToBeHighlighted = transcriptExpressions.getTop(this.heatmapMatrixSize).getDistinctTranscriptIds();

        TranscriptExpressionsList heatmapExpressions = transcriptExpressions.subList(transcriptsToBeHighlighted);

        model.addAttribute("heatmapTranscripts", transcriptsToBeHighlighted);

        model.addAttribute("heatmapOrganismParts", transcriptExpressions.getDistinctOrganismParts(transcriptsToBeHighlighted));

        model.addAttribute("transcriptExpressions", transcriptExpressions);

        model.addAttribute("minFpkm", heatmapExpressions.getMinFpkm());

        model.addAttribute("maxFpkm", heatmapExpressions.getMaxFpkm());

        return "experiment";
    }


}
















