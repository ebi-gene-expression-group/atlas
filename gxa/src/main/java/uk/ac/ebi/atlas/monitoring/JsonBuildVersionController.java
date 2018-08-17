package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
@PropertySource("classpath:configuration.properties")
public class JsonBuildVersionController extends JsonExceptionHandlingController {

    private String buildNumber;
    private String buildBranch;
    private String buildCommitId;
    private String tomcatHostname;

    @Inject
    public JsonBuildVersionController(@Value("${buildNumber}") String buildNumber,
                                      @Value("${buildBranch}") String buildBranch,
                                      @Value("${buildCommitId}") String buildCommitId,
                                      @Value("${tomcatHostname}") String tomcatHostname) {
        this.buildNumber = buildNumber;
        this.buildBranch = buildBranch;
        this.buildCommitId = buildCommitId;
        this.tomcatHostname = tomcatHostname;
    }

    @RequestMapping(
            value = "/json/build",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String getBuildInfo() {
        return GSON.toJson(
                ImmutableMap.of(
                        "bambooBuildVersion", buildNumber,
                        "gitBranch", buildBranch,
                        "gitCommitID", buildCommitId,
                        "tomcatHostname", tomcatHostname));
    }
}
