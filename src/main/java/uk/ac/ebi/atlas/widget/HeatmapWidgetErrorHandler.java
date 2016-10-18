package uk.ac.ebi.atlas.widget;

import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

public abstract class HeatmapWidgetErrorHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView widgetSpecific404(Exception e) {
        ModelAndView mav = new ModelAndView("widget-error");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }

    @ExceptionHandler(value = {RecoverableDataAccessException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView blah(Exception e) {
        ModelAndView mav = new ModelAndView("widget-error");
        mav.addObject("exceptionMessage", e.getMessage());

        return mav;
    }
}
