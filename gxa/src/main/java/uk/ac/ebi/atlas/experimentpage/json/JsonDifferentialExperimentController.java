package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
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
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.validation.Valid;

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
                                                DataFileHub dataFileHub) {
        super(experimentTrader);

        diffRnaSeqExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.RnaSeq(),
                        new DifferentialProfilesHeatMap<>(rnaSeqProfileStreamFactory, solrQueryService),
                        atlasResourceHub);

        diffMicroarrayExperimentPageService =
                new DifferentialExperimentPageService<>(new DifferentialRequestContextFactory.Microarray(),
                        new DifferentialProfilesHeatMap<>(microarrayProfileStreamFactory, solrQueryService),
                        atlasResourceHub);
        diffRnaSeqEvidenceService =
                new EvidenceService<>(rnaSeqProfileStreamFactory, dataFileHub);
        diffMicroarrayEvidenceService =
                new EvidenceService<>(microarrayProfileStreamFactory, dataFileHub);

    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
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
                    method = RequestMethod.GET,
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
    Returns an empty array for experiments that are not about diseases.
     */
    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8",
            params = "type=MICROARRAY_ANY")
    @ResponseBody
    public String differentialMicroarrayExperimentEvidence(
            @ModelAttribute("preferences") @Valid MicroarrayRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        MicroarrayExperiment experiment = (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        return gson.toJson(diffMicroarrayEvidenceService.evidenceForExperiment(experiment,new MicroarrayRequestContext(preferences, experiment)));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}/evidence",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_DIFFERENTIAL")
    @ResponseBody
    public String differentialRnaSeqExperimentEvidence(
            @ModelAttribute("preferences") @Valid DifferentialRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        return gson.toJson(diffRnaSeqEvidenceService.evidenceForExperiment(experiment, new RnaSeqRequestContext(preferences, experiment)));
    }
}
