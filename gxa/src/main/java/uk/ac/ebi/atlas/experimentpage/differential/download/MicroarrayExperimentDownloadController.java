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
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamTransforms;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesWriterFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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

    private final MicroarrayProfileStreamFactory microarrayProfileStreamFactory;

    private final MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory;

    private final SolrQueryService solrQueryService;

    private final ExperimentTrader experimentTrader;

    @Inject
    public MicroarrayExperimentDownloadController(ExperimentTrader experimentTrader,
                                                  MicroarrayProfileStreamFactory microarrayProfileStreamFactory,
                                                  MicroarrayProfilesWriterFactory microarrayProfilesWriterFactory,
                                                  SolrQueryService solrQueryService) {
        this.experimentTrader = experimentTrader;
        this.microarrayProfileStreamFactory = microarrayProfileStreamFactory;
        this.microarrayProfilesWriterFactory = microarrayProfilesWriterFactory;
        this.solrQueryService = solrQueryService;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadGeneProfiles(@PathVariable String experimentAccession,
                                     @RequestParam(value = "accessKey", required = false) String accessKey,
                                     @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES)
                                         @Valid MicroarrayRequestPreferences preferences,
                                     HttpServletResponse response)
    throws IOException {

        MicroarrayExperiment experiment =
                (MicroarrayExperiment) experimentTrader.getExperiment(experimentAccession,accessKey);

        //TODO code is not using species for microarray experiments - I think it's wrong
        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse(preferences.getGeneQuery(), "");
        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: {}", preferences);

        prepareResponse(response, experimentAccession, experiment.getArrayDesignAccessions(), QUERY_RESULTS_TSV);


        if (experiment.getArrayDesignAccessions().size() == 1) {
            fetchAndWrite(response.getWriter(), experiment, preferences, geneQueryResponse);
        } else {
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {
                String filename = experiment.getAccession() + "_" + selectedArrayDesign + QUERY_RESULTS_TSV;

                ZipEntry ze = new ZipEntry(filename);
                zipOutputStream.putNextEntry(ze);

                preferences.setArrayDesignAccession(selectedArrayDesign);
                fetchAndWrite(new PrintWriter(zipOutputStream), experiment, preferences, geneQueryResponse);

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
        }

    }

    // convenience method for test
    long fetchAndWriteGeneProfiles(Writer responseWriter, MicroarrayExperiment experiment, MicroarrayRequestPreferences
            preferences){
        return fetchAndWrite(responseWriter, experiment, preferences, solrQueryService.fetchResponse(preferences
                .getGeneQuery(), ""));
    }

    long fetchAndWrite(Writer responseWriter, MicroarrayExperiment experiment, MicroarrayRequestPreferences
            preferences, GeneQueryResponse geneQueryResponse){
        MicroarrayRequestContext context =
                new DifferentialRequestContextFactory.Microarray().create(experiment, preferences);
        return microarrayProfileStreamFactory.write(
                experiment,
                context,
                new DifferentialProfileStreamTransforms<MicroarrayProfile>(context, geneQueryResponse),
                microarrayProfilesWriterFactory.create(responseWriter, context));
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