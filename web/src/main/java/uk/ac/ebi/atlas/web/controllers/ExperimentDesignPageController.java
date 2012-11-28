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

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.model.readers.ExperimentDesignTsvReader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExperimentDesignPageController {

    private ExperimentDesignTsvReader experimentDesignTsvReader;
    private ExperimentsCache experimentsCache;

    @Inject
    public ExperimentDesignPageController(ExperimentDesignTsvReader experimentDesignTsvReader, ExperimentsCache experimentsCache) {
        this.experimentDesignTsvReader = experimentDesignTsvReader;
        this.experimentsCache = experimentsCache;
    }

    @RequestMapping("/experiments/{experimentAccession}-experiment-design")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        // read contents from file
        List<String[]> csvLines = new ArrayList<>(experimentDesignTsvReader.readAll(experimentAccession));
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

        // add table data to model
        model.addAttribute("tableHeader", header);
        model.addAttribute("tableData", data);

        // run accessions are used for highlighting
        Experiment experiment = experimentsCache.getExperiment(experimentAccession);
        String runAccessions = gson.toJson(experiment.getExperimentRunAccessions());
        model.addAttribute("runAccessions", runAccessions);

        // add general experiment attributes to model
        model.addAttribute("experimentAccession", experimentAccession);
        model.addAttribute("experimentDescription", experiment.getDescription());
        model.addAttribute("specie", experiment.getSpecie());

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
















