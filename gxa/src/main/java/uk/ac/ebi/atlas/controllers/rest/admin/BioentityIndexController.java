package uk.ac.ebi.atlas.controllers.rest.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.solr.bioentities.admin.BioentitiesIndexer;
import uk.ac.ebi.atlas.solr.bioentities.admin.monitor.BioentityIndexMonitor;

@RestController
@Scope("request")
@RequestMapping(value = "/admin")
public class BioentityIndexController extends HtmlExceptionHandlingController {
    private BioentitiesIndexer bioentitiesIndexer;
    private BioentityIndexMonitor bioentityIndexMonitor;

    BioentityIndexController(BioentitiesIndexer bioentitiesIndexer, BioentityIndexMonitor bioentityIndexMonitor) {
        this.bioentitiesIndexer = bioentitiesIndexer;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
    }

    @RequestMapping(value = "/bioentitiesIndex/buildIndex/status",
                    method = RequestMethod.GET,
                    produces = "text/plain;charset=UTF-8")
    public String buildStatus() {
        return bioentityIndexMonitor.reportProgress();
    }

    @RequestMapping(value = "/bioentitiesIndex/buildIndex",
            method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    public String build() {
        bioentitiesIndexer.rebuildIndex();
        return bioentityIndexMonitor.reportProgress();
    }

}
