package uk.ac.ebi.atlas.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class ExpressionService {


    @RequestMapping("/experiment")
    public String getExpressionLevels(Model model) {
        //should come from some king of DAO
        Set<ExpressionLevel> expressionLevels = new HashSet<>();

        Set<FactorValue> factorValues = new HashSet<>();
        factorValues.add(new FactorValue("f1", "v1"));
        factorValues.add(new FactorValue("f2", "v2"));

        expressionLevels.add(new ExpressionLevel("ENST1", factorValues, 100));

        model.addAttribute("expressions", expressionLevels);
        model.addAttribute("size", expressionLevels.size());
        return "expressions";
    }
}
