/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.web.controllers;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.model.readers.ExperimentDesignTsvReader;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Scope("request")
public class ExperimentDesignDownloadController {
    private static final Logger logger = Logger.getLogger(ExperimentDesignDownloadController.class);

    private ExperimentDesignTsvReader experimentDesignTsvReader;
    private ExperimentsCache experimentsCache;

    @Inject
    public ExperimentDesignDownloadController(ExperimentDesignTsvReader experimentDesignTsvReader, ExperimentsCache experimentsCache) {
        this.experimentDesignTsvReader = experimentDesignTsvReader;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}-experiment-design.tsv")
    public void downloadGeneProfiles(@PathVariable String experimentAccession
            , @ModelAttribute("preferences") @Valid RequestPreferences preferences
            , HttpServletResponse response) throws IOException {

        // read contents from file
        List<String[]> csvLines = new ArrayList<>(experimentDesignTsvReader.readAll(experimentAccession));
        List<String[]> newCsvLines = new ArrayList<>(csvLines.size());

        // get used runs from experiment
        Experiment experiment = experimentsCache.getExperiment(experimentAccession);
        Set<String> used = experiment.getExperimentRunAccessions();

        // modify header by adding new column
        String[] header = csvLines.remove(0);
        String[] newHeader = new String[header.length + 1];
        System.arraycopy(header, 0, newHeader, 0, header.length);
        newHeader[header.length] = "Analysed";
        newCsvLines.add(newHeader);

        // copy content and add used field
        for (String[] array : csvLines) {
            String[] newArray = new String[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            if (used.contains(newArray[0])) {
                newArray[array.length] = "Yes";
            } else {
                newArray[array.length] = "No";
            }
            newCsvLines.add(newArray);
        }

        response.setHeader("Content-Disposition", "attachment; filename=\"" + experimentAccession + "-experiment-design.tsv\"");

        response.setContentType("text/plain; charset=utf-8");

        CSVWriter csvWriter = new CSVWriter(response.getWriter(), '\t', CSVWriter.NO_QUOTE_CHARACTER);
        csvWriter.writeAll(newCsvLines);

        logger.debug("<downloadExperimentDesign> streamed " + newCsvLines.size() + " rows");

        csvWriter.flush();
        csvWriter.close();

    }

}
















