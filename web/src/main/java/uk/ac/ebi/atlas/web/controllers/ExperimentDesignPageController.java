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

import au.com.bytecode.opencsv.CSVReader;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
public class ExperimentDesignPageController {

    private ApplicationProperties applicationProperties;
    private ExperimentsCache experimentsCache;

    @Inject
    public ExperimentDesignPageController(ApplicationProperties applicationProperties, ExperimentsCache experimentsCache) {
        this.applicationProperties = applicationProperties;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}-experiment-design")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        Path filePath = FileSystems.getDefault().getPath(applicationProperties.getExperimentDesignCsvFilePath(experimentAccession));

        Reader dataFileReader = new InputStreamReader(Files.newInputStream(filePath));

        CSVReader csvReader = new CSVReader(dataFileReader, '\t');

        List<String[]> csvLines = csvReader.readAll();
        // delete first line with table headers
        String[] headerLine = csvLines.remove(0);

        // convert table header into right data structure
        HeaderHelper[] headers = new HeaderHelper[headerLine.length];
        for (int i = 0; i < headerLine.length; i++) {
            if (i == 0)
                headers[i] = new HeaderHelper(headerLine[i], "");
            else
                headers[i] = new HeaderHelper(headerLine[i], "center");
        }

        // does the serialisation to JSON
        Gson gson = new Gson();
        String header = gson.toJson(headers);
        String data = gson.toJson(csvLines);

        model.addAttribute("experimentAccession", experimentAccession);
        model.addAttribute("tableHeader", header);
        model.addAttribute("tableData", data);

        Experiment experiment = experimentsCache.getExperiment(experimentAccession);
        String runAccessions = gson.toJson(experiment.getExperimentRunAccessions());
        model.addAttribute("runAccessions", runAccessions);

        String specie = experiment.getSpecie();
        model.addAttribute("specie", specie);
        model.addAttribute("experimentDescription", experiment.getDescription());

        return "experiment-experiment-design";
    }

    /**
     * Helper class for dynamic loading of table headers from file, gets serialized to JSON
     */
    private class HeaderHelper {

        public String sTitle;

        public String sClass;

        public HeaderHelper(String t, String c) {
            this.sTitle = t;
            this.sClass = c;
        }
    }

}
















