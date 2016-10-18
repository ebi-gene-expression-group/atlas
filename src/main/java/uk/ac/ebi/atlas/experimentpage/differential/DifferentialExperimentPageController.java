package uk.ac.ebi.atlas.experimentpage.differential;

import org.apache.solr.common.SolrException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public class DifferentialExperimentPageController {

    @InitBinder("preferences")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }


    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }
}
