package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Controller
public class QCReportController {

    private static final String QC_REPORT_URL = "experiments-content/{experimentAccession}/qc/{arrayDesign}/{resource:.*}";

    public static String getQcReportUrl(String experimentAccession, String arrayDesign, String accessKey) {
        return QC_REPORT_URL.replace("{experimentAccession}", experimentAccession)
                            .replace("{arrayDesign}", arrayDesign)
                            .replace("{resource:.*}", "index.html")
                + (org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "?accessKey=" + accessKey : "");
    }

    private ExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;

    @Inject
    public QCReportController(ExperimentTrader experimentTrader, DataFileHub dataFileHub) {
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
    }

    // RedirectAttributes is needed for redirection without parameters being added to the url
    @RequestMapping(value = QC_REPORT_URL, method = RequestMethod.GET)
    public String getQCPage(HttpServletRequest request,
                            @PathVariable final String experimentAccession,
                            @PathVariable final String arrayDesign,
                            @PathVariable String resource,
                            @RequestParam(value = "accessKey", required = false) String accessKey,
                            RedirectAttributes ra) {

        if (!resource.equals("index.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToQcResource(experimentAccession, arrayDesign, resource);
        }

        Preconditions.checkNotNull(experimentTrader.getExperiment(experimentAccession, accessKey));

        request.setAttribute("contentPath",
                new MicroarrayQCFiles(dataFileHub.getExperimentFiles(experimentAccession).qcFolder)
                        .get(experimentAccession, arrayDesign).or(() -> {
                            throw new ResourceNotFoundException(
                                    MessageFormat.format("Not found: QC report for experiment {0} and array design {1}",
                                            experimentAccession, arrayDesign));
                        }));

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) {
        String path = MessageFormat.format("/expdata/{0}/qc/{1}/{2}",
                experimentAccession, MicroarrayQCFiles.folderName(experimentAccession, arrayDesign), resource);

        return "forward:" + path;
    }

}
