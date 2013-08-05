/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.WriteMicroarrayProfilesCommand;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.DataWriterFactory;
import uk.ac.ebi.atlas.commands.download.ExpressionsWriter;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@Scope("request")
public class MicroarrayPageDownloadController {

    private static final Logger LOGGER = Logger.getLogger(MicroarrayPageDownloadController.class);
    private static final String NORMALIZED_EXPRESSIONS_TSV = "-normalized-expressions.tsv";
    private static final String LOG_FOLD_CHANGES_TSV = "-log-fold-changes.tsv";
    private static final String ANALYTICS_TSV = "-analytics.tsv";
    private static final String PARAMS_TYPE_MICROARRAY = "type=MICROARRAY";
    private static final String MODEL_ATTRIBUTE_PREFERENCES = "preferences";
    private static final String QUERY_RESULTS_TSV = "-query-results.tsv";

    private final MicroarrayRequestContextBuilder requestContextBuilder;

    private WriteMicroarrayProfilesCommand writeGeneProfilesCommand;

    private DataWriterFactory dataWriterFactory;

    // 4MB buffer
    private static final byte[] BUFFER = new byte[4096 * 1024];

    @Inject
    public MicroarrayPageDownloadController(
            MicroarrayRequestContextBuilder requestContextBuilder, WriteMicroarrayProfilesCommand writeGeneProfilesCommand, DataWriterFactory dataWriterFactory) {

        this.requestContextBuilder = requestContextBuilder;
        this.writeGeneProfilesCommand = writeGeneProfilesCommand;
        this.dataWriterFactory = dataWriterFactory;
    }

    @RequestMapping(value = "/experiments/{experimentAccession}.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadGeneProfiles(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        LOGGER.info("<downloadMicroarrayGeneProfiles> received download request for requestPreferences: " + preferences);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), QUERY_RESULTS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            preferences.setArrayDesignAccession(experiment.getArrayDesignAccessions().first());
            requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

            writeMicroarrayGeneProfiles(response.getWriter(), experiment);

        } else {

            List<File> tempFiles = Lists.newArrayList();

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + QUERY_RESULTS_TSV;

                // create a temp file
                File tempFile = File.createTempFile(filename, ".tmp");

                //(B) this is very broken. It's overriding the same parameter for every array design
                preferences.setArrayDesignAccession(selectedArrayDesign);
                requestContextBuilder.forExperiment(experiment).withPreferences(preferences).build();

                writeMicroarrayGeneProfiles(new PrintWriter(tempFile), experiment);

                tempFiles.add(tempFile);
            }

            writeTempFilesToZip(response, tempFiles);

        }

    }

    void writeMicroarrayGeneProfiles(PrintWriter writer, MicroarrayExperiment experiment) {
        writeGeneProfilesCommand.setResponseWriter(writer);

        try {

            long genesCount = writeGeneProfilesCommand.execute(experiment.getAccession());
            LOGGER.info("<writeMicroarrayGeneProfiles> wrote " + genesCount + " genes for experiment " + experiment.getAccession());

        } catch (GenesNotFoundException e) {
            LOGGER.info("<writeMicroarrayGeneProfiles> no genes found");
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/normalized.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadNormalizedData(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), NORMALIZED_EXPRESSIONS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            String selectedArrayDesign = experiment.getArrayDesignAccessions().first();

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                    selectedArrayDesign,
                    response.getWriter());

            long genesCount = writer.write();

            LOGGER.info("<download" + NORMALIZED_EXPRESSIONS_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            List<File> tempFiles = Lists.newArrayList();

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + NORMALIZED_EXPRESSIONS_TSV;

                // create a temp file
                File tempFile = File.createTempFile(filename, ".tmp");

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayRawDataWriter(experiment,
                        selectedArrayDesign,
                        new PrintWriter(tempFile));

                long genesCount = writer.write();

                LOGGER.info("<download" + NORMALIZED_EXPRESSIONS_TSV + "> zipped " + genesCount + " in " + filename);

                tempFiles.add(tempFile);
            }

            writeTempFilesToZip(response, tempFiles);
        }

    }

    @RequestMapping(value = "/experiments/{experimentAccession}/logFold.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadLogFoldData(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), LOG_FOLD_CHANGES_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            String selectedArrayDesign = experiment.getArrayDesignAccessions().first();

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                    selectedArrayDesign,
                    response.getWriter());

            long genesCount = writer.write();

            LOGGER.info("<download" + LOG_FOLD_CHANGES_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            List<File> tempFiles = Lists.newArrayList();

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + LOG_FOLD_CHANGES_TSV;

                // create a temp file
                File tempFile = File.createTempFile(filename, ".tmp");

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayLogFoldDataWriter(experiment,
                        selectedArrayDesign,
                        new PrintWriter(tempFile));

                long genesCount = writer.write();

                LOGGER.info("<download" + LOG_FOLD_CHANGES_TSV + "> zipped " + genesCount + " in " + filename);

                tempFiles.add(tempFile);
            }

            writeTempFilesToZip(response, tempFiles);
        }
    }

    @RequestMapping(value = "/experiments/{experimentAccession}/all-analytics.tsv", params = PARAMS_TYPE_MICROARRAY)
    public void downloadAllAnalytics(HttpServletRequest request
            , @ModelAttribute(MODEL_ATTRIBUTE_PREFERENCES) @Valid MicroarrayRequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        MicroarrayExperiment experiment = (MicroarrayExperiment) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        prepareResponse(response, experiment.getAccession(), experiment.getArrayDesignAccessions(), ANALYTICS_TSV);

        if (experiment.getArrayDesignAccessions().size() == 1) {

            String selectedArrayDesign = experiment.getArrayDesignAccessions().first();

            ExpressionsWriter writer = dataWriterFactory.getMicroarrayAnalyticsDataWriter(experiment,
                    selectedArrayDesign,
                    response.getWriter());

            long genesCount = writer.write();

            LOGGER.info("<download" + ANALYTICS_TSV + "> streamed " + genesCount + " gene expression profiles");

            writer.close();

        } else {

            List<File> tempFiles = Lists.newArrayList();

            for (String selectedArrayDesign : experiment.getArrayDesignAccessions()) {

                String filename = experiment.getAccession() + "_" + selectedArrayDesign + ANALYTICS_TSV;

                // create a temp file
                File tempFile = File.createTempFile(filename, ".tmp");

                ExpressionsWriter writer = dataWriterFactory.getMicroarrayAnalyticsDataWriter(experiment,
                        selectedArrayDesign,
                        new PrintWriter(tempFile));

                long genesCount = writer.write();

                LOGGER.info("<download" + ANALYTICS_TSV + "> zipped " + genesCount + " in " + filename);

                tempFiles.add(tempFile);
            }

            writeTempFilesToZip(response, tempFiles);
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

    private void writeTempFilesToZip(HttpServletResponse response, List<File> tempFiles) throws IOException {

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for (File tempFile : tempFiles) {

            int lastIndex = tempFile.getName().lastIndexOf(".tsv");

            String filename = tempFile.getName().substring(0, lastIndex + 4);

            ZipEntry ze = new ZipEntry(filename);

            zipOutputStream.putNextEntry(ze);

            FileInputStream fileInputStream = new FileInputStream(tempFile);
            copy(fileInputStream, zipOutputStream);
            fileInputStream.close();

            zipOutputStream.closeEntry();

            // cleanup
            tempFile.delete();
        }

        zipOutputStream.close();
    }

    /**
     * copy input to output stream - available in several StreamUtils or Streams classes
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(BUFFER)) != -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }

}