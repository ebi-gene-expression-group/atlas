package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProteinWidgetController {

    @RequestMapping("/heatmap-widget")
    public String getHomePage(Model model) {

        model.addAttribute("experimentAccession", "E-MTAB-513");
        model.addAttribute("experimentDescription", "RNA-Seq of human individual tissues and mixture of 16 tissues (Illumina Body Map)");
        model.addAttribute("allSpecies", "Home sapiens");

        return "heatmap-widget";
    }
}
