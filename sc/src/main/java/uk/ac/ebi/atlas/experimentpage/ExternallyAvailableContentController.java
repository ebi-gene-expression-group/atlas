package uk.ac.ebi.atlas.experimentpage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
public class ExternallyAvailableContentController {

    private static final String LIST_RESOURCES_URL = "json/experiments/{experimentAccession}/resources/{contentType}";
    private static final String STREAM_RESOURCES_URL = "experiments-content/{experimentAccession}/resources/**";

    public static String listResourcesUrl(String experimentAccession, String accessKey, ExternallyAvailableContent.ContentType contentType){
        return LIST_RESOURCES_URL
                .replace("{experimentAccession}", experimentAccession)
                .replace("{contentType}", contentType.name())
                + (isNotEmpty(accessKey) ? "?accessKey="+accessKey : "");
    }

    public static String streamResourcesUrl(String experimentAccession, String accessKey, String resourceName){
        return STREAM_RESOURCES_URL
                .replace("{experimentAccession}", experimentAccession)
                .replace("**", resourceName)
                + (isNotEmpty(accessKey) ? "?accessKey="+accessKey : "");
    }
    private final SingleCellContentService singleCellContentService;
    private static final Gson gson = new Gson();

    @Inject
    public ExternallyAvailableContentController(SingleCellContentService singleCellContentService){
        this.singleCellContentService = singleCellContentService;
    }

    /*
    I could be nicer and maybe even have tests:
    - the "View in Array Express" needs an independent URL
    - the QC report needs a redirect to another Atlas page, preserving the access key
    - the typical resource needs to circle back to this page
    */
//    private JsonObject contentAsJson(ExternallyAvailableContent content,String accession,String accessKey, HttpServletRequest request){
//        JsonObject result = content.description.asJson();
//        if("redirect".equals(content.uri.getScheme())){
//            try {
//                result.addProperty("url", new URL(content.uri.getSchemeSpecificPart()).toExternalForm());
//
//            } catch (MalformedURLException e) {
//                result.addProperty("url",
//                        MessageFormat.format("{0}{1}",
//                                content.uri.getSchemeSpecificPart(),
//                                isNotEmpty(accessKey) ? "?accessKey="+accessKey : "")
//                );
//            }
//
//        } else {
//            result.addProperty("url",
//                    MessageFormat.format("experiments-content/{0}/resources/{1}{2}",
//                            accession, content.uri.toString(), isNotEmpty(accessKey)? "?accessKey="+accessKey : ""
//            ));
//        }
//        return result;
//    }

//    private JsonArray contentAsJson(List<ExternallyAvailableContent> contents, String accession, String accessKey, HttpServletRequest request){
//        JsonArray result = new JsonArray();
//        for(ExternallyAvailableContent content: contents){
//            result.add(contentAsJson(content, accession, accessKey, request));
//        }
//        return result;
//    }

//    @ResponseBody
//    @RequestMapping(value = LIST_RESOURCES_URL, method = RequestMethod.GET)
//    public String list(@PathVariable String experimentAccession,@PathVariable String contentType, @RequestParam(value = "accessKey", defaultValue = "") String accessKey,
//                       HttpServletRequest request) {
//
//        return gson.toJson(
//                contentAsJson(singleCellContentService.list(experimentAccession, accessKey, ExternallyAvailableContent.ContentType.valueOf(contentType)),
//                experimentAccession, accessKey, request
//                ));
//    }
//
//    @RequestMapping(value = STREAM_RESOURCES_URL, method = RequestMethod.GET)
//    public void get(@PathVariable String experimentAccession, @RequestParam(value = "accessKey", defaultValue = "") String accessKey,
//                    HttpServletRequest request, HttpServletResponse response) {
//
//        singleCellContentService.stream(experimentAccession, accessKey,
//                URI.create(request.getAttribute( HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE )
//                        .toString()
//                        .replaceFirst(".*/resources/","")
//                        .replaceFirst("\\?.*$", "")
//                )
//        ).apply(response);
//    }

}
