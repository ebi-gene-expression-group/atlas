package uk.ac.ebi.atlas.experimentpage.json.opentargets;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.differential.DifferentialRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.json.JsonExperimentController;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.stream.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

// View of experiment as set of evidence between genes and diseases, in the Open Targets format.
// Returns an empty response for experiments that are not about diseases or that we have no evidence for.
@Controller
@Scope("request")
public class OpenTargetsEvidenceController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new DifferentialRequestPreferencesValidator());
    }

    private final
        EvidenceService<DifferentialExpression, DifferentialExperiment, RnaSeqRequestContext, RnaSeqProfile>
            diffRnaSeqEvidenceService;
    private final
        EvidenceService<MicroarrayExpression, MicroarrayExperiment, MicroarrayRequestContext, MicroarrayProfile>
            diffMicroarrayEvidenceService;


    @Inject
    public OpenTargetsEvidenceController(ExperimentTrader experimentTrader,
                                         RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory,
                                         MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
                                         DataFileHub dataFileHub,
                                         Environment props) {
        super(experimentTrader);
        String resourcesVersion = props.getProperty("projectVersion");

        diffRnaSeqEvidenceService =
                new EvidenceService<>(rnaSeqProfileStreamFactory, dataFileHub, resourcesVersion);
        diffMicroarrayEvidenceService =
                new EvidenceService<>(microarrayProfileStreamFactory, dataFileHub, resourcesVersion);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            produces = "application/json-seq;charset=UTF-8",
            params = "type=MICROARRAY_ANY")
    public void differentialMicroarrayExperimentEvidence(
            @RequestParam(defaultValue = "0") double logFoldChangeCutoff,
            @RequestParam(defaultValue = "1") double pValueCutoff,
            @RequestParam(defaultValue = "-1") int maxGenesPerContrast,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey, HttpServletResponse response) throws IOException {
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        PrintWriter w = response.getWriter();
        diffMicroarrayEvidenceService.evidenceForExperiment(experiment, contrast -> {
            MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
            requestPreferences.setFoldChangeCutoff(logFoldChangeCutoff);
            requestPreferences.setCutoff(pValueCutoff);
            requestPreferences.setHeatmapMatrixSize(maxGenesPerContrast);
            requestPreferences.setSelectedColumnIds(ImmutableSet.of(contrast.getId()));
            return new MicroarrayRequestContext(requestPreferences, experiment);
        }, o -> w.println(GSON.toJson(o)));

    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            produces = "application/json-seq;charset=UTF-8",
            params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    public void differentialRnaSeqExperimentEvidence(
            @RequestParam(defaultValue = "0") double logFoldChangeCutoff,
            @RequestParam(defaultValue = "1") double pValueCutoff,
            @RequestParam(defaultValue = "-1") int maxGenesPerContrast,
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
        }, o-> w.println(GSON.toJson(o)));
    }
}
