package uk.ac.ebi.atlas.controllers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.apache.commons.lang3.StringUtils.isBlank;

//TODO Make all controllers that produce JSON data inherit from this class
public abstract class JsonExceptionHandlingController {
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(Exception e) {
        return gson.toJson(
                ImmutableMap.of(
                        "error",
                        isBlank(e.getMessage()) ? "Unknown error" : e.getMessage()));
    }
}
