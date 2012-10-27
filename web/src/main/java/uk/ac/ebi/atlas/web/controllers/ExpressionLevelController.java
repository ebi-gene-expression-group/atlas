package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankBySpecificityAndExpressionLevelCommand;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import javax.inject.Inject;
import javax.validation.Valid;

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

            GeneProfilesList geneProfiles = rankCommand.apply(DEMO_ACCESSION);

            //Set<String> genesToBeHighlighted = geneProfiles.getTop(preferences.getHeatmapMatrixSize()).getDistinctGeneIds();

            model.addAttribute("heatmapOrganismParts", geneProfiles.getAllOrganismParts());

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());
        }

        return "experiment";
    }


}
















