package uk.ac.ebi.atlas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public abstract class HtmlExceptionHandlingController {
    @Autowired
    protected Environment env;

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {BioentityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleBioentityNotFoundException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(value = {UnparseableSemanticQueryException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadJsonException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView InternalServerHandleException(Exception e) {
        ModelAndView mav = new ModelAndView("error-page");
        mav.addObject("exceptionMessage", e.getMessage());
        return mav;
    }
}
