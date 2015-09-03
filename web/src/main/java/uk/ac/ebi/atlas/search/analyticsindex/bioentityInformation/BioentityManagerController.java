package uk.ac.ebi.atlas.search.analyticsindex.bioentityInformation;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.bioentity.GeneSetUtil;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.validation.Valid;

@Controller
@Scope("request")
public class BioentityManagerController {


    @RequestMapping(value = "/search/bioentity")
    public String searchGeneInformation(@Valid GeneQuerySearchRequestParameters requestParameters, Model model, RedirectAttributes redirectAttributes) {

        GeneQuery geneQuery = requestParameters.getGeneQuery();

        model.addAttribute("hasBaselineResults", true);

        redirectAttributes.addFlashAttribute("searchDescription", requestParameters.getDescription());
        model.addAttribute("bioentity", true);

        return getGeneIdRedirectString(geneQuery, "", requestParameters.isExactMatch());
    }


    private String getGeneIdRedirectString(GeneQuery geneQuery, String species, boolean isExactMatch) {

        return "redirect:/search/genes/" +  geneQuery.description();

//        boolean singleTerm = !StringUtils.containsWhitespace(geneQuery.description());
//        if (singleTerm && GeneSetUtil.isGeneSet(geneQuery.description().toUpperCase())) {
//            return ("redirect:/search/genesets/" + geneQuery.description());
//        } else {
//            return("redirect:/search/genes/" +  geneQuery.description());
//        }

    }
    
}
