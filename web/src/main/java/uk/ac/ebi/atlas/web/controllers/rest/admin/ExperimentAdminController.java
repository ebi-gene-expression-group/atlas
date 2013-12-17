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

package uk.ac.ebi.atlas.web.controllers.rest.admin;

import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentloader.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.experimentloader.ExperimentChecker;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@Scope("request")
@RequestMapping("/admin")
public class ExperimentAdminController {

    private static final Logger LOGGER = Logger.getLogger(ExperimentAdminController.class);

    private ExperimentChecker experimentChecker;
    private ExperimentMetadataCRUD experimentMetadataCRUD;


    @Inject
    public ExperimentAdminController(ExperimentChecker experimentChecker,
                                     ExperimentMetadataCRUD experimentMetadataCRUD) {
        this.experimentChecker = experimentChecker;
        this.experimentMetadataCRUD = experimentMetadataCRUD;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String experimentAccession,
                                 @RequestParam(value = "private", defaultValue = "true") boolean isPrivate) throws IOException {


        experimentChecker.checkConfigurationFilePermissions(experimentAccession);

        ExperimentConfiguration configuration = experimentMetadataCRUD.loadExperimentConfiguration(experimentAccession);

        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());

        UUID accessKeyUUID = experimentMetadataCRUD.importExperiment(experimentAccession, configuration, isPrivate);

        return "Experiment " + experimentAccession + " loaded, accessKey: " + accessKeyUUID;
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String experimentAccession) {

        experimentMetadataCRUD.deleteExperiment(experimentAccession);
        return "Experiment " + experimentAccession + " successfully deleted.";
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public String updateExperiment(@RequestParam("accession") String experimentAccession,
                                   @RequestParam("private") boolean isPrivate) throws IOException {

        experimentMetadataCRUD.updateExperiment(experimentAccession, isPrivate);
        return "Experiment " + experimentAccession + " successfully updated.";
    }

    @RequestMapping(value = "/listExperiments", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String listExperiments(@RequestParam(value = "accession", required = false) Set<String> experimentAccessions) {
        List<ExperimentDTO> experiments;
        if (CollectionUtils.isEmpty(experimentAccessions)) {
            experiments = experimentMetadataCRUD.findAllExperiments();
        } else {
            experiments = experimentMetadataCRUD.findExperiments(experimentAccessions);
        }
        return new Gson().toJson(experiments);

    }

    @RequestMapping("/updateAllExperimentDesigns")
    @ResponseBody
    public String updateAllExperimentDesigns() {
        int updatedExperimentsCount = experimentMetadataCRUD.updateAllExperimentDesigns();
        return "Experiment design was updated for " + updatedExperimentsCount + " experiments";
    }

    @RequestMapping("/updateAllExperiments")
    @ResponseBody
    public String updateAllExperiments() throws IOException {
        int updatedExperimentsCount = experimentMetadataCRUD.updateAllExperiments();
        return "Experiment design was updated for " + updatedExperimentsCount + " experiments";
    }

}