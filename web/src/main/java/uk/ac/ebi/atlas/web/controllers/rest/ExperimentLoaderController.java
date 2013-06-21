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

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.configuration.ExperimentManager;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);
    private ConfigurationDao configurationDao;
    private GeneProfileDao geneProfileDao;
    private ExperimentManager experimentManager;


    @Inject
    public ExperimentLoaderController(ConfigurationDao configurationDao,
                                      GeneProfileDao geneProfileDao,
                                      ExperimentManager experimentManager) {
        this.configurationDao = configurationDao;
        this.geneProfileDao = geneProfileDao;
        this.experimentManager = experimentManager;

    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String accession, @RequestParam("type") String type) {

        try {
            ExperimentType experimentType = checkAccessionAndType(accession, type);

            experimentManager.generateExpDesign(accession, experimentType);

            if (experimentType == ExperimentType.BASELINE) {
                experimentManager.loadTranscripts(accession);
            }

            if (configurationDao.addExperimentConfiguration(accession, experimentType) == 1) {
                LOGGER.info("<loadExperiment> Experiment " + accession + " loaded.");
                return "Experiment " + accession + " loaded.";
            }

        } catch (IllegalStateException e) {
            return e.getMessage();
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

    protected ExperimentType checkAccessionAndType(String accession, String type) {
        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<loadExperiment> Experiment accession cannot be empty.");
            throw new IllegalStateException("Experiment accession cannot be empty.");
        }

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            LOGGER.error("<loadExperiment> Experiment with accession " + accession + " already exists.");
            throw new IllegalStateException("Experiment with accession " + accession + " already exists.");
        }

        ExperimentType experimentType;
        try {
            experimentType = ExperimentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<loadExperiment> Illegal ExperimentType specified: " + e.getMessage());
            throw new IllegalStateException("An unknown experiment type has been specified.");
        }

        return experimentType;
    }

}