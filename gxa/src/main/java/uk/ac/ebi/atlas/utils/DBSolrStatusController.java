package uk.ac.ebi.atlas.utils;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.apache.solr.client.solrj.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;

import javax.inject.Inject;

/*
On 4 Oct 2016, at 12:04, Andrea Cristofori via RT <www-prod@ebi.ac.uk> wrote:

Hi Wojtek,

I added the check which now checks, on each VM, that the page on
/gxa/json/dbsolr/status

contains the text:
{"DB":"UP","Solr":"UP"}

if it's not found it should remove your node from the pool. IF you want to test
it you could try to change it in one of the PG VMs which should stop receiving
requests.

Andrea
 */


@Controller
@Scope("request")
public final class DBSolrStatusController extends JsonExceptionHandlingController {

    private ExperimentDAO experimentDAO;
    private SolrClient solrClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(DBSolrStatusController.class);

    @Inject
    public DBSolrStatusController(ExperimentDAO experimentDAO, SolrClient solrClientAnalytics) {
        this.experimentDAO = experimentDAO;
        this.solrClient = solrClientAnalytics;
    }

    @RequestMapping(value = "/json/dbsolr/status",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String dbAndSolrStatus(){
        //check database is up or down
        Integer experimentsSize = experimentDAO.countExperiments();
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

        return new Gson().toJson(ImmutableMap.of("DB", dbStatus, "Solr", solrStatus));
    }
}
