package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankBySpecificityAndExpressionLevelCommand;
import uk.ac.ebi.atlas.model.GeneExpressionsList;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Set;

@Controller
@Scope("request")
public class ExpressionLevelController {

    public static final String DEMO_ACCESSION = "E-MTAB-513";

    private RankBySpecificityAndExpressionLevelCommand rankCommand;

    @Inject
    public ExpressionLevelController(RankBySpecificityAndExpressionLevelCommand rankCommand) {
        this.rankCommand = rankCommand;
    }

    @RequestMapping("/experiment")
    public String showGeneExpressions(@ModelAttribute("preferences") @Valid RequestPreferences preferences, BindingResult result, Model model) {

        if (!result.hasErrors()) {


            rankCommand.setRequestPreferences(preferences);

            GeneExpressionsList geneExpressions = rankCommand.apply(DEMO_ACCESSION);

            Set<String> genesToBeHighlighted = geneExpressions.getTop(preferences.getHeatmapMatrixSize()).getDistinctGeneIds();

            GeneExpressionsList heatmapExpressions = geneExpressions.subList(genesToBeHighlighted);

            model.addAttribute("heatmapGenes", genesToBeHighlighted);

            model.addAttribute("heatmapOrganismParts", geneExpressions.getDistinctOrganismParts(genesToBeHighlighted));

            model.addAttribute("geneExpressions", geneExpressions);

            model.addAttribute("minExpressionLevel", heatmapExpressions.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", heatmapExpressions.getMaxExpressionLevel());
            model.addAttribute("totalResultCount", geneExpressions.getTotalResultCount());
        }

        return "experiment";
    }


}
















