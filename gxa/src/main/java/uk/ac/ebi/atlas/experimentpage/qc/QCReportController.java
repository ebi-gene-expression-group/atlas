package uk.ac.ebi.atlas.experimentpage.qc;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.controllers.DownloadURLBuilder;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
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

    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";


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
                    MessageFormat.format("/experiments/{0}/qc/{1}/index.html",
                            experiment.getAccession(), arrayDesign
                            ),
                    ExternallyAvailableContent.Description.create(
                            ExternallyAvailableContent.Description.join("Supplementary Information",
                                    arrayDesignTrader.getArrayDesignByName(arrayDesign)),
                            "icon-qc", "The microarray quality check report"
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

        // will generate 404 here for private experiments without access key
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        prepareModel(request, model, experiment);

        String selectedArrayDesignName = preferences.getArrayDesignAccession();
        if (selectedArrayDesignName != null) {
            String selectedArrayDesign = arrayDesignTrader.getArrayDesignAccession(selectedArrayDesignName);

            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            String path = MessageFormat.format("/experiments/{0}/qc/{1}/{2}", experimentAccession, selectedArrayDesign, resource);
            return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setArrayDesignAccession(arrayDesignTrader.getArrayDesignByName(arrayDesign));

        if (!qcReportUtil.hasQCReport(experimentAccession, arrayDesign)) {
            throw new ResourceNotFoundException("No qc report for " + experimentAccession + " array design " + arrayDesign);
        }

        String path = qcReportUtil.buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));

        model.addAllAttributes(new DownloadURLBuilder(experimentAccession).dataDownloadUrls(request.getRequestURI()));

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/qc/{0}_{1}_QM/{2}", experimentAccession, arrayDesign, resource);

        return "forward:" + path;
    }

    private void prepareModel(HttpServletRequest request, Model model, MicroarrayExperiment experiment) {
        request.setAttribute("experiment", experiment);

        model.addAllAttributes(experiment.getAttributes());
    }
}
