package uk.ac.ebi.atlas.controllers.rest.admin;

import uk.ac.ebi.atlas.solr.admin.BioentitiesIndexer;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping(value = "/admin")
public class BioentityIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIndexController.class);

    private BioentitiesIndexer bioentitiesIndexer;
    private BioentityIndexMonitor bioentityIndexMonitor;

    @Inject
    BioentityIndexController(BioentitiesIndexer bioentitiesIndexer, BioentityIndexMonitor bioentityIndexMonitor){
        this.bioentitiesIndexer = bioentitiesIndexer;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        bioentityIndexMonitor.failed(e);

        LOGGER.error(e.getMessage(),e);
        return e.getMessage();
    }

    @RequestMapping(value = "/bioentitiesIndex/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String buildStatus() {
        return bioentityIndexMonitor.reportProgress();
    }


    @RequestMapping(value = "/bioentitiesIndex/buildIndex",  produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String build() {

        bioentitiesIndexer.rebuildIndex();

        return bioentityIndexMonitor.reportProgress();

    }

}