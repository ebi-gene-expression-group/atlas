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

    private class HeaderOrder {
        String html;
        List<ColumnHelper> columnDefs;
        Map<Integer, Integer> mapping;

        public HeaderOrder(String s, List<ColumnHelper> d, Map<Integer, Integer> m) {
            this.html = s;
            this.columnDefs = d;
            this.mapping = m;
        }
    }

    private class ColumnHelper {
        String sClass;
        List<Integer> aTargets = new ArrayList<>();

        public ColumnHelper(String s) {
            this.sClass = s;
        }

        public void addTargets(Integer... t) {
            for (Integer i : t)
                aTargets.add(i);
        }
    }

    private ExperimentDesignTsvReader experimentDesignTsvReader;
    private ExperimentsCache experimentsCache;

    @Inject
    public ExperimentDesignPageController(ExperimentDesignTsvReader experimentDesignTsvReader, ExperimentsCache experimentsCache) {
        this.experimentDesignTsvReader = experimentDesignTsvReader;
        this.experimentsCache = experimentsCache;
    }

    /**
     * Build html code for table header taking categories from file into account
     *
     * @param headerLine
     * @return
     */
    public HeaderOrder buildTableHeader(String[] headerLine) {

        // split header line into samples and factors
        List<String> samples = new ArrayList<>();
        List<String> factors = new ArrayList<>();

        for (int i = 1; i < headerLine.length; i++) {
            if (headerLine[i].startsWith("Sample Characteristics")) {
                samples.add(headerLine[i].substring(23, headerLine[i].length() - 1));
            } else if (headerLine[i].startsWith("Factor Values")) {
                factors.add(headerLine[i].substring(14, headerLine[i].length() - 1));
            } else {
                System.err.println("Found wrong header: " + headerLine[i]);
            }
        }

        // sort within each category
        Collections.sort(samples);
        Collections.sort(factors);

        // track change in order of columns
        Map<Integer, Integer> mapping = new HashMap<>();
        mapping.put(0, 0);
        for (int i = 1; i < headerLine.length; i++) {
            if (headerLine[i].startsWith("Sample Characteristics")) {
                mapping.put(i, 1 + samples.indexOf(headerLine[i].substring(23, headerLine[i].length() - 1)));
            } else if (headerLine[i].startsWith("Factor Values")) {
                mapping.put(i, 1 + samples.size() + factors.indexOf(headerLine[i].substring(14, headerLine[i].length() - 1)));
            }
        }

        // CSS class for each column
        List<ColumnHelper> columnDefs = new ArrayList<>();
        ColumnHelper helper = new ColumnHelper("assays");
        helper.addTargets(0);
        columnDefs.add(helper);
        for (int i = 1; i < headerLine.length; i++) {
            if (headerLine[i].startsWith("Sample Characteristics")) {
                helper = new ColumnHelper("samples");
                helper.addTargets(mapping.get(i));
                columnDefs.add(helper);
            } else if (headerLine[i].startsWith("Factor Values")) {
                helper = new ColumnHelper("factors");
                helper.addTargets(mapping.get(i));
                columnDefs.add(helper);
            }
        }

        // build html representation of table template
        StringBuilder header = new StringBuilder("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"experiment-design-table\">");
        header.append("<thead><tr>");
        header.append("<th rowspan=\"2\">");
        header.append(headerLine[0]);
        header.append("</th>");
        header.append("<th class=\"samples\" colspan=\"");
        header.append(samples.size());
        header.append("\">Sample Characteristics</th>");
        header.append("<th class=\"factors\" colspan=\"");
        header.append(factors.size());
        header.append("\">Factor Values</th>");
        header.append("</tr><tr>");
        for (String sample : samples) {
            header.append("<th class=\"samples\">");
            header.append(sample);
            header.append("</th>");
        }
        for (String factor : factors) {
            header.append("<th class=\"factors\">");
            header.append(factor);
            header.append("</th>");
        }
        header.append("</tr></thead><tbody></tbody></table>");
        return new HeaderOrder(header.toString(), columnDefs, mapping);
    }

    @RequestMapping("/experiments/{experimentAccession}-experiment-design")
    public String showGeneProfiles(@PathVariable String experimentAccession, Model model) throws IOException {

        // read contents from file
        List<String[]> csvLines = new ArrayList<>(experimentDesignTsvReader.readAll(experimentAccession));
        // delete first line with table headers
        String[] headerLine = csvLines.remove(0);
        HeaderOrder headerOrder = buildTableHeader(headerLine);

        // reorder lines according to new header
        Map<Integer, Integer> mapping = headerOrder.mapping;
        for (int i = 0; i < csvLines.size(); i++) {
            String[] line = csvLines.get(i);
            String[] newLine = new String[line.length];
            for (Integer src : mapping.keySet()) {
                newLine[mapping.get(src)] = line[src];
            }
            csvLines.set(i, newLine);
        }

        // does the serialisation to JSON
        Gson gson = new Gson();
        String header = gson.toJson(headerOrder.html);
        String data = gson.toJson(csvLines);
        String columnDefs = gson.toJson(headerOrder.columnDefs);

        // add table data to model
        model.addAttribute("tableHeader", header);
        model.addAttribute("tableData", data);
        model.addAttribute("columnDefs", columnDefs);

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
















