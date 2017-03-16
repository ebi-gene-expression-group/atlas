package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.MessageFormat;
import java.util.Collection;

@Controller
@Scope("singleton")
public class QCReportController extends ExternallyAvailableContent.Supplier<MicroarrayExperiment> {


    private final QCReportUtil qcReportUtil;
    private ExperimentTrader experimentTrader;
    private ArrayDesignTrader arrayDesignTrader;

    @Inject
    public QCReportController(QCReportUtil qcReportUtil, ExperimentTrader experimentTrader, ArrayDesignTrader arrayDesignTrader) {
        this.qcReportUtil = qcReportUtil;
        this.experimentTrader = experimentTrader;
        this.arrayDesignTrader = arrayDesignTrader;
    }

    @Override
    public Collection<ExternallyAvailableContent> get(final MicroarrayExperiment experiment) {
        ImmutableList.Builder<ExternallyAvailableContent> b = ImmutableList.builder();
        for (final String arrayDesign : experiment.getArrayDesignAccessions()) {

            if (qcReportUtil.hasQCReport(experiment.getAccession(), arrayDesign)) {
                b.add( new ExternallyAvailableContent(
                    MessageFormat.format("experiments/{0}/qc/{1}/index.html",
                            experiment.getAccession(), arrayDesign
                            ),
                    ExternallyAvailableContent.Description.create(
                            ExternallyAvailableContent.Description.join("Supplementary Information",
                                    arrayDesignTrader.getArrayDesignByName(arrayDesign)),
                            "icon-qc", MessageFormat.format("Microarray quality metrics report for {0} on array design {1}", experiment.getAccession(), arrayDesign)
                    )));
            }
        }
        return b.build();
    }

    /**
     * @param request
     * @param model
     * @param experimentAccession
     * @param arrayDesign
     * @param resource
     * @param preferences
     * @param ra                  RedirectAttributes is needed for redirection without parameters being added to the url
     *                            DO NOT REMOVE IT
     * @return
     * @throws IOException
     * TODO: get rid of
     */
    @RequestMapping(value = "/experiments/{experimentAccession}/qc/{arrayDesign}/{resource:.*}",
            method = RequestMethod.GET)
    public String getQCPage(HttpServletRequest request, Model model,
                            @PathVariable String experimentAccession,
                            @PathVariable String arrayDesign,
                            @PathVariable String resource,
                            @RequestParam(value = "accessKey", required = false) String accessKey,
                            @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences, RedirectAttributes ra) throws IOException {

        if (!resource.equals("index.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToQcResource(experimentAccession, arrayDesign, resource);
        }

        request.setAttribute("contentPath", FileSystems.getDefault().getPath(qcReportUtil.buildQCReportIndexHtmlPath(experimentAccession, arrayDesign)));

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/qc/{0}_{1}_QM/{2}", experimentAccession, arrayDesign, resource);

        return "forward:" + path;
    }

}
