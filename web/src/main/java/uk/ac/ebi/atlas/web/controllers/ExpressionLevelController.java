package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankBySpecificityAndExpressionLevelCommand;
import uk.ac.ebi.atlas.model.GeneProfilesList;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("request")
public class ExpressionLevelController {

    private RankBySpecificityAndExpressionLevelCommand rankCommand;

    @Inject
    public ExpressionLevelController(RankBySpecificityAndExpressionLevelCommand rankCommand) {
        this.rankCommand = rankCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneExpressions(@PathVariable String experimentAccession
                                    , @ModelAttribute("preferences") @Valid RequestPreferences preferences
                                    , BindingResult result, Model model) {

        if (!result.hasErrors()) {

            rankCommand.setRequestPreferences(preferences);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            model.addAttribute("heatmapOrganismParts", geneProfiles.getAllOrganismParts());

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());
        }

        return "experiment";
    }


}
















