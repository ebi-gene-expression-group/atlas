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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentAdminController.class);

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

    @RequestMapping(value = "/importExperiment", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importExperiment(@RequestParam("accession") String experimentAccession,
                                   @RequestParam(value = "private", defaultValue = "true") boolean isPrivate) throws IOException {
        UUID accessKeyUUID = experimentCRUD.importExperiment(experimentAccession, isPrivate);
        return "Experiment " + experimentAccession + " loaded, accessKey: " + accessKeyUUID;
    }

    // TODO This should be automatically done when an experiment is imported. See TODO note in ExperimentCRUD
    @RequestMapping(value = "/serializeExpressionData", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String serializeExpressionData(@RequestParam("accession") String experimentAccession) throws IOException {
        experimentCRUD.serializeExpressionData(experimentAccession);
        return "Expression data successfully serialized for " + experimentAccession;
    }

    @RequestMapping(value = "/deserializeExpressionData", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String deserializeExpressionData(@RequestParam("accession") String experimentAccession) throws IOException {
        experimentCRUD.deserializeExpressionData(experimentAccession);
        return "Expression data successfully deserialized for " + experimentAccession;
    }

    @RequestMapping(value = "/serializeAllBaselineExpressionData", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String serializeAllBaselineExpressionData() throws IOException {
        for (String baselineExperimentAccession : trader.getBaselineExperimentAccessions()) {
            experimentCRUD.serializeExpressionData(baselineExperimentAccession);
        }
        return "All baseline experiments expression data successfully serialized";
    }

    @RequestMapping(value = "/deleteExperiment", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String experimentAccession) {
        experimentCRUD.deleteExperiment(experimentAccession);
        return "Experiment " + experimentAccession + " successfully deleted.";
    }

    @RequestMapping(value = "/deleteInactiveAnalytics", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteInactiveExpressions() {
        experimentCRUD.deleteInactiveAnalytics();
        return "Deleted all inactive analytics";
    }

    @RequestMapping(value = "/updateStatus", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updateExperiment(@RequestParam("accession") String experimentAccession,
                                   @RequestParam("private") boolean isPrivate) throws IOException {

        experimentMetadataCRUD.updateExperiment(experimentAccession, isPrivate);
        return "Experiment " + experimentAccession + " successfully updated.";
    }

    @RequestMapping(value = "/listExperiments", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
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

    @RequestMapping(value = "/updateAllExperimentDesigns", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updateAllExperimentDesigns() {
        int updatedExperimentsCount = experimentMetadataCRUD.updateAllExperimentDesigns();
        return "Experiment design was updated for " + updatedExperimentsCount + " experiments";
    }

    @RequestMapping(value = "/updateExperimentDesign", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updateExperimentDesign(@RequestParam("accession") String experimentAccession) {
        experimentMetadataCRUD.updateExperimentDesign(experimentAccession);
        return "Experiment design was updated for " + experimentAccession;
    }


    @RequestMapping(value = "/invalidateExperimentCache", produces="text/plain;charset=UTF-8")
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