
package uk.ac.ebi.atlas.web.controllers.rest.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.solr.admin.BioentityIndexAdmin;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping(value = "/admin")
public class BioentityIndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BioentityIndexController.class);

    private BioentityIndexAdmin bioentityIndexAdmin;
    private BioentityIndexMonitor bioentityIndexMonitor;

    @Inject
    BioentityIndexController(BioentityIndexAdmin bioentityIndexAdmin, BioentityIndexMonitor bioentityIndexMonitor){
        this.bioentityIndexAdmin = bioentityIndexAdmin;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        bioentityIndexMonitor.failed(e);

        LOGGER.error(e.getMessage(),e);
        return e.getMessage();
    }

    @RequestMapping(value = "/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String buildStatus() {
        return bioentityIndexMonitor.reportProgress();
    }


    @RequestMapping(value = "/buildIndex",  produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String build() {

        bioentityIndexAdmin.rebuildIndex();

        return bioentityIndexMonitor.reportProgress();

    }

}