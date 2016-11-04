package uk.ac.ebi.atlas.utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;

import javax.inject.Inject;
import java.io.IOException;

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
public final class DBSolrStatusController {

    private ExperimentDAO experimentDAO;
    private SolrClient solrClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(DBSolrStatusController.class);

    @Inject
    public DBSolrStatusController(ExperimentDAO experimentDAO, SolrClient solrClient) {
        this.experimentDAO = experimentDAO;
        this.solrClient = solrClient;
    }

    @RequestMapping(value = "/json/dbsolr/status", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView dbAndSolrStatus(){
        ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());

        //check database is up or down
        Integer experimentsSize = experimentDAO.countExperiments();
        String dbStatus = (experimentsSize > 0) ? "UP" : "DOWN";

        //check solr is up or down
        String solrStatus = "UP";
        try {
            if(solrClient.ping().getStatus() == 0) {
                solrStatus = "UP";
            }
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage());
            solrStatus = "DOWN";
        }

        mav.addObject("DB", dbStatus);
        mav.addObject("Solr", solrStatus);
        return mav;
    }
}
