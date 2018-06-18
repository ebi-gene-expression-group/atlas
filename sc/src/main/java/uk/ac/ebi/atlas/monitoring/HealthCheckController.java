package uk.ac.ebi.atlas.monitoring;

import com.google.common.collect.ImmutableMap;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.solr.cloud.admin.SolrCloudAdminProxy;

import javax.inject.Inject;
import java.io.IOException;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RestController
public class HealthCheckController {
    private SolrCloudAdminProxy solrCloudAdminProxy;

    @Inject
    public HealthCheckController(SolrCloudAdminProxy solrCloudAdminProxy) throws IOException, SolrServerException {
        this.solrCloudAdminProxy = solrCloudAdminProxy;
    }

    @RequestMapping(
            value = "/json/health",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getHealthStatus() throws IOException, SolrServerException {
        boolean solrStatus = solrCloudAdminProxy.isCollectionUp("bioentities", false) && solrCloudAdminProxy.isCollectionUp("scxa-analytics", true);

        return GSON.toJson(ImmutableMap.of(
                "solr", solrStatus ? "UP" : "DOWN"
        ));
    }

}
