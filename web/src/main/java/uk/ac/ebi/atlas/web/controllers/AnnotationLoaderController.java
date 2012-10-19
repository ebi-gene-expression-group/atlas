package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GeneNameUpdateCommand;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("request")
public class AnnotationLoaderController {


    private GeneNameUpdateCommand geneNameUpdateCommand;

    @Inject
    public AnnotationLoaderController(GeneNameUpdateCommand geneNameUpdateCommand) {
        this.geneNameUpdateCommand = geneNameUpdateCommand;
    }

    @RequestMapping("/updateAnnotations")
    public String showGeneExpressions(@ModelAttribute("preferences") @Valid RequestPreferences preferences, BindingResult result, Model model) {

        geneNameUpdateCommand.loadGeneNames();

        return "experiment";
    }


}
















