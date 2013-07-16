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
import uk.ac.ebi.atlas.configuration.ExperimentChecker;
import uk.ac.ebi.atlas.configuration.ExperimentManager;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ExperimentLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentLoaderController.class);

    private ConfigurationDao configurationDao;
    private GeneProfileDao geneProfileDao;
    private ExperimentChecker experimentChecker;
    private ExperimentManager experimentManager;


    @Inject
    public ExperimentLoaderController(ConfigurationDao configurationDao,
                                      GeneProfileDao geneProfileDao,
                                      ExperimentChecker experimentChecker,
                                      ExperimentManager experimentManager) {
        this.configurationDao = configurationDao;
        this.geneProfileDao = geneProfileDao;
        this.experimentChecker = experimentChecker;
        this.experimentManager = experimentManager;
    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String accession, @RequestParam("type") String type) {

        try {
            ExperimentType experimentType = experimentChecker.checkAccessionAndType(accession, type);

            experimentChecker.checkAllFilesPresent(accession, experimentType);

            experimentManager.generateExpDesign(accession, experimentType);

            switch (experimentType) {
                case BASELINE:
                    experimentManager.loadTranscripts(accession);
                    break;
                case MICROARRAY:
                case TWOCOLOUR:
                    experimentManager.loadArrayDesign(accession, ArrayDesignType.MICRO_ARRAY);
                    break;
                case MICRORNA:
                    experimentManager.loadArrayDesign(accession, ArrayDesignType.MICRO_RNA);
                    break;
            }

            if (! configurationDao.addExperimentConfiguration(accession, experimentType)) {
                return "Failure storing configuration for experiment " + accession;
            }
            return "Experiment " + accession + " loaded.";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String accession) {

        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<deleteExperiment> Experiment accession cannot be empty.");
            return "Experiment accession cannot be empty.";
        }

        if (configurationDao.deleteExperimentConfiguration(accession)) {
            int deletedTranscriptsCount = geneProfileDao.deleteTranscriptProfilesForExperiment(accession);
            return "Experiment " + accession + " deleted. " + deletedTranscriptsCount + " transcript profiles deleted for the given experiment.";
        } else {
            return "Experiment " + accession + " not present.";
        }
    }

    @RequestMapping("/listExperiments")
    @ResponseBody
    public String listExperiments() {
        Gson gson = new Gson();
        return gson.toJson(configurationDao.getExperimentConfigurations());
    }

}