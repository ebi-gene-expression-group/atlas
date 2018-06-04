package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class JsonBuildVersionController extends JsonExceptionHandlingController {

    private String buildNumber;
    private String buildBranch;
    private String buildCommitId;

    @Inject
    public JsonBuildVersionController(@Value("#{configuration['buildNumber']}") String buildNumber,
                                      @Value("#{configuration['buildBranch']}") String buildBranch,
                                      @Value("#{configuration['buildCommitId']}") String buildCommitId) {
        this.buildNumber = buildNumber;
        this.buildBranch = buildBranch;
        this.buildCommitId = buildCommitId;
    }

    @RequestMapping(
            value = "/json/build",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public String getBuildNumber() {
        return GSON.toJson(
                ImmutableMap.of(
                        "bambooBuildVersion", buildNumber,
                        "gitBranch", buildBranch,
                        "gitCommitID", buildCommitId));
    }
}
