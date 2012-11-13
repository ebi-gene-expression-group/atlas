package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.RankGeneProfilesCommand;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class GeneProfilesPageController {

    private RankGeneProfilesCommand rankCommand;

    private ApplicationProperties applicationProperties;

    @Inject
    public GeneProfilesPageController(RankGeneProfilesCommand rankCommand, ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        this.rankCommand = rankCommand;
    }

    @RequestMapping("/experiments/{experimentAccession}")
    public String showGeneProfiles(@PathVariable String experimentAccession
                                    , @ModelAttribute("preferences") @Valid RequestPreferences preferences
                                    , BindingResult result, Model model, HttpServletRequest request) {

        if (!result.hasErrors()) {

            rankCommand.setRequestPreferences(preferences);

            GeneProfilesList geneProfiles = rankCommand.apply(experimentAccession);

            model.addAttribute("heatmapOrganismParts", geneProfiles.getAllOrganismParts());

            model.addAttribute("geneProfiles", geneProfiles);

            model.addAttribute("minExpressionLevel", geneProfiles.getMinExpressionLevel());

            model.addAttribute("maxExpressionLevel", geneProfiles.getMaxExpressionLevel());

            model.addAttribute("totalResultCount", geneProfiles.getTotalResultCount());

            model.addAttribute("requestURI", request.getRequestURI());

            model.addAttribute("experimentAccession", experimentAccession);

            model.addAttribute("allOrganismParts", applicationProperties.getAllOrganismParts());

            model.addAttribute("maleAnatomogramFile", applicationProperties.getAnatomogramFileName(experimentAccession, true));

            model.addAttribute("femaleAnatomogramFile", applicationProperties.getAnatomogramFileName(experimentAccession, false));

            model.addAttribute("downloadUrl", request.getRequestURI() + ".tsv"
                    + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        }

        return "experiment";
    }


}
















