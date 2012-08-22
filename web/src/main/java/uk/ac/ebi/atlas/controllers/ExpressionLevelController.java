package uk.ac.ebi.atlas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.services.ExpressionLevelService;

import javax.inject.Inject;
import java.util.List;

@Controller
public class ExpressionLevelController {

    private ExpressionLevelService expressionLevelService;

    @Inject
    public ExpressionLevelController(ExpressionLevelService expressionLevelService) {
        this.expressionLevelService = expressionLevelService;
    }

    @ModelAttribute("expressions")
    public List<ExpressionLevel> getExpressionLevels(){
        return expressionLevelService.getExpressionLevels();
    }

    @RequestMapping("/experiment")
    public String showExpressionLevels() {
        return "experiment";
    }
}
