package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@Controller
public class ExternallyAvailableContentController {
    private final ExpressionAtlasContentService expressionAtlasContentService;

    public ExternallyAvailableContentController(ExpressionAtlasContentService expressionAtlasContentService) {
        this.expressionAtlasContentService = expressionAtlasContentService;
    }

    /*
    I could be nicer and maybe even have tests:
    - the "View in Array Express" needs an independent URL
    - the QC report needs a redirect to another Atlas page, preserving the access key
    - the typical resource needs to circle back to this page
    */
    private JsonObject contentAsJson(ExternallyAvailableContent content, String accession, String accessKey) {
        JsonObject result = content.description.asJson();
        if ("redirect".equals(content.uri.getScheme())) {
            try {
                result.addProperty("url", new URL(content.uri.getSchemeSpecificPart()).toExternalForm());

            } catch (MalformedURLException e) {
                result.addProperty("url",
                        MessageFormat.format("{0}{1}",
                                content.uri.getSchemeSpecificPart(),
                                isNotEmpty(accessKey) ? "?accessKey=" + accessKey : "")
                );
            }

        } else {
            result.addProperty(
                    "url",
                    MessageFormat.format("experiments-content/{0}/resources/{1}{2}",
                            accession, content.uri.toString(), isNotEmpty(accessKey) ? "?accessKey=" + accessKey : ""
            ));
        }
        return result;
    }

    private JsonArray contentAsJson(List<ExternallyAvailableContent> contents, String accession, String accessKey) {
        JsonArray result = new JsonArray();
        for (ExternallyAvailableContent content: contents) {
            result.add(contentAsJson(content, accession, accessKey));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "json/experiments/{experimentAccession}/resources/{contentType}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String list(@PathVariable String experimentAccession,
                       @PathVariable String contentType,
                       @RequestParam(value = "accessKey", defaultValue = "") String accessKey) {
        return GSON.toJson(
                contentAsJson(
                        expressionAtlasContentService.list(
                                experimentAccession,
                                accessKey,
                                ExternallyAvailableContent.ContentType.valueOf(contentType)),
                        experimentAccession,
                        accessKey));
    }

    @RequestMapping(value = "experiments-content/{experimentAccession}/resources/**",
                    method = RequestMethod.GET)
    public void get(@PathVariable String experimentAccession,
                    @RequestParam(value = "accessKey", defaultValue = "") String accessKey,
                    HttpServletRequest request,
                    HttpServletResponse response) {

        expressionAtlasContentService.stream(experimentAccession, accessKey,
                URI.create(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
                        .toString()
                        .replaceFirst(".*/resources/", "")
                        .replaceFirst("\\?.*$", "")))
                .apply(response);
    }

}
