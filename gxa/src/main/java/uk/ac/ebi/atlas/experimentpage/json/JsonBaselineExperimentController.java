package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.HistogramService;
import uk.ac.ebi.atlas.experimentpage.baseline.profiles.BaselineExperimentProfilesService;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;
import static uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences.VERY_SMALL_NON_ZERO_VALUE;

@RestController
public class JsonBaselineExperimentController extends JsonExperimentController {
    private final BaselineExperimentPageService baselineExperimentPageService;
    private final SpeciesInferrer speciesInferrer;
    private final HistogramService.RnaSeq rnaSeqHistograms;
    private final HistogramService.Proteomics proteomicsHistograms;

    public
    JsonBaselineExperimentController(ExperimentTrader experimentTrader,
                                     CoexpressedGenesService coexpressedGenesService,
                                     BaselineExperimentProfilesService baselineExperimentProfilesService,
                                     RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory,
                                     ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory,
                                     SpeciesInferrer speciesInferrer) {
        super(experimentTrader);

        this.baselineExperimentPageService =
                new BaselineExperimentPageService(baselineExperimentProfilesService, coexpressedGenesService);

        this.rnaSeqHistograms =
                new HistogramService.RnaSeq(rnaSeqBaselineProfileStreamFactory, experimentTrader);

        this.proteomicsHistograms =
                new HistogramService.Proteomics(proteomicsBaselineProfileStreamFactory, experimentTrader);

        this.speciesInferrer = speciesInferrer;
    }

    private String baselineExperimentData(BaselineRequestPreferences<? extends ExpressionUnit.Absolute> preferences,
                                          String experimentAccession,
                                          String accessKey) {
        return GSON.toJson(
                baselineExperimentPageService.getResultsForExperiment(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                        accessKey,
                        preferences));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqExperimentData(@Valid RnaSeqBaselineRequestPreferences preferences,
                                               @PathVariable String experimentAccession,
                                               @RequestParam(defaultValue = "") String accessKey) {
        return baselineExperimentData(preferences, experimentAccession, accessKey);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    produces = "application/json;charset=UTF-8",
                    params = "type=PROTEOMICS_BASELINE")
    public String baselineProteomicsExperimentData(@Valid ProteomicsBaselineRequestPreferences preferences,
                                                   @PathVariable String experimentAccession,
                                                   @RequestParam(defaultValue = "") String accessKey) {
        return baselineExperimentData(preferences, experimentAccession, accessKey);
    }

    @RequestMapping(value = "/json/baseline_refexperiment", produces = "application/json;charset=UTF-8")
    public String jsonBaselineRefExperiment(
            @RequestParam(value = "geneQuery") SemanticQuery geneQuery,
            @RequestParam(value = "species", required = false, defaultValue = "") String speciesString,
            @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences,
            HttpServletRequest request) {

        //different default - reference experiments always had FPKMs, no need to change this now
        if (!request.getParameterMap().containsKey("unit")) {
            preferences.setUnit(ExpressionUnit.Absolute.Rna.TPM);
        }

        Species species = speciesInferrer.inferSpeciesForGeneQuery(geneQuery, speciesString);
        String experimentAccession = ApplicationProperties.getBaselineReferenceExperimentAccession(species);

        if (isBlank(experimentAccession)) {
            throw new ResourceNotFoundException("No reference baseline experiment for species " + speciesString);
        }

        return baselineRnaSeqExperimentData(preferences, experimentAccession, "");
    }

    static final String GENE_DISTRIBUTION_URL = "json/experiments/{experimentAccession}/genedistribution";
    public static String geneDistributionUrl(String experimentAccession,
                                             String accessKey,
                                             ExperimentType experimentType) {
        return GENE_DISTRIBUTION_URL.replace("{experimentAccession}", experimentAccession)
                + "?experimentType=" + experimentType.name()
                + (isNotEmpty(accessKey) ? "&accessKey=" + accessKey : "");
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqHistogram(@Valid RnaSeqBaselineRequestPreferences preferences,
                                          @PathVariable String experimentAccession,
                                          @RequestParam(defaultValue = "") String accessKey) {
        preferences.setCutoff(VERY_SMALL_NON_ZERO_VALUE);
        preferences.setGeneQuery(SemanticQuery.create());

        return GSON.toJson(rnaSeqHistograms.get(experimentAccession, accessKey, preferences).asJson());
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
                    produces = "application/json;charset=UTF-8",
                    params = "type=PROTEOMICS_BASELINE")
    public String baselineProteomicsHistogram(@Valid ProteomicsBaselineRequestPreferences preferences,
                                              @PathVariable String experimentAccession,
                                              @RequestParam(defaultValue = "") String accessKey) {
        preferences.setCutoff(VERY_SMALL_NON_ZERO_VALUE);
        preferences.setGeneQuery(SemanticQuery.create());

        return GSON.toJson(proteomicsHistograms.get(experimentAccession, accessKey, preferences).asJson());
    }

}
