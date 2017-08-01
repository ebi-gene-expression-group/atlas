package uk.ac.ebi.atlas.controllers;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.apache.commons.lang3.StringUtils.isBlank;

//TODO Make all controllers that produce JSON data inherit from this class
public abstract class JsonExceptionHandlingController extends ReturnsJsonErrors {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonExceptionHandlingController.class);

    protected final Gson gson = new Gson();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(Exception e) {
        LOGGER.error("{} - {}", e.getMessage(), Joiner.on("\n\t").join(e.getStackTrace()));
        return gson.toJson(jsonError(isBlank(e.getMessage()) ? "Unknown error" : e.getMessage()));
    }
}
