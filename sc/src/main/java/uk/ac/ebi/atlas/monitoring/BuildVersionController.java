package uk.ac.ebi.atlas.monitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class BuildVersionController extends JsonExceptionHandlingController {

    private String buildNumber;
    private String buildBranch;
    private String buildCommitId;

    @Inject
    public BuildVersionController(@Value("#{configuration['buildNumber']}") String buildNumber,
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
        Map<String, String> buildInformation = new HashMap<>();

        buildInformation.put("Bamboo build version", buildNumber);
        buildInformation.put("Git branch", buildBranch);
        buildInformation.put("Git commit ID", buildCommitId);

        return GSON.toJson(buildInformation);
    }
}
