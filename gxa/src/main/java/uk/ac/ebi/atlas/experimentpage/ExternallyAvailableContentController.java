package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

@Controller
public class ExternallyAvailableContentController {

    public static final String LIST_RESOURCES_URL = "/json/experiments/{experimentAccession}/resources";

    public static final String listResourcesUrl(HttpServletRequest request, String experimentAccession, String accessKey){
        return ApplicationProperties.buildServerURL(request)+LIST_RESOURCES_URL.replace("{experimentAccession}", experimentAccession)+ (
                org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "?accessKey="+accessKey : ""
                );
    }
    private final ExpressionAtlasContentService expressionAtlasContentService;
    private static final Gson gson = new Gson();

    @Inject
    public ExternallyAvailableContentController(ExpressionAtlasContentService expressionAtlasContentService){
        this.expressionAtlasContentService = expressionAtlasContentService;
    }

    /*
    I could be nicer and maybe even have tests:
    - the "View in Array Express" needs an independent URL
    - the QC report needs a redirect to another Atlas page, preserving the access key
    - the typical resource needs to circle back to this page
    */
    private JsonObject contentAsJson(ExternallyAvailableContent content,String accession,String accessKey, HttpServletRequest request){
        JsonObject result = content.description.asJson();
        if("redirect".equals(content.uri.getScheme())){
            try {
                result.addProperty("url", new URL(content.uri.getSchemeSpecificPart()).toExternalForm());

            } catch (MalformedURLException e) {
                result.addProperty("url",
                        MessageFormat.format("{0}/{1}{2}",
                                ApplicationProperties.buildServerURL(request),
                                content.uri.getSchemeSpecificPart(), StringUtils.isNotEmpty(accessKey)? "?accessKey="+accessKey : ""
                        )
                );
            }

        } else {
            result.addProperty("url", MessageFormat.format("{0}/experiments/{1}/resources/{2}{3}",
                    ApplicationProperties.buildServerURL(request), accession, content.uri.toString(), StringUtils.isNotEmpty(accessKey)? "?accessKey="+accessKey : ""
            ));
        }
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
    @RequestMapping(value = LIST_RESOURCES_URL, method = RequestMethod.GET)
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
                )
        ).apply(response);
    }

}
