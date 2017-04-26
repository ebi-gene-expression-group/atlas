package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;

public abstract class BaselineExperimentPageController extends HtmlExceptionHandlingController {

    @InitBinder("preferences")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

}
