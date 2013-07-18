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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.experimentloader.ExperimentCRUD;
import uk.ac.ebi.atlas.experimentloader.ExperimentChecker;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;

@Controller
@Scope("request")
public class LoadExperimentsController {

    private static final Logger LOGGER = Logger.getLogger(LoadExperimentsController.class);

    private ExperimentChecker experimentChecker;
    private ExperimentCRUD experimentCRUD;


    @Inject
    public LoadExperimentsController(ExperimentChecker experimentChecker,
                                     ExperimentCRUD experimentCRUD) {
        this.experimentChecker = experimentChecker;
        this.experimentCRUD = experimentCRUD;
    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String accession, @RequestParam("type") ExperimentType experimentType) {

        try {
            experimentChecker.checkAllFilesPresent(accession, experimentType);

            experimentCRUD.importExperiment(accession, experimentType);

            return "Experiment " + accession + " loaded.";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String accession) {

        try{
            experimentCRUD.deleteExperiment(accession);
            return "Experiment " + accession + " successfully deleted.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/listExperiments")
    @ResponseBody
    public String listExperiments() {
        Gson gson = new Gson();
        return gson.toJson(experimentCRUD.findAllExperiments());

    }

}