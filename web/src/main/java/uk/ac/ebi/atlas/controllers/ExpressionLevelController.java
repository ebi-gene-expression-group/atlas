package uk.ac.ebi.atlas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.services.ExpressionLevelService;

import javax.inject.Inject;

@Controller
public class ExpressionLevelController {

    private ExpressionLevelService expressionLevelService;

    @Inject
    public ExpressionLevelController(ExpressionLevelService expressionLevelService) {
        this.expressionLevelService = expressionLevelService;
    }

    @RequestMapping("/experiment")
    public String getExpressionLevels(Model model) {

        model.addAttribute("expressions", expressionLevelService.getExpressionLevels());
        return "expressions";
    }
}
