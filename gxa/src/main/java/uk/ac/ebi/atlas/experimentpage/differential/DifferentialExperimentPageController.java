package uk.ac.ebi.atlas.experimentpage.differential;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.baseline.ExperimentPageController;

public class DifferentialExperimentPageController  extends ExperimentPageController {

    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }
}
