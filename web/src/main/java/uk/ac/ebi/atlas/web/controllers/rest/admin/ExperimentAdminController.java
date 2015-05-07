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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.experimentimport.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentMetadataCRUD;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

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

    private ExperimentCRUD experimentCRUD;
    private ExperimentMetadataCRUD experimentMetadataCRUD;
    private ExperimentTrader trader;

    @Inject
    public ExperimentAdminController(ExperimentCRUD experimentCRUD,
                                     ExperimentMetadataCRUD experimentMetadataCRUD, ExperimentTrader trader) {
        this.trader = trader;
        this.experimentCRUD = experimentCRUD;
        this.experimentMetadataCRUD = experimentMetadataCRUD;
    }

    @RequestMapping("/importExperiment")
    @ResponseBody
    public String importExperiment(@RequestParam("accession") String experimentAccession,
                                   @RequestParam(value = "private", defaultValue = "true") boolean isPrivate) throws IOException {
        UUID accessKeyUUID = experimentCRUD.importExperiment(experimentAccession, isPrivate);
        return "Experiment " + experimentAccession + " loaded, accessKey: " + accessKeyUUID;
    }

    // TODO This should be automatically done when an experiment is imported. See TODO note in ExperimentCRUD
    @RequestMapping("/serializeExpressionData")
    @ResponseBody
    public String serializeExpressionData(@RequestParam("accession") String experimentAccession) throws IOException {
        experimentCRUD.serializeExpressionData(experimentAccession);
        return "Expression data successfully serialized for " + experimentAccession;
    }

    @RequestMapping("/serializeAllBaselineExpressionData")
    @ResponseBody
    public String serializeAllBaselineExpressionData() throws IOException {
        for (String baselineExperimentAccession : trader.getAllBaselineExperimentAccessions()) {
            experimentCRUD.serializeExpressionData(baselineExperimentAccession);
        }
        return "All baseline experiments expression data successfully serialized.";
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String experimentAccession) {
        experimentCRUD.deleteExperiment(experimentAccession);
        return "Experiment " + experimentAccession + " successfully deleted.";
    }

    @RequestMapping("/deleteInactiveAnalytics")
    @ResponseBody
    public String deleteInactiveExpressions() {
        experimentCRUD.deleteInactiveAnalytics();
        return "Deleted all inactive analytics";
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

    @RequestMapping("/updateExperimentDesign")
    @ResponseBody
    public String updateExperimentDesign(@RequestParam("accession") String experimentAccession) {
        experimentMetadataCRUD.updateExperimentDesign(experimentAccession);
        return "Experiment design was updated for " + experimentAccession;
    }


    @RequestMapping("/invalidateExperimentCache")
    @ResponseBody
    public String invalidateExperimentCache() throws IOException {
        trader.removeAllExperimentsFromCache();
        return "All experiments removed from cache";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(Exception e) throws IOException {
        String lineSeparator = "<br>";
        LOGGER.error(e.getMessage(), e);

        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(lineSeparator);

        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append(lineSeparator);
        }

        return sb.toString();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFoundException(Exception e) throws IOException {
        LOGGER.error(e.getMessage(), e);
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }

}