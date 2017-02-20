package uk.ac.ebi.atlas.widget;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

public abstract class WidgetController {

    protected final Gson gson = new Gson();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        Map<String, String> response = Maps.newHashMap();
        response.put("error", e.getMessage());
        return gson.toJson(response);
    }
}
