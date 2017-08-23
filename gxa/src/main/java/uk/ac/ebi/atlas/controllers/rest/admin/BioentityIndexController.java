package uk.ac.ebi.atlas.controllers.rest.admin;

import uk.ac.ebi.atlas.controllers.HtmlExceptionHandlingController;
import uk.ac.ebi.atlas.solr.admin.BioentitiesIndexer;
import uk.ac.ebi.atlas.solr.admin.monitor.BioentityIndexMonitor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping(value = "/admin")
public class BioentityIndexController extends HtmlExceptionHandlingController {

    private BioentitiesIndexer bioentitiesIndexer;
    private BioentityIndexMonitor bioentityIndexMonitor;

    @Inject
    BioentityIndexController(BioentitiesIndexer bioentitiesIndexer, BioentityIndexMonitor bioentityIndexMonitor){
        this.bioentitiesIndexer = bioentitiesIndexer;
        this.bioentityIndexMonitor = bioentityIndexMonitor;
    }

    @RequestMapping(value = "/bioentitiesIndex/buildIndex/status", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String buildStatus() {
        return bioentityIndexMonitor.reportProgress();
    }

    @RequestMapping(value = "/bioentitiesIndex/buildIndex", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String build() {
        bioentitiesIndexer.rebuildIndex();
        return bioentityIndexMonitor.reportProgress();
    }

}