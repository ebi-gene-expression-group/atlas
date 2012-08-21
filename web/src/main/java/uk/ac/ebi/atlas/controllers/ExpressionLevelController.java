package uk.ac.ebi.atlas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.services.ExpressionLevelService;

@Controller
public class ExpressionLevelController {

    @Autowired
    private ExpressionLevelService expressionLevelService;

    @RequestMapping("/experiment")
    public String getExpressionLevels(Model model) {

        model.addAttribute("expressions", expressionLevelService.getExpressionLevels());
        return "expressions";
    }
}
