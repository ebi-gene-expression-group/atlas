package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonPrimitive;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

@Controller
public class SingleCellExperimentPageController extends ExperimentPageController {

    @RequestMapping(value = "/experiments/{experimentAccession}")
    @ResponseBody
    public String baselineExperimentData(@PathVariable String experimentAccession,
                                         @RequestParam(required = false) String accessKey,
                                         Model model, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        return gson.toJson(new JsonPrimitive(MessageFormat.format("You asked me for an experiment {0}. Nice one",
                experimentAccession)));
    }
}
