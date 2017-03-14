package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.DifferentialRequestContextFactory;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqDifferentialProfilesWriterFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.Writer;

@Controller
@Scope("request")
public class RnaSeqExperimentDownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqExperimentDownloadController.class);

    private static final String ALL_ANALYTICS_TSV = "-analytics.tsv";
    private static final String RAW_COUNTS_TSV = "-raw-counts.tsv";
    private static final String PARAMS_TYPE_DIFFERENTIAL = "type=RNASEQ_MRNA_DIFFERENTIAL";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";

    private final RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory;

    private final RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory;

    private final SolrQueryService solrQueryService;

    private final ExperimentTrader experimentTrader;

    @Inject
    public RnaSeqExperimentDownloadController(
            RnaSeqProfileStreamFactory rnaSeqProfileStreamFactory,
            RnaSeqDifferentialProfilesWriterFactory rnaSeqDifferentialProfilesWriterFactory,
            SolrQueryService solrQueryService,
            ExperimentTrader experimentTrader) {
        this.rnaSeqProfileStreamFactory = rnaSeqProfileStreamFactory;
        this.rnaSeqDifferentialProfilesWriterFactory = rnaSeqDifferentialProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadGeneProfiles(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey", required = false) String accessKey,
                                     @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid
                                     DifferentialRequestPreferences preferences, HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: {}", preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");


        long genesCount = fetchAndWriteGeneProfiles(response.getWriter(), experiment, preferences);
        LOGGER.info("<downloadGeneProfiles> streamed {} gene expression profiles", genesCount);

    }

    long fetchAndWriteGeneProfiles(Writer responseWriter, DifferentialExperiment experiment, DifferentialRequestPreferences differentialRequestPreferences){
        RnaSeqRequestContext context =
                new DifferentialRequestContextFactory.RnaSeq().create(experiment, differentialRequestPreferences);
        return rnaSeqProfileStreamFactory.write(
                experiment,
                context,
                new DifferentialProfileStreamTransforms<RnaSeqProfile>(context,
                        solrQueryService.fetchResponse(context.getGeneQuery(), experiment.getSpecies().getReferenceName())),
                rnaSeqDifferentialProfilesWriterFactory.create(responseWriter, context));
    }
}
