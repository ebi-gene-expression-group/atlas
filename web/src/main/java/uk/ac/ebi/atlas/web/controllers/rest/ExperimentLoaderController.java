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

import au.com.bytecode.opencsv.CSVWriter;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.expdesign.ExpDesignTsvWriter;
import uk.ac.ebi.atlas.expdesign.ExpDesignWriter;
import uk.ac.ebi.atlas.expdesign.ExpDesignWriterBuilder;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

@Controller
@Scope("request")
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);
    private ConfigurationDao configurationDao;
    private GeneProfileDao geneProfileDao;
    private ExpDesignTsvWriter expDesignTsvWriter;
    private ExpDesignWriterBuilder expDesignWriterBuilder;
    private TranscriptProfilesLoader transcriptProfileLoader;

    @Inject
    public ExperimentLoaderController(ConfigurationDao configurationDao,
                                      GeneProfileDao geneProfileDao,
                                      ExpDesignTsvWriter expDesignTsvWriter,
                                      ExpDesignWriterBuilder expDesignWriterBuilder,
                                      TranscriptProfilesLoader transcriptProfileLoader) {
        this.configurationDao = configurationDao;
        this.geneProfileDao = geneProfileDao;
        this.expDesignTsvWriter = expDesignTsvWriter;
        this.expDesignWriterBuilder = expDesignWriterBuilder;
        this.transcriptProfileLoader = transcriptProfileLoader;
    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String accession, @RequestParam("type") String type) {

        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<loadExperiment> Experiment accession cannot be empty.");
            return "Experiment accession cannot be empty.";
        }

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            LOGGER.error("<loadExperiment> Experiment with accession " + accession + " already exists.");
            return "Experiment with accession " + accession + " already exists.";
        }

        ExperimentType experimentType;
        try {
            experimentType = ExperimentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<loadExperiment> Illegal ExperimentType specified: " + e.getMessage());
            return "An unknown experiment type has been specified.";
        }

        ExpDesignWriter expDesignWriter = expDesignWriterBuilder.forExperimentType(experimentType).build();

        try (CSVWriter csvWriter = expDesignTsvWriter.forExperimentAccession(accession)) {
            expDesignWriter.forExperimentAccession(accession, csvWriter);
            csvWriter.flush();
            LOGGER.info("<loadExperiment> written ExpDesign file: " + expDesignTsvWriter.getFileAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("<loadExperiment> error writing to file: " + e.getMessage());
            return e.getMessage();
        } catch (ParseException e) {
            LOGGER.error("<loadExperiment> error parsing SDRF: " + e.getMessage());
            File expDesignTsv = new File(expDesignTsvWriter.getFileAbsolutePath());
            String outcomeDelete = expDesignTsv.delete() ? " success" : " fail";
            LOGGER.error("<loadExperiment> ExpDesign cleanup " + expDesignTsvWriter.getFileAbsolutePath() + outcomeDelete);
            return e.getMessage();
        }

        if (experimentType == ExperimentType.BASELINE) {
            try {
                StopWatch stopWatch = new StopWatch(getClass().getSimpleName());

                stopWatch.start();
                int count = transcriptProfileLoader.load(accession);
                stopWatch.stop();

                LOGGER.info("<loadExperiment> Inserted " + count + " transcript profiles and took " + stopWatch.getLastTaskInfo().getTimeSeconds() + "secs");
            } catch (IOException e) {
                LOGGER.error("<loadExperiment> error reading from file: " + e.getMessage());
                return e.getMessage();
            }
        }

        if (configurationDao.addExperimentConfiguration(accession, experimentType) == 1) {
            LOGGER.info("<loadExperiment> Experiment " + accession + " loaded.");
            return "Experiment " + accession + " loaded.";
        }

        LOGGER.error("<loadExperiment> An illegal state has been reached.");
        throw new IllegalStateException("<loadExperiment> An illegal state has been reached.");
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String accession) {

        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<deleteExperiment> Experiment accession cannot be empty.");
            return "Experiment accession cannot be empty.";
        }

        int returnValue = configurationDao.deleteExperimentConfiguration(accession);
        if (returnValue == 1) {
            if (geneProfileDao.deleteTranscriptProfilesForExperiment(accession) > 0) {
                LOGGER.info("<deleteExperiment> Transcripts for Experiment " + accession + " deleted.");
            }
            LOGGER.info("<deleteExperiment> Experiment " + accession + " deleted.");
            return "Experiment " + accession + " deleted.";
        } else if (returnValue == 0) {
            LOGGER.error("<deleteExperiment> Experiment " + accession + " not present.");
            return "Experiment " + accession + " not present.";
        }

        LOGGER.error("<deleteExperiment> An illegal state has been reached.");
        throw new IllegalStateException("<deleteExperiment> An illegal state has been reached.");
    }

    @RequestMapping("/listExperiments")
    @ResponseBody
    public String listExperiments() {
        Gson gson = new Gson();
        return gson.toJson(configurationDao.getExperimentConfigurations());
    }

}