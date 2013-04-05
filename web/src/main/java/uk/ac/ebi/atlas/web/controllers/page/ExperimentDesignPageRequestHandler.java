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
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class ExperimentDesignPageRequestHandler<T extends Experiment> {

    static final Pattern SAMPLE_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristics\\[(.*?)\\]\\s*");
    static final Pattern FACTOR_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Values\\[(.*?)\\]\\s*");

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String pathTemplate;

    private TsvReaderBuilder tsvReaderBuilder;

    @Inject
    void setTsvReaderBuilder(TsvReaderBuilder tsvReaderBuilder){
        this.tsvReaderBuilder = tsvReaderBuilder;
    }

    public String handleRequest(Model model, HttpServletRequest request) {
        T experiment = (T) request.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE);

        String experimentAccession = experiment.getAccession();

        TsvReader tsvReader = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate).build();

        List<String[]> csvLines = new ArrayList<>(tsvReader.readAll(experimentAccession));

        String[] headerLine = csvLines.remove(0);

        Map<String, Integer> sampleHeaderIndexes = extractHeaderIndexes(headerLine, SAMPLE_COLUMN_HEADER_PATTERN);
        Map<String, Integer> factorHeaderIndexes = extractHeaderIndexes(headerLine, FACTOR_COLUMN_HEADER_PATTERN);

        // reorder lines according to new header
        Map<Integer, Integer> mapping = createReorderMapping(sampleHeaderIndexes, factorHeaderIndexes);

        int startIndex = headerLine.length - (sampleHeaderIndexes.size() + factorHeaderIndexes.size());

        for (String[] line : csvLines) {
            String[] copy = Arrays.copyOf(line, headerLine.length);
            for (int j = 0 ; j < mapping.size(); j++) {
                Integer columnIndex = mapping.get(j);
                checkNotNull(columnIndex, "No mapping found for ExpDesign column " + j);
                // here re-ordering of each line
                line[j + startIndex] = copy[columnIndex];
            }
        }

        // does the serialisation to JSON
        Gson gson = new Gson();
        // add table data to model
        String[] assayHeaders = (String[])ArrayUtils.subarray(headerLine, 0, startIndex);
        model.addAttribute("assayHeaders", gson.toJson(assayHeaders));
        model.addAttribute("sampleHeaders", gson.toJson(sampleHeaderIndexes.keySet()));
        model.addAttribute("factorHeaders", gson.toJson(factorHeaderIndexes.keySet()));
        model.addAttribute("tableData", gson.toJson(csvLines));

        //analysed row accessions are added to the model separately,
        //because design table rows can be hidden or shown depending if they are or not part of the analysed subset
        String runAccessions = gson.toJson(getAnalysedRowsAccessions(experiment));
        model.addAttribute("runAccessions", runAccessions);

        // add general experiment attributes to model
        model.addAttribute("experimentAccession", experimentAccession);


        extendModel(model, experiment);


        return "experiment-experiment-design";

    }

    protected abstract void extendModel(Model model, T experiment);

    protected abstract Set<String> getAnalysedRowsAccessions(T experiment);


    protected Map<String, Integer> extractHeaderIndexes(String[] columnHeaders, Pattern columnHeaderPattern) {
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 0; i < columnHeaders.length; i++) {
            String matchingHeaderContent = extractMatchingContent(columnHeaders[i],columnHeaderPattern);
            if (matchingHeaderContent != null) {
                map.put(matchingHeaderContent, i);
            }
        }
        return map;
    }

    static String extractMatchingContent(String string, Pattern pattern){
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()){
            return matcher.group(1);
        }
        return null;
    }

    protected Map<Integer, Integer> createReorderMapping(Map<String, Integer> samples, Map<String, Integer> factors) {
        Map<Integer, Integer> mapping = new HashMap<>(samples.size() + factors.size());

        int i = 0;
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
















