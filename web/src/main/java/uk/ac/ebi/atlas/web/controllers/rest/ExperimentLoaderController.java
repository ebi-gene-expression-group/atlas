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

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.atlas.configuration.ConfigurationDao;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;

@Controller
@Scope("request")
public class ExperimentLoaderController {

    private ConfigurationDao configurationDao;

    @Inject
    public ExperimentLoaderController(ConfigurationDao configurationDao) {
        this.configurationDao = configurationDao;
    }

    @RequestMapping("/loadExperiment")
    @ResponseBody
    public String loadExperiment(@RequestParam("accession") String accession, @RequestParam("type") String type) {

        if (StringUtils.isEmpty(accession)) {
            return "Experiment accession cannot be empty.";
        }

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            return "Experiment with accession " + accession + " already exists.";
        }

        ExperimentType experimentType;
        try {
            experimentType = ExperimentType.valueOf(type);
        } catch (IllegalArgumentException iae) {
            return "An unknown experiment type has been specified.";
        }

        if (configurationDao.addExperimentConfiguration(accession, experimentType) == 1) {
            return "Experiment " + accession + " loaded.";
        }

        throw new IllegalStateException("<loadExperiment> An illegal state has been reached.");
    }

    @RequestMapping("/deleteExperiment")
    @ResponseBody
    public String deleteExperiment(@RequestParam("accession") String accession) {

        if (StringUtils.isEmpty(accession)) {
            return "Experiment accession cannot be empty.";
        }

        int returnValue = configurationDao.deleteExperimentConfiguration(accession);
        if (returnValue == 1) {
            return "Experiment " + accession + " deleted.";
        } else if (returnValue == 0) {
            return "Experiment " + accession + " not present.";
        }

        throw new IllegalStateException("<deleteExperiment> An illegal state has been reached.");
    }

}