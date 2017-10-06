package uk.ac.ebi.atlas.experimentpage.json;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentpage.LinkToGene;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineExperimentPageService;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatmapsWranglerFactory;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineRequestPreferencesValidator;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.baseline.genedistribution.HistogramService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.json.ExternallyViewableProfilesList;
import uk.ac.ebi.atlas.profiles.stream.BaselineTranscriptProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesInferrer;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
public class JsonBaselineExperimentController extends JsonExperimentController {

    @InitBinder("preferences")
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new BaselineRequestPreferencesValidator());
    }

    private final BaselineExperimentPageService rnaSeqBaselineExperimentPageService;
    private final BaselineExperimentPageService proteomicsBaselineExperimentPageService;
    private final BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory;
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
            BaselineTranscriptProfileStreamFactory baselineTranscriptProfileStreamFactory,
            SpeciesInferrer speciesInferrer) {
        super(experimentTrader);
        this.rnaSeqBaselineExperimentPageService = new BaselineExperimentPageService(new BaselineProfilesHeatmapsWranglerFactory(
                rnaSeqBaselineProfileStreamFactory, solrQueryService, coexpressedGenesService)
        );
        this.proteomicsBaselineExperimentPageService = new BaselineExperimentPageService(new BaselineProfilesHeatmapsWranglerFactory(
                proteomicsBaselineProfileStreamFactory, solrQueryService, coexpressedGenesService)
        );
        this.baselineTranscriptProfileStreamFactory = baselineTranscriptProfileStreamFactory;
        this.speciesInferrer = speciesInferrer;
        this.rnaSeqHistograms = new HistogramService.RnaSeq(rnaSeqBaselineProfileStreamFactory, experimentTrader);
        this.proteomicsHistograms = new HistogramService.Proteomics(proteomicsBaselineProfileStreamFactory, experimentTrader);
    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqExperimentData(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                rnaSeqBaselineExperimentPageService.getResultsForExperiment(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                        accessKey, preferences
                ));
    }

    @RequestMapping(value = "/debug/json/experiments/{experimentAccession}/genes/{geneId}/transcripts",
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqTranscriptsDataWithVeryTemporaryImplementation(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @PathVariable String geneId,
            @RequestParam(defaultValue = "") String accessKey) {

        BaselineExperiment experiment = (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey);
        /*
        NOTE: incredibly obscure hack
        Reusing RnaSeqBaselineRequestPreferences is NOT okay
        there's a clash on what the kryo file should be
        setting cutoff to 0.0d makes the look somewhere else where we won't serialize the file
        and it all works :)
        It's enough to test this endpoint, there's no way to make a transcripts kryo file
        I think we possibly don't want one, and that the new code that reads first column without parsing makes reading tsv's
        >faster< than kryo's
        TODO: test this on a large experiment and baseline data
         */
        preferences.setCutoff(0.0);
        BaselineRequestContext<ExpressionUnit.Absolute.Rna> requestContext = new BaselineRequestContext<>(preferences, experiment);
        return gson.toJson(
                new ExternallyViewableProfilesList<>(
                        new GeneProfilesList<>(ImmutableList.copyOf(
                                new IterableObjectInputStream<>(baselineTranscriptProfileStreamFactory.create(
                                        experiment,
                                        requestContext,
                                        ImmutableSet.of(geneId)
                                ))
                        )),
                        new LinkToGene<>(),
                        requestContext.getDataColumnsToReturn(),
                        p -> requestContext.getExpressionUnit()
                ).asJson()
        );

    }

    @RequestMapping(value = "/json/experiments/{experimentAccession}",
            produces = "application/json;charset=UTF-8",
            params = "type=PROTEOMICS_BASELINE")
    public String baselineProteomicsExperimentData(
            @Valid ProteomicsBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        return gson.toJson(
                proteomicsBaselineExperimentPageService.getResultsForExperiment(
                        (BaselineExperiment) experimentTrader.getExperiment(experimentAccession, accessKey),
                        accessKey,
                        preferences));
    }

    @RequestMapping(
            value = "/json/baseline_refexperiment",
            produces = "application/json;charset=UTF-8")
    public String jsonBaselineRefExperiment(
            @RequestParam(value = "geneQuery") SemanticQuery geneQuery,
            @RequestParam(value = "species", required = false) String speciesString,
            @ModelAttribute("preferences") @Valid RnaSeqBaselineRequestPreferences preferences, HttpServletRequest request) {

        //different default - reference experiments always had FPKMs, no need to change this now
        if (!request.getParameterMap().containsKey("unit")) {
            preferences.setUnit(ExpressionUnit.Absolute.Rna.FPKM);
        }

        Species species = speciesInferrer.inferSpeciesForGeneQuery(geneQuery, speciesString);
        String experimentAccession = ApplicationProperties.getBaselineReferenceExperimentAccession(species);

        if (isBlank(experimentAccession)) {
            throw new ResourceNotFoundException("No reference baseline experiment for species " + speciesString);
        }

        return baselineRnaSeqExperimentData(preferences, experimentAccession, "");
    }

    public static final String GENE_DISTRIBUTION_URL = "json/experiments/{experimentAccession}/genedistribution";

    public static String geneDistributionUrl(String experimentAccession, String accessKey, ExperimentType experimentType) {
        return GENE_DISTRIBUTION_URL.replace("{experimentAccession}", experimentAccession)
                + "?experimentType=" + experimentType.name()
                + (
                org.apache.commons.lang.StringUtils.isNotEmpty(accessKey) ? "&accessKey=" + accessKey : ""
        );
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
            produces = "application/json;charset=UTF-8",
            params = "type=RNASEQ_MRNA_BASELINE")
    public String baselineRnaSeqHistogram(
            @Valid RnaSeqBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        BaselineRequestPreferences.setRequestAllData(preferences);
        return gson.toJson(
                rnaSeqHistograms.get(experimentAccession, accessKey, preferences).asJson()
        );
    }

    @RequestMapping(value = GENE_DISTRIBUTION_URL,
            produces = "application/json;charset=UTF-8",
            params = "type=PROTEOMICS_BASELINE")
    public String baselineProteomicsHistogram(
            @Valid ProteomicsBaselineRequestPreferences preferences,
            @PathVariable String experimentAccession,
            @RequestParam(defaultValue = "") String accessKey) {
        BaselineRequestPreferences.setRequestAllData(preferences);
        return gson.toJson(
                proteomicsHistograms.get(experimentAccession, accessKey, preferences).asJson()
        );
    }


}
