
package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.profiles.baseline.ProteomicsBaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Scope("request")
public class ProteomicsBaselineExperimentPageController extends BaselineExperimentController {

    BaselineExperimentPageService baselineExperimentPageService;

    @Inject
    public ProteomicsBaselineExperimentPageController(BaselineExperimentPageServiceFactory
                                                                  baselineExperimentPageServiceFactory,
                                                                  ProteomicsBaselineProfileInputStreamFactory
                                                                  proteomicsBaselineProfileInputStreamFactory) {

        this.baselineExperimentPageService = baselineExperimentPageServiceFactory.create(proteomicsBaselineProfileInputStreamFactory);
    }


    @RequestMapping(value = "/experiments/{experimentAccession}", params = "type=PROTEOMICS_BASELINE")
    public String baselineExperiment(@ModelAttribute("preferences") @Valid ProteomicsBaselineRequestPreferences preferences
            , BindingResult result, Model model, HttpServletRequest request) {

        try {
            baselineExperimentPageService.prepareModel(preferences,
                    model, request,
                    false, false, false);
        } catch (GenesNotFoundException e) {
            result.addError(new ObjectError("requestPreferences", "No genes found matching query: '" + preferences.getGeneQuery() + "'"));
        }

        return "experiment";
    }

}