package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.FastQCReportUtil;
import uk.ac.ebi.atlas.web.FastQCReportRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.MessageFormat;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: barrera
 */

@Controller
@Scope("singleton")
public class FastQCReportController {

    public static final String EXPERIMENT_ATTRIBUTE = "experiment";
    private static final String ALL_SPECIES_ATTRIBUTE = "allSpecies";
    private static final String PUBMED_IDS_ATTRIBUTE = "pubMedIds";
    private static final String EXPERIMENT_DESCRIPTION_ATTRIBUTE = "experimentDescription";
    private static final String HAS_EXTRA_INFO_ATTRIBUTE = "hasExtraInfo";
    private static final String EXPERIMENT_TYPE_ATTRIBUTE = "type";

    private final FastQCReportUtil fastQCReportUtil;
    private ExperimentTrader experimentTrader;
    private String reportSelected;


    @Inject
    public FastQCReportController(FastQCReportUtil fastQCReportUtil, ExperimentTrader experimentTrader) {
        this.fastQCReportUtil = fastQCReportUtil;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/qc/{resource:.*}",
                    method = RequestMethod.GET)
    public String getFastQCPage(HttpServletRequest request, Model model,
                                @PathVariable String experimentAccession,
                                @PathVariable String resource,
                                @RequestParam(value = "accessKey",required = false) String accessKey,
                                @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences, RedirectAttributes ra) throws IOException {


        if(!resource.equals("qc.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToQcResource(experimentAccession, resource);
        }

        // will generate 404 here for private experiments without access key
        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        prepareModel(request, model, experiment);

        model.addAttribute("fastQCReports", preferences.fastQCReportsList());

        reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.QC.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("MAPPING")){
                String path = MessageFormat.format("/experiments/{0}/qc/mapping/{1}", experimentAccession, "tophat1.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        if(!fastQCReportUtil.hasFastQC(experimentAccession)) {
            throw new ResourceNotFoundException("No fast qc report for " + experimentAccession);
        }

        String path = fastQCReportUtil.buildFastQCIndexHtmlPath(experimentAccession);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));

        return "fast-qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String resource) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/qc/{1}/", experimentAccession, resource);

        return "forward:" + path;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/qc/mapping/{resource:.*}",
            method = RequestMethod.GET)
    public String getFastMappingQCPage(HttpServletRequest request, Model model,
                                @PathVariable String experimentAccession,
                                @PathVariable String resource,
                                @RequestParam(value = "accessKey",required = false) String accessKey,
                                @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences, RedirectAttributes ra) throws IOException {

        if(!resource.equals("tophat1.html")) {
            // NB: resources do not need access key
            // otherwise we would have to add the access key to the query string for every resource in the page
            return forwardToMappingQcResource(experimentAccession, resource);
        }

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        prepareModel(request, model, experiment);

        model.addAttribute("fastQCReports", preferences.fastQCReportsList());

        reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.MAPPING.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("QC")){
                String path = MessageFormat.format("/experiments/{0}/qc/{1}", experimentAccession, "qc.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        if(!fastQCReportUtil.hasMappingQC(experimentAccession)) {
            throw new ResourceNotFoundException("No fast qc report for " + experimentAccession);
        }

        String path = fastQCReportUtil.buildMappingQCIndexHtmlPath(experimentAccession);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));

        return "fast-qc-template";

    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToMappingQcResource(String experimentAccession, String resource) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/qc/mapping/{1}", experimentAccession, resource);

        return "forward:" + path;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/qc/riq/**",
                    method = RequestMethod.GET)
    public String getFastQCReportPage(HttpServletRequest request, Model model, @PathVariable String experimentAccession,
                                      @RequestParam(value = "accessKey",required = false) String accessKey,
                                      @ModelAttribute("preferences") @Valid FastQCReportRequestPreferences preferences) throws IOException {

        Experiment experiment = experimentTrader.getExperiment(experimentAccession, accessKey);
        prepareModel(request, model, experiment);

        model.addAttribute("fastQCReports", preferences.fastQCReportsList());

        reportSelected = preferences.getSelectedReport();

        if (StringUtils.isBlank(reportSelected)) {
            reportSelected = FastQCReportRequestPreferences.FastQCReportType.QC.toString();
        }

        if(reportSelected != null) {
            //eg: redirect to nicer URL when arrayDesign is provided as a query string parameter
            if(reportSelected.equals("MAPPING")){
                String path = MessageFormat.format("/experiments/{0}/qc/mapping/{1}", experimentAccession, "tophat1.html");
                return "redirect:" + path + (StringUtils.isNotBlank(accessKey) ? "?accessKey=" + accessKey : "");
            }
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setSelectedReport(reportSelected);

        String beginPath = fastQCReportUtil.buildFastQCReportIndexHtmlPath(experimentAccession);

        String endPath = extractPath(request.getServletPath());

        //We need to check if the resource is an image or icon and handle it to the correspondent path
        String file = endPath.substring(endPath.lastIndexOf("/") + 1);
        if(!file.equals("fastqc_report.html")){
            return forwardToFastQCReportMappingResources(experimentAccession, endPath);
        }

        String fullPath = beginPath + "/" + endPath;
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(fullPath));

        return "fast-qc-template";
    }

    private String extractPath(String path) {
        return path.substring(path.indexOf("raw_data"));
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToFastQCReportMappingResources(String experimentAccession, String resource) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/qc/riq/{1}", experimentAccession, resource);

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

}
