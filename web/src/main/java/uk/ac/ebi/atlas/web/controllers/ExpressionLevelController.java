package uk.ac.ebi.atlas.web.controllers;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.LoadExpressionLevelsCommand;
import uk.ac.ebi.atlas.model.TranscriptExpression;

import javax.inject.Inject;
import java.util.LinkedHashSet;
import java.util.List;
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

        List<TranscriptExpression> transcriptExpressions;

        transcriptExpressions = loadExpressionLevelsCommand.apply(DEMO_ACCESSION);

        model.addAttribute("heatmapTranscripts", this.transcriptsToBeHighlighted(transcriptExpressions));

        model.addAttribute("heatmapExpressions", this.expressionsToBeHighlighted(transcriptExpressions));

        model.addAttribute("expressions", transcriptExpressions);


        return "experiment";
    }

    public List<TranscriptExpression> expressionsToBeHighlighted(List<TranscriptExpression> transcriptExpressions){
        List<TranscriptExpression> topExpressions = transcriptExpressions.subList(0, this.heatmapMatrixSize);
        return Ordering.usingToString().onResultOf(orderByOrganismPart).sortedCopy(topExpressions);
    }

    public Set<String> transcriptsToBeHighlighted(List<TranscriptExpression> transcriptExpressions){
        Set<String> transcriptIds = new LinkedHashSet<String>();
        for (int i = 0; i < this.heatmapMatrixSize && i < transcriptExpressions.size(); i++){
            transcriptIds.add(transcriptExpressions.get(i).getTranscriptId());
        }
        return transcriptIds;
    }

    private Function<TranscriptExpression, String> orderByOrganismPart = new Function<TranscriptExpression, String>() {
        @Override
        public String apply(TranscriptExpression transcriptExpression) {
            return transcriptExpression.getOrganismPart();
        }
    };
}
















