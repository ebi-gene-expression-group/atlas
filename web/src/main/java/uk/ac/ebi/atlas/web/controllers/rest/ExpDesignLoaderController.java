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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.expdesign.ExpDesignTsvWriter;
import uk.ac.ebi.atlas.expdesign.ExpDesignWriter;
import uk.ac.ebi.atlas.expdesign.ExpDesignWriterBuilder;

import javax.inject.Inject;
import java.io.IOException;

@Controller
@Scope("request")
public class ExpDesignLoaderController {

    private static final Logger LOGGER = Logger.getLogger(ExpDesignLoaderController.class);

    private ExpDesignTsvWriter expDesignTsvWriter;

    private ExpDesignWriterBuilder expDesignWriterBuilder;

    @Inject
    public ExpDesignLoaderController(ExpDesignTsvWriter expDesignTsvWriter, ExpDesignWriterBuilder expDesignWriterBuilder) {
        this.expDesignTsvWriter = expDesignTsvWriter;
        this.expDesignWriterBuilder = expDesignWriterBuilder;
    }

    @RequestMapping(value = "/loadExperimentDesign/{experimentAccession}")
    @ResponseBody
    public String loadExpDesign(@PathVariable String experimentAccession) {

        ExpDesignWriter expDesignWriter = expDesignWriterBuilder.forExperimentAccession(experimentAccession).build();

        try {
            CSVWriter csvWriter = expDesignTsvWriter.forExperimentAccession(experimentAccession);
            expDesignWriter.forExperimentAccession(experimentAccession, csvWriter);
            csvWriter.flush();
            csvWriter.close();
            LOGGER.info("<loadExpDesign> written ExpDesign file: " + expDesignTsvWriter.getFileAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("<loadExpDesign> error writing to file: " + e.getMessage());
            return e.getMessage();
        } catch (ParseException e) {
            LOGGER.error("<loadExpDesign> error parsing SDRF: " + e.getMessage());
            return e.getMessage();
        }

        return "ExperimentDesign for " + experimentAccession + " loaded.";
    }

}