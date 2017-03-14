package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
/*
TODO : implement refetch in the browser (or make this not be a separate component)
 */
@Controller
public class ExternallyAvailableContentController {

    private final ExpressionAtlasContentService expressionAtlasContentService;
    private static final Gson gson = new Gson();

    @Inject
    public ExternallyAvailableContentController(ExpressionAtlasContentService expressionAtlasContentService){
        this.expressionAtlasContentService = expressionAtlasContentService;
    }

    private JsonObject contentAsJson(ExternallyAvailableContent content,String accession,String accessKey, HttpServletRequest request){
        JsonObject result = content.description.asJson();

        result.addProperty("uri", MessageFormat.format("{0}/experiments/{1}/resources/{2}?accessKey={3}",
                ApplicationProperties.buildServerURL(request), accession, content.uri.toString(), accessKey
                ));
        return result;
    }

    private JsonArray contentAsJson(List<ExternallyAvailableContent> contents, String accession, String accessKey, HttpServletRequest request){
        JsonArray result = new JsonArray();
        for(ExternallyAvailableContent content: contents){
            result.add(contentAsJson(content, accession, accessKey, request));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/json/experiments/{experimentAccession}/resources", method = RequestMethod.GET)
    public String list(@PathVariable String experimentAccession, @RequestParam(value = "accessKey", defaultValue = "") String accessKey,
                       HttpServletRequest request) {

        return gson.toJson(
                contentAsJson(expressionAtlasContentService.list(experimentAccession, accessKey),
                experimentAccession, accessKey, request
                ));
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/resources/**", method = RequestMethod.GET)
    public void get(@PathVariable String experimentAccession, @RequestParam(value = "accessKey", defaultValue = "") String accessKey,
                    HttpServletRequest request, HttpServletResponse response) {

        expressionAtlasContentService.stream(experimentAccession, accessKey,
                URI.create(request.getAttribute( HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE )
                        .toString()
                        .replaceFirst(".*/resources/","")
                        .replaceFirst("\\?.*$", "")
                        .replaceAll(" +", "+")
                )
        ).apply(response);
    }

}
