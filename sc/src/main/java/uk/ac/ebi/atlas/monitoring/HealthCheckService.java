package uk.ac.ebi.atlas.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.solr.cloud.admin.SolrCloudAdminProxy;

import java.util.Collections;

@Component
public class HealthCheckService {
    private SolrCloudAdminProxy solrCloudAdminProxy;
    private ScxaExperimentDao experimentDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckService.class);

    public HealthCheckService(SolrCloudAdminProxy solrCloudAdminProxy, ScxaExperimentDao experimentDao) {
        this.solrCloudAdminProxy = solrCloudAdminProxy;
        this.experimentDao = experimentDao;
    }

    public boolean isSolrUp() {
        try {
            return solrCloudAdminProxy.areCollectionsUp(Collections.singletonList("bioentities"),"scxa-analytics");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean isDatabaseUp() {
        try {
            return experimentDao.countExperiments() > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}
