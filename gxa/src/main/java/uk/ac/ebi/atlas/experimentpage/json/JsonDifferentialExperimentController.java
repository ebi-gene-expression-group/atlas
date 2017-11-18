package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialProfilesHeatMap;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.differential.evidence.EvidenceService;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@Scope("request")
public class JsonDifferentialExperimentController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    private final
        DifferentialExperimentPageService<DifferentialExpression, DifferentialExperiment,
                DifferentialRequestPreferences, RnaSeqProfile, RnaSeqRequestContext>
            diffRnaSeqExperimentPageService;
    private final
        DifferentialExperimentPageService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestPreferences,
                MicroarrayProfile, MicroarrayRequestContext>
            diffMicroarrayExperimentPageService;

    private final
        EvidenceService<DifferentialExpression, DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile>
            diffRnaSeqEvidenceService;
    private final
        EvidenceService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile>
            diffMicroarrayEvidenceService;


    @Inject
    public JsonDifferentialExperimentController(ExperimentTrader experimentTrader,
                                                RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory,
                                                MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
                                                SolrQueryService solrQueryService,
                                                AtlasResourceHub atlasResourceHub,
                                                DataFileHub dataFileHub,
                                                Environment props) {
        super(experimentTrader);
        String resourcesVersion = props.getProperty("projectVersion");

        diffRnaSeqExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.RnaSeq(),
                        new DifferentialProfilesHeatMap<>(rnaSeqProfileStreamFactory, solrQueryService),
                        atlasResourceHub);

        diffMicroarrayExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.Microarray(),
                        new DifferentialProfilesHeatMap<>(microarrayProfileStreamFactory, solrQueryService),
                        atlasResourceHub);
        diffRnaSeqEvidenceService =
                new EvidenceService<>(rnaSeqProfileStreamFactory, dataFileHub, resourcesVersion);
        diffMicroarrayEvidenceService =
                new EvidenceService<>(microarrayProfileStreamFactory, dataFileHub, resourcesVersion);

    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    produces = "application/json;charset=UTF-8",
                    params = "type=MICROARRAY_ANY")
    @ResponseBody
    public String differentialMicroarrayExperimentData(
            @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(diffMicroarrayExperimentPageService.getResultsForExperiment(
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                accessKey, preferences));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    @ResponseBody
    public String differentialRnaSeqExperimentData(
            @ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(diffRnaSeqExperimentPageService.getResultsForExperiment(
                (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                accessKey, preferences));
    }

    /*
    View of experiment as set of evidence between genes and diseases, in the Open Targets format.
    Returns an empty response for experiments that are not about diseases or that we have no evidence for.
     */
    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            produces = "application/json-seq;charset=UTF-8",
            params = "type=MICROARRAY_ANY")
    public void differentialMicroarrayExperimentEvidence(
            @RequestParam(defaultValue = "0") Double logFoldChangeCutoff,
            @RequestParam(defaultValue = "1") Double pValueCutoff,
            @RequestParam(defaultValue = "1000") Integer maxGenesPerContrast,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey, HttpServletResponse response) throws IOException {
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        PrintWriter w = response.getWriter();
        diffMicroarrayEvidenceService.evidenceForExperiment(experiment,contrast -> {
            MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
            requestPreferences.setFoldChangeCutoff(logFoldChangeCutoff);
            requestPreferences.setCutoff(pValueCutoff);
            requestPreferences.setHeatmapMatrixSize(maxGenesPerContrast);
            requestPreferences.setSelectedColumnIds(ImmutableSet.of(contrast.getId()));
            return new MicroarrayRequestContext(requestPreferences, experiment);
        }, o -> w.println(gson.toJson(o)));

    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            produces = "application/json-seq;charset=UTF-8",
            params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    public void differentialRnaSeqExperimentEvidence(
            @RequestParam(defaultValue = "0") Double logFoldChangeCutoff,
            @RequestParam(defaultValue = "1") Double pValueCutoff,
            @RequestParam(defaultValue = "1000") Integer maxGenesPerContrast,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        PrintWriter w = response.getWriter();
        diffRnaSeqEvidenceService.evidenceForExperiment(experiment, contrast -> {
            DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
            requestPreferences.setFoldChangeCutoff(logFoldChangeCutoff);
            requestPreferences.setCutoff(pValueCutoff);
            requestPreferences.setHeatmapMatrixSize(maxGenesPerContrast);
            requestPreferences.setSelectedColumnIds(ImmutableSet.of(contrast.getId()));
            return new RnaSeqRequestContext(requestPreferences, experiment);
        }, o-> w.println(gson.toJson(o)));
    }
}
