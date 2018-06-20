package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class HealthCheckController {
    private HealthCheckService healthCheckService;

    @Inject
    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @RequestMapping(
            value = "/json/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatus() {

        return GSON.toJson(ImmutableMap.of(
                "solr", healthCheckService.isSolrUp() ? "UP" : "DOWN",
                "db", healthCheckService.isDatabaseUp() ? "UP" : "DOWN"
        ));
    }

}
