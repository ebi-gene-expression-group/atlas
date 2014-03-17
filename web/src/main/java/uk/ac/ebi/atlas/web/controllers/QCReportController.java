package uk.ac.ebi.atlas.web.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.QCReportUtil;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private static final String QC_ARRAY_DESIGNS_ATTRIBUTE = "qcArrayDesigns";


    private final QCReportUtil qcReportUtil;
    private ExperimentTrader experimentTrader;

    @Inject
    public QCReportController(QCReportUtil qcReportUtil, ExperimentTrader experimentTrader) {
        this.qcReportUtil = qcReportUtil;
        this.experimentTrader = experimentTrader;
    }

    /**
     *
     * @param request
     * @param model
     * @param experimentAccession
     * @param arrayDesign
     * @param resource
     * @param preferences
     * @param ra RedirectAttributes is needed for redirection without parameters being added to the url
     *           DO NOT REMOVE IT
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/experiments/{experimentAccession}/qc/{arrayDesign}/{resource:.*}",
                    method = RequestMethod.GET)
    public String getQCPage(HttpServletRequest request, Model model,
                            @PathVariable String experimentAccession,
                            @PathVariable String arrayDesign,
                            @PathVariable String resource,
                            @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences, RedirectAttributes ra) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getPublicExperiment(experimentAccession);
        prepareModel(request, model, experiment);

        String selectedArrayDesign = preferences.getArrayDesignAccession();

        if(selectedArrayDesign != null) {
            String path = MessageFormat.format("/experiments/{0}/qc/{1}/{2}", experimentAccession, selectedArrayDesign, resource);
            return "redirect:/" + path;
        }

        //When changing the selection in the combo, we need to set the new selection in the preferences
        //otherwise the combo is not being updated.
        preferences.setArrayDesignAccession(arrayDesign);

        if(!resource.equals("index.html")) {
            return forwardToQcResource(experimentAccession, arrayDesign, resource);
        }

        if(!qcReportUtil.hasQCReport(experimentAccession, arrayDesign)) {
            throw new ResourceNotFoundException("No qc report for " + experimentAccession + " array design " + arrayDesign);
        }

        String path = qcReportUtil.buildQCReportIndexHtmlPath(experimentAccession, arrayDesign);
        request.setAttribute("contentPath", FileSystems.getDefault().getPath(path));
        extendModel(request, experiment);

        return "qc-template";
    }

    // forwards to a url that is handled by the mvc:resources handler, see WebConfig.java
    public String forwardToQcResource(String experimentAccession, String arrayDesign, String resource) throws IOException {
        String path = MessageFormat.format("/qc/{0}/qc/{0}_{1}_QM/{2}", experimentAccession, arrayDesign, resource);

        return "forward:" + path;
    }

    private void prepareModel(HttpServletRequest request, Model model, MicroarrayExperiment experiment) {
        request.setAttribute(EXPERIMENT_ATTRIBUTE, experiment);

        Set<String> allSpecies = experiment.getSpecies();

        model.addAttribute(EXPERIMENT_TYPE_ATTRIBUTE, experiment.getType());

        model.addAttribute(ALL_SPECIES_ATTRIBUTE, StringUtils.join(allSpecies, ", "));

        model.addAttribute(EXPERIMENT_DESCRIPTION_ATTRIBUTE, experiment.getDescription());

        model.addAttribute(HAS_EXTRA_INFO_ATTRIBUTE, experiment.hasExtraInfoFile());

        model.addAttribute(PUBMED_IDS_ATTRIBUTE, experiment.getPubMedIds());
    }

    private void extendModel(HttpServletRequest request, MicroarrayExperiment experiment) {
        request.setAttribute(QC_ARRAY_DESIGNS_ATTRIBUTE, experiment.getArrayDesignAccessions());

    }

}
