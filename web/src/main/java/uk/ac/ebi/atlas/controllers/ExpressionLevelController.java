package uk.ac.ebi.atlas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.commands.RankExpressionLevels;
import uk.ac.ebi.atlas.model.ExpressionLevel;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ExpressionLevelController {

    public static final String DEMO_ACCESSION = "E-MTAB-513";
    private RankExpressionLevels rankExpressionLevels;

    @Inject
    public ExpressionLevelController(RankExpressionLevels rankExpressionLevels) {
        this.rankExpressionLevels = rankExpressionLevels;
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
            rankExpressionLevels.setDataFileURL(dataFileURL);
        }

        List<ExpressionLevel> expressionLevels;

        if (experimentAccession == null) {
            experimentAccession = DEMO_ACCESSION;
        }

        expressionLevels = rankExpressionLevels.apply(experimentAccession);

        model.addAttribute("expressions", expressionLevels);

        return "experiment";
    }
}
















