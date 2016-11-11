package uk.ac.ebi.atlas.experimentpage.differential.download;

import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@Scope("request")
public class MicroarrayExperimentDownloadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicroarrayExperimentDownloadController.class);

    private static final String NORMALIZED_EXPRESSIONS_TSV = "-normalized-expressions.tsv";
    private static final String LOG_FOLD_CHANGES_TSV = "-log-fold-changes.tsv";
    private static final String ANALYTICS_TSV = "-analytics.tsv";
    private static final String PARAMS_TYPE_MICROARRAY = "type=MICROARRAY_ANY";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";
    private static final String QUERY_RESULTS_TSV = "-query-results.tsv";

    private final MicroarrayRequestContextBuilder requestContextBuilder;

    private MicroarrayProfilesWriter profilesWriter;

    private DataWriterFactory dataWriterFactory;

    private final ExperimentTrader experimentTrader;

    @Inject
    public MicroarrayExperimentDownloadController(ExperimentTrader experimentTrader,
            MicroarrayRequestContextBuilder requestContextBuilder, MicroarrayProfilesWriter profilesWriter, DataWriterFactory dataWriterFactory) {
        this.experimentTrader = experimentTrader;
        this.requestContextBuilder = requestContextBuilder;
        this.profilesWriter = profilesWriter;
        this.dataWriterFactory = dataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadGeneProfiles(
            HttpServletRequest request, @PathVariable String experimentAccession,
            @RequestParam(value = "accessKey", required = false) String accessKey,
            @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences, HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);


        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: {}", preferences);

        prepareResponse(response, experimentAccession, experiment.getArrayDesignAccessions(), QUERY_RESULTS_TSV);

        MicroarrayRequestContext requestContext = requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

        if (experiment.getArrayDesignAccessions().size() == 1) {

            String arrayDesign = experiment.getArrayDesignAccessions().first();

            writeMicroarrayGeneProfiles(response.getWriter(), experimentAccession, requestContext, arrayDesign);

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {
                String filename = experiment.getAccession() + "_" + selectedArrayDesign + QUERY_RESULTS_TSV;

                ZipEntry ze = new ZipEntry(filename);
                zipOutputStream.putNextEntry(ze);

                writeMicroarrayGeneProfiles(new PrintWriter(zipOutputStream), experimentAccession, requestContext, selectedArrayDesign);
                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-atlasExperimentSummary.Rdata", params = PARAMS_TYPE_MICROARRAY)
    public String downloadRdataURL(@PathVariable String experimentAccession) throws
            IOException {

        String path = MessageFormat.format("/expdata/{0}/{0}-atlasExperimentSummary.Rdata", experimentAccession);

        return "forward:" + path;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/{experimentAccession}-heatmap.pdf", params = PARAMS_TYPE_MICROARRAY)
    public String downloadPdf(@PathVariable String experimentAccession) throws IOException {

        String path = MessageFormat.format("/expdata/{0}/{0}-heatmap.pdf", experimentAccession);

        return "forward:" + path;
    }


    private void writeMicroarrayGeneProfiles(PrintWriter writer, String experimentAccession, MicroarrayRequestContext
            requestContext, String arrayDesign){
        try {

            long genesCount = profilesWriter.write(writer, requestContext, arrayDesign);
            LOGGER.info("<writeMicroarrayGeneProfiles> wrote {} genes for experiment {}", genesCount, experimentAccession);

        } catch (GenesNotFoundException e) {
            LOGGER.info("<writeMicroarrayGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/normalized.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadNormalizedData(HttpServletRequest request, @PathVariable String experimentAccession
            , @RequestParam(value = "accessKey",required = false) String accessKey,
              @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);
        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), NORMALIZED_EXPRESSIONS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                    response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download{}> streamed {} gene expression profiles", NORMALIZED_EXPRESSIONS_TSV, genesCount);

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + NORMALIZED_EXPRESSIONS_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                        new PrintWriter(zipOutputStream),selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download{}> zipped {} in {}", NORMALIZED_EXPRESSIONS_TSV, genesCount, filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/logFold.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadLogFoldData(HttpServletRequest request, @PathVariable String experimentAccession
            , @RequestParam(value = "accessKey",required = false) String accessKey,@ModelAttribute
                                                (MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment)
                experimentTrader.getExperiment(experimentAccession,accessKey);
        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), LOG_FOLD_CHANGES_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                    response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download{}> streamed {} gene expression profiles", LOG_FOLD_CHANGES_TSV, genesCount);

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + LOG_FOLD_CHANGES_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                        new PrintWriter(zipOutputStream), selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download{}> zipped {} in {}", LOG_FOLD_CHANGES_TSV, genesCount, filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadAllAnalytics(HttpServletRequest request, @PathVariable String experimentAccession
            , @RequestParam(value = "accessKey",required = false) String accessKey,@ModelAttribute
                                                 (MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment)
                experimentTrader.getExperiment(experimentAccession, accessKey);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), ANALYTICS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            ExpressionsWriter writer = dataWriterFactory
                    .getMicroarrayAnalyticsDataWriter(experiment, response.getWriter(), experiment.getArrayDesignAccessions().first());

            long genesCount = writer.write();

            LOGGER.info("<download{}> streamed gene expression profiles", ANALYTICS_TSV, genesCount);

            writer.close();

        } else {

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + ANALYTICS_TSV;

                ZipEntry ze = new ZipEntry(filename);

                zipOutputStream.putNextEntry(ze);

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayAnalyticsDataWriter(experiment,
                        new PrintWriter(zipOutputStream),selectedArrayDesign);

                long genesCount = writer.write();

                LOGGER.info("<download{}> zipped {} in {}", ANALYTICS_TSV, genesCount, filename);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }

    }

    private void prepareResponse(HttpServletResponse response, String experimentAccession, Set<String> arrayDesignAccessions, String fileExtension) {

        if (arrayDesignAccessions.size() > 1) {

            response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + fileExtension + ".zip\"");
            response.setContentType("application/octet-stream");

        } else {

            String arrayDesignAccession = arrayDesignAccessions.iterator().next();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "_" + arrayDesignAccession + fileExtension + "\"");
            response.setContentType("text/plain; charset=utf-8");

        }
    }

}