package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.experimentpage.ExperimentPageController;

public abstract class BaselineExperimentPageController extends ExperimentPageController {

    @InitBinder("preferences")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

}
