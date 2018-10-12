package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;

import javax.inject.Inject;

import java.util.Collections;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public final class HealthCheckController {
    private HealthCheckService healthCheckService;
    private ScxaExperimentDao experimentDao;

    @Inject
    public HealthCheckController(HealthCheckService healthCheckService, ScxaExperimentDao experimentDao) {
        this.healthCheckService = healthCheckService;
        this.experimentDao = experimentDao;
    }

    @RequestMapping(
            value = "/json/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatus() {

        return GSON.toJson(ImmutableMap.of(
                "solr",
                healthCheckService.isSolrUp(
                        Collections.singletonList("bioentities"),
                        "scxa-analytics", "scxa-gene2experiment") ? "UP" : "DOWN",
                "db",
                healthCheckService.isDatabaseUp(experimentDao) ? "UP" : "DOWN"
        ));
    }

}
