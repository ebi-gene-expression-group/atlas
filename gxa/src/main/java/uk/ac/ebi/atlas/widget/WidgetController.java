package uk.ac.ebi.atlas.widget;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public abstract class WidgetController {
    protected final Gson gson = new Gson();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleException(Exception e, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, String> bodyJsonMap = Maps.newHashMap();
        bodyJsonMap.put("error", e.getMessage());
        return gson.toJson(bodyJsonMap);
    }
}
