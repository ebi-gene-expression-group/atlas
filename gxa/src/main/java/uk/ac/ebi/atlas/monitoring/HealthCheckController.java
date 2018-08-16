package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;

import javax.inject.Inject;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public final class HealthCheckController {

    private GxaExperimentDao expressionAtlasExperimentDao;
    private SolrClient solrClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @Inject
    public HealthCheckController(GxaExperimentDao expressionAtlasExperimentDao, SolrClient solrClientAnalytics) {
        this.expressionAtlasExperimentDao = expressionAtlasExperimentDao;
        this.solrClient = solrClientAnalytics;
    }

    @RequestMapping(
            value = "/json/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatus(){
        //check database is up or down
        Integer experimentsSize = expressionAtlasExperimentDao.countExperiments();
        String dbStatus = (experimentsSize > 0) ? "UP" : "DOWN";

        //check solr is up or down
        String solrStatus = "UP";
        try {
            if(solrClient.ping().getStatus() == 0) {
                solrStatus = "UP";
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            solrStatus = "DOWN";
        }

        return GSON.toJson(ImmutableMap.of("DB", dbStatus, "Solr", solrStatus));
    }
}
