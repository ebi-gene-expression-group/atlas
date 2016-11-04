
package uk.ac.ebi.atlas.experimentpage.baseline;

import org.apache.solr.common.SolrException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaselineExperimentController {

    @ExceptionHandler(value = {SolrException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("query-error-page");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

}
