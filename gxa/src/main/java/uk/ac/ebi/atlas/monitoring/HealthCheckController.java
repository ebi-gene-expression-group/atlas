package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;

import javax.inject.Inject;
import java.util.Arrays;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public final class HealthCheckController {

    private HealthCheckService healthCheckService;
    private GxaExperimentDao experimentDao;

    @Inject
    public HealthCheckController(HealthCheckService healthCheckService, GxaExperimentDao experimentDao) {
        this.healthCheckService = healthCheckService;
        this.experimentDao = experimentDao;
    }

    // This is the legacy endpoint that we will maintain until the settings on the load balancer can be adjusted
    @RequestMapping(
            value = "/json/dbsolr/status",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatusLegacy(){
        return GSON.toJson(ImmutableMap.of(
                "Solr", healthCheckService.isSolrUp(Arrays.asList("analytics", "bioentities")) ? "UP" : "DOWN",
                "DB", healthCheckService.isDatabaseUp(experimentDao) ? "UP" : "DOWN"
        ));
    }

    @GetMapping(
            value = "/json/health",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatus(){
        return GSON.toJson(ImmutableMap.of(
                "solr", healthCheckService.isSolrUp(Arrays.asList("analytics", "bioentities")) ? "UP" : "DOWN",
                "db", healthCheckService.isDatabaseUp(experimentDao) ? "UP" : "DOWN"
        ));
    }
}
