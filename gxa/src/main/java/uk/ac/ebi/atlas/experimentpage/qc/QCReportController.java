package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;

@Controller
public class QCReportController extends ExternallyAvailableContent.Supplier<MicroarrayExperiment> {

    private static final String QC_REPORT_URL = "/experiments-content/{experimentAccession}/qc/{arrayDesign}/{resource:.*}";

    public static final String getQcReportUrl(HttpServletRequest request, String experimentAccession,
                                              String arrayDesign, String accessKey) {
        return ApplicationProperties.buildServerURL(request) +
                QC_REPORT_URL
                        .replace("{experimentAccession}", experimentAccession)
                        .replace("{arrayDesign}", arrayDesign)
                        .replace("{resource:.*}", "index.html")
                + (
                org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "?accessKey=" + accessKey : ""
        );
    }


    private ExperimentTrader experimentTrader;
    private final DataFileHub dataFileHub;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    public QCReportController(ExperimentTrader experimentTrader,
                              DataFileHub dataFileHub, ArrayDesignTrader arrayDesignTrader) {
        this.experimentTrader = experimentTrader;
        this.dataFileHub = dataFileHub;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    @Override
    public Collection<ExternallyAvailableContent> get(final MicroarrayExperiment experiment) {
        ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
        for (final String arrayDesign : new MicroarrayQCFiles(dataFileHub.getExperimentFiles(experiment.getAccession()).qcFolder)
                .getArrayDesignsThatHaveQcReports()) {
            b.add(new ExternallyAvailableContent(
                    QC_REPORT_URL
                    .replace("{experimentAccession}", experiment.getAccession())
                    .replace("{arrayDesign}", arrayDesign)
                    .replace("{resource:.*}", "index.html")
                    ,
                    ExternallyAvailableContent.Description.create(
                            ExternallyAvailableContent.Description.join("Supplementary Information",
                                    arrayDesignTrader.getArrayDesignByName(arrayDesign)),
                            "icon-qc", MessageFormat.format("Microarray quality metrics report for {0} on array design {1}", experiment.getAccession(), arrayDesign)
                    )));

        }
        return b.build();
    }

    /**
     * @param request
     * @param model
     * @param experimentAccession
     * @param arrayDesign
     * @param resource
     * @param ra                  RedirectAttributes is needed for redirection without parameters being added to the url
     *                            DO NOT REMOVE IT
     * @return
     * @throws IOException
     */
    @RequestMapping(value = QC_REPORT_URL,
            method = RequestMethod.GET)
    public String getQCPage(HttpServletRequest request, Model model,
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
                        .get(experimentAccession, arrayDesign).or(new Supplier<Path>() {
                    @Override
                    public Path get() {
                        throw new ResourceNotFoundException(
                                MessageFormat.format("Not found: QC report for experiment {0} and array design {1}",
                                        experimentAccession, arrayDesign));
                    }
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
