package uk.ac.ebi.atlas.utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
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

@Controller
@Scope("request")
public final class DBSolrStatusController {

    private ExperimentDAO experimentDAO;
    private SolrClient solrClient;

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
        Integer experimentsSize = experimentDAO.findAllExperiments().size();
        String dbStatus = (experimentsSize > 0) ? "up" : "down";

        //check solr is up or down
        String solrStatus = null;
        try {
            if(solrClient.ping().getStatus() == 0) {
                solrStatus = "up";
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
            solrStatus = "down";
        } catch (IOException e) {
            e.printStackTrace();
        }

        mav.addObject("DataBase status", dbStatus);
        mav.addObject("Solr status", solrStatus);
        return mav;
    }
}
