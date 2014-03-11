package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.ServletContextResourceLoader;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.QCReportUtil;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.MessageFormat;
import java.util.Set;

@Controller
@Scope("singleton")
public class QCReportController {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";
    private static final String ALL_SPECIES_ATTRIBUTE = "allSpecies";
    private static final String PUBMED_IDS_ATTRIBUTE = "pubMedIds";
    private static final String EXPERIMENT_DESCRIPTION_ATTRIBUTE = "experimentDescription";
    private static final String HAS_EXTRA_INFO_ATTRIBUTE = "hasExtraInfo";
    private static final String EXPERIMENT_TYPE_ATTRIBUTE = "type";

    private final ServletContextResourceLoader servletContextResourceLoader;
    private final QCReportUtil qcReportUtil;
    private ExperimentTrader experimentTrader;

    @Inject
    public QCReportController(ServletContext servletContext, QCReportUtil qcReportUtil, ExperimentTrader experimentTrader) {
        this.servletContextResourceLoader = new ServletContextResourceLoader(servletContext);
        this.qcReportUtil = qcReportUtil;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/qc/{arrayDesign}/{resource:.*}")
    public String getQCPage(HttpServletRequest request, Model model,
                            @PathVariable String experimentAccession,
                            @PathVariable String arrayDesign,
                            @PathVariable String resource) throws IOException {

        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        prepareModel(request, model, experiment);

        if(!resource.equals("index.html")) {
            return forwardToQcResource(experimentAccession, arrayDesign, resource);
        }

        if(!qcReportUtil.hasQCReport(experimentAccession, arrayDesign)) {
            throw new ResourceNotFoundException("No qc report for " + experimentAccession + " array design " + arrayDesign);
        }

        String path = qcReportUtil.buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);

        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));
        request.setAttribute("experimentAccession", experimentAccession);

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) throws IOException {
        String path = MessageFormat.format("/qc/{0}/qc/{0}_{1}_QM/{2}", experimentAccession, arrayDesign, resource);

        return "forward:" + path;
    }

    private void prepareModel(HttpServletRequest request, Model model, Experiment experiment) {
        request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

        Set<String> allSpecies = experiment.getSpecies();

        model.addAttribute(EXPERIMENT_TYPE_ATTRIBUTE, experiment.getType());

        model.addAttribute(ALL_SPECIES_ATTRIBUTE, StringUtils.join(allSpecies, ", "));

        model.addAttribute(EXPERIMENT_DESCRIPTION_ATTRIBUTE, experiment.getDescription());

        model.addAttribute(HAS_EXTRA_INFO_ATTRIBUTE, experiment.hasExtraInfoFile());

        model.addAttribute(PUBMED_IDS_ATTRIBUTE, experiment.getPubMedIds());
    }

    private Resource fetchResource(String path) {
        Resource resource = servletContextResourceLoader.getResource(path);
        if (!resource.exists() || !resource.isReadable()) {
            throw new ResourceNotFoundException("Resource " + path + " does not exist");
        }
        return resource;
    }
}
