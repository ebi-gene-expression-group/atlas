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
import java.util.*;

@Controller
public class ExperimentDesignPageController {

    private ExperimentDesignTsvReader experimentDesignTsvReader;
    private ExperimentsCache experimentsCache;

    @Inject
    public ExperimentDesignPageController(ExperimentDesignTsvReader experimentDesignTsvReader, ExperimentsCache experimentsCache) {
        this.experimentDesignTsvReader = experimentDesignTsvReader;
        this.experimentsCache = experimentsCache;
    }

    /**
     * Extracts subcategories for a given category within the header line
     *
     * @param headerLine
     * @param category
     * @return
     */
    private TreeMap<String, Integer> extractSubcategories(String[] headerLine, String category) {
        TreeMap<String, Integer> map = new TreeMap<>();
        for (int i = 1; i < headerLine.length; i++) {
            if (headerLine[i].startsWith(category)) {
                String subcategory = headerLine[i].substring(category.length() + 1, headerLine[i].length() - 1);
                map.put(subcategory, i);
            }
        }
        return map;
    }

    @RequestMapping("/experiments/{experimentAccession}-experiment-design")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        // read contents from file
        List<String[]> csvLines = new ArrayList<>(experimentDesignTsvReader.readAll(experimentAccession));
        // delete first line with table headers
        String[] headerLine = csvLines.remove(0);

        // split header line into samples and factors
        TreeMap<String, Integer> samples = extractSubcategories(headerLine, "Sample Characteristics");
        TreeMap<String, Integer> factors = extractSubcategories(headerLine, "Factor Values");

        // reorder lines according to new header
        Map<Integer, Integer> mapping = new HashMap<>();
        mapping.put(0, 0);
        int i = 1;
        for (Integer value : samples.values()) {
            mapping.put(i, value);
            i++;
        }
        for (Integer value : factors.values()) {
            mapping.put(i, value);
            i++;
        }
        for (i = 0; i < csvLines.size(); i++) {
            String[] line = csvLines.get(i);
            String[] newLine = new String[line.length];
            for (int j = 0; j < line.length; j++) {
                newLine[j] = line[mapping.get(j)];
            }
            csvLines.set(i, newLine);
        }

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add table data to model
        model.addAttribute("assayHeader", headerLine[0]);
        model.addAttribute("samples", gson.toJson(samples));
        model.addAttribute("factors", gson.toJson(factors));
        model.addAttribute("tableData", gson.toJson(csvLines));

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

}
















