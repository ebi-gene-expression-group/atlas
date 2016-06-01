package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.MessageFormat;

@Controller
@Scope("request")
public class RnaSeqExperimentDownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RnaSeqExperimentDownloadController.class);

    private static final String ALL_ANALYTICS_TSV = "-analytics.tsv";
    private static final String RAW_COUNTS_TSV = "-raw-counts.tsv";
    private static final String PARAMS_TYPE_DIFFERENTIAL = "type=RNASEQ_MRNA_DIFFERENTIAL";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";

    private final RnaSeqRequestContextBuilder requestContextBuilder;

    private RnaSeqProfilesWriter profilesWriter;

    private DataWriterFactory dataWriterFactory;

    private final ExperimentTrader experimentTrader;

    @Inject
    public RnaSeqExperimentDownloadController(
            RnaSeqRequestContextBuilder requestContextBuilder, RnaSeqProfilesWriter profilesWriter
            , DataWriterFactory dataWriterFactory, ExperimentTrader experimentTrader) {

        this.requestContextBuilder = requestContextBuilder;
        this.profilesWriter = profilesWriter;
        this.dataWriterFactory = dataWriterFactory;
        this.experimentTrader = experimentTrader;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadGeneProfiles(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey",required = false) String accessKey,
                                     HttpServletRequest request, @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid
                                         DifferentialRequestPreferences preferences , HttpServletResponse response) throws IOException {

        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);

        LOGGER.info("<downloadGeneProfiles> received download request for requestPreferences: {}", preferences);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experiment.getAccession() + "-query-results.tsv\"");

        response.setContentType("text/plain; charset=utf-8");


        RnaSeqRequestContext requestContext = requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

        try {

            long genesCount = profilesWriter.write(response.getWriter(), requestContext);
            LOGGER.info("<downloadGeneProfiles> streamed {} gene expression profiles", genesCount);

        } catch (GenesNotFoundException e) {
            LOGGER.info("<downloadGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/raw-counts.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadRawCounts(@PathVariable String experimentAccession,
                                  @RequestParam(value = "accessKey",required = false) String accessKey,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);
        prepareResponse(response, experiment.getAccession(), RAW_COUNTS_TSV);

        ExpressionsWriter rnaSeqRawDataWriter = dataWriterFactory.getRnaSeqRawDataWriter(experiment, response.getWriter());

        long genesCount = rnaSeqRawDataWriter.write();
        LOGGER.info("<download{}> streamed {} gene expression profiles", RAW_COUNTS_TSV, genesCount);
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = PARAMS_TYPE_DIFFERENTIAL)
    public void downloadAllAnalytics(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey",required = false) String accessKey,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        DifferentialExperiment experiment = (DifferentialExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);

        prepareResponse(response, experiment.getAccession(), ALL_ANALYTICS_TSV);

        ExpressionsWriter rnaSeqAnalyticsDataWriter = dataWriterFactory.getRnaSeqAnalyticsDataWriter(experiment, response.getWriter());

        long genesCount = rnaSeqAnalyticsDataWriter.write();
        LOGGER.info("<download{}> streamed {} gene expression profiles", ALL_ANALYTICS_TSV, genesCount);

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = PARAMS_TYPE_DIFFERENTIAL)
    public String downloadRdataURL(@PathVariable String experimentAccession,
                                   HttpServletRequest request) throws IOException {
        String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experimentAccession);

        return "forward:" + path;
    }

    private void prepareResponse(HttpServletResponse response, String experimentAccession, String fileExtension) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + fileExtension + "\"");
        response.setContentType("text/plain; charset=utf-8");
    }

}
