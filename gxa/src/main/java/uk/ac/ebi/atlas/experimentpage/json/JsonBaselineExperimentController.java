package uk.ac.ebi.atlas.experimentpage.json;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatmapsWranglerFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.HistogramService;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Controller
@Scope("request")
public class JsonBaselineExperimentController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private final BaselineExperimentPageService rnaSeqBaselineExperimentPageService;
    private final BaselineExperimentPageService proteomicsBaselineExperimentPageService;
    private final SpeciesInferrer speciesInferrer;
    private final HistogramService.RnaSeq rnaSeqHistograms;
    private final HistogramService.Proteomics proteomicsHistograms;

    @Inject
    public JsonBaselineExperimentController(
            ExperimentTrader experimentTrader,
            CoexpressedGenesService coexpressedGenesService,
            SolrQueryService solrQueryService,
            RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory,
            ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory,
            SpeciesInferrer speciesInferrer) {
        super(experimentTrader);
        this.rnaSeqBaselineExperimentPageService = new BaselineExperimentPageService(new BaselineProfilesHeatmapsWranglerFactory(
                rnaSeqBaselineProfileStreamFactory, solrQueryService, coexpressedGenesService)
        );
        this.proteomicsBaselineExperimentPageService = new BaselineExperimentPageService(new BaselineProfilesHeatmapsWranglerFactory(
                proteomicsBaselineProfileStreamFactory, solrQueryService, coexpressedGenesService)
        );
        this.speciesInferrer = speciesInferrer;
        this.rnaSeqHistograms = new HistogramService.RnaSeq(rnaSeqBaselineProfileStreamFactory, experimentTrader);
        this.proteomicsHistograms = new HistogramService.Proteomics(proteomicsBaselineProfileStreamFactory, experimentTrader);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=RNASEQ_MRNA_BASELINE")
    @ResponseBody
    public String baselineRnaSeqExperimentData(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                rnaSeqBaselineExperimentPageService.getResultsForExperiment(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey), preferences
                ));
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
                    method = RequestMethod.GET,
                    produces = "application/json;charset=UTF-8",
                    params = "type=PROTEOMICS_BASELINE")
    @ResponseBody
    public String baselineProteomicsExperimentData(
            @Valid ProteomicsBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                proteomicsBaselineExperimentPageService.getResultsForExperiment(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                        preferences));
    }

    @RequestMapping(
            value = "/json/baseline_refexperiment",
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String jsonBaselineRefExperiment(
            @RequestParam(value = "geneQuery") SemanticQuery geneQuery,
            @RequestParam(value = "species", required = false) String speciesString,
            @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences, HttpServletRequest request) {

        //different default - reference experiments always had FPKMs, no need to change this now
        if(!request.getParameterMap().containsKey("unit")){
            preferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);
        }

        Species species = speciesInferrer.inferSpeciesForGeneQuery(geneQuery, speciesString);
        String experimentAccession = ApplicationProperties.getBaselineReferenceExperimentAccession(species);

        if (isBlank(experimentAccession)) {
            throw new ResourceNotFoundException("No reference baseline experiment for species " + speciesString);
        }

        return baselineRnaSeqExperimentData(preferences, experimentAccession, "");
    }

    public static final String GENE_DISTRIBUTION_URL = "/json/experiments/{experimentAccession}/genedistribution";

    public static final String geneDistributionUrl(HttpServletRequest request, String experimentAccession, String accessKey, ExperimentType experimentType){
        return request.getContextPath()
                +GENE_DISTRIBUTION_URL.replace("{experimentAccession}", experimentAccession)
                + "?experimentType="+experimentType.name()
                +(
                org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "&accessKey="+accessKey : ""
        );
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    @ResponseBody
    public String baselineRnaSeqHistogram(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                rnaSeqHistograms.get(experimentAccession, accessKey, preferences).asJson()
        );
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
            method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8",
            params = "type=PROTEOMICS_BASELINE")
    @ResponseBody
    public String baselineProteomicsHistogram(
            @Valid ProteomicsBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                proteomicsHistograms.get(experimentAccession, accessKey, preferences).asJson()
        );
    }



}
