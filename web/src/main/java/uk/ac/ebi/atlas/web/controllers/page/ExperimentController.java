package uk.ac.ebi.atlas.web.controllers.page;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@Scope("request")
public class ExperimentController {

    @Inject
    private GeneProfilesPageController geneProfilesPageController;

    @RequestMapping(value = "/experiments/{experimentAccession}")
    public String showDifferentialProfiles( HttpServletRequest request) {

        request.setAttribute("type", "baseline");
        return "forward:/experiments/{experimentAccession}?type=baseline";

    }
}
