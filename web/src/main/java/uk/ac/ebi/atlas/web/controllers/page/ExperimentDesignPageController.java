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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderImpl;
import uk.ac.ebi.atlas.model.Experiment;

import java.util.*;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ExperimentDesignPageController {

    private final TsvReader experimentDesignTsvReader;

    public ExperimentDesignPageController(String pathTemplate) {
        this.experimentDesignTsvReader = new TsvReaderImpl(pathTemplate);
    }

    protected void extractExperimentDesign(Model model, Experiment experiment, Set<String> libraries) {
        // read contents from file
        List<String[]> csvLines = new ArrayList<>(experimentDesignTsvReader.readAll(experiment.getAccession()));
        // delete first line with table headers
        String[] headerLine = csvLines.remove(0);

        // split header line into samples and factors
        Map<String, Integer> samples = extractSubcategories(headerLine, "Sample Characteristics");
        Map<String, Integer> factors = extractSubcategories(headerLine, "Factor Values");

        // reorder lines according to new header
        Map<Integer, Integer> mapping = createReorderMapping(samples, factors);

        for (String[] line : csvLines) {
            String[] copy = Arrays.copyOf(line, line.length);
            for (int j = 0; j < copy.length; j++) {
                Integer value = mapping.get(j);
                checkNotNull(value, "No mapping found for ExpDesign column " + j);
                // here re-ordering of each line
                checkElementIndex(j, line.length, "ExpDesign column " + j + " is outside legal range.");
                checkElementIndex(value, copy.length, "Mapped index for ExpDesign column " + j + " is " + value + ", but is outside legal range.");
                line[j] = copy[value];
            }
        }

        // does the serialisation to JSON
        Gson gson = new Gson();

        // add table data to model
        model.addAttribute("assayHeader", headerLine[0]);
        model.addAttribute("samples", gson.toJson(samples));
        model.addAttribute("factors", gson.toJson(factors));
        model.addAttribute("tableData", gson.toJson(csvLines));

        // run accessions are used for highlighting
        String runAccessions = gson.toJson(libraries);
        model.addAttribute("runAccessions", runAccessions);

        // add general experiment attributes to model
        model.addAttribute("experimentAccession", experiment.getAccession());
    }

    /**
     * Extracts subcategories for a given category within the header line
     */
    private static Map<String, Integer> extractSubcategories(String[] headerLine, String category) {
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 1; i < headerLine.length; i++) {
            // cleanup of misplaced white space characters
            headerLine[i] = headerLine[i].trim();
            if (headerLine[i].startsWith(category)) {
                String subcategory = headerLine[i].substring(category.length() + 1, headerLine[i].length() - 1);
                map.put(subcategory, i);
            }
        }
        return map;
    }

    private static Map<Integer, Integer> createReorderMapping(Map<String, Integer> samples, Map<String, Integer> factors) {
        Map<Integer, Integer> mapping = new HashMap<>(1 + samples.size() + factors.size());
        // run accession always at first column
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
        return mapping;
    }

}
















