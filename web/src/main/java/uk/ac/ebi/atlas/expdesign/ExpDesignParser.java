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

package uk.ac.ebi.atlas.expdesign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ToDo: (N) to be removed
@Named
@Scope("prototype")
public class ExpDesignParser {

    static final Pattern SAMPLE_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristics\\[(.*?)\\]\\s*");
    static final Pattern FACTOR_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Values\\[(.*?)\\]\\s*");

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String pathTemplate;

    private TsvReaderBuilder tsvReaderBuilder;

    @Inject
    void setTsvReaderBuilder(TsvReaderBuilder tsvReaderBuilder) {
        this.tsvReaderBuilder = tsvReaderBuilder;
    }

    public ExperimentDesign parse(String experimentAccession) {

        TsvReader tsvReader = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate).build();

        List<String[]> csvLines = new ArrayList<>(tsvReader.readAll(experimentAccession));

        String[] headerLine = csvLines.remove(0);

        Map<String, Integer> sampleHeaderIndexes = extractHeaderIndexes(headerLine, SAMPLE_COLUMN_HEADER_PATTERN);
        Map<String, Integer> factorHeaderIndexes = extractHeaderIndexes(headerLine, FACTOR_COLUMN_HEADER_PATTERN);
        int headersStartIndex = headerLine.length - (sampleHeaderIndexes.size() + factorHeaderIndexes.size());

        ExperimentDesign experimentDesign = new ExperimentDesign();
        for (int i = 0; i < headersStartIndex; i++) {
            experimentDesign.addAssayHeader(headerLine[i]);
        }

        for (String[] line : csvLines) {
            String runOrAssay = line[0];
            if (headersStartIndex > 1) {
                experimentDesign.putArrayDesign(runOrAssay, line[1]);
            }

            for (String sampleHeader : sampleHeaderIndexes.keySet()) {
                String sampleValue = line[sampleHeaderIndexes.get(sampleHeader)];
                experimentDesign.putSample(runOrAssay, sampleHeader, sampleValue);
            }

            for (String factorHeader : factorHeaderIndexes.keySet()) {
                String factorValue = line[factorHeaderIndexes.get(factorHeader)];
                experimentDesign.putFactor(runOrAssay, factorHeader, factorValue);
            }
        }

        return experimentDesign;
    }

    protected Map<String, Integer> extractHeaderIndexes(String[] columnHeaders, Pattern columnHeaderPattern) {
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 0; i < columnHeaders.length; i++) {
            String matchingHeaderContent = extractMatchingContent(columnHeaders[i], columnHeaderPattern);
            if (matchingHeaderContent != null) {
                map.put(matchingHeaderContent, i);
            }
        }
        return map;
    }

    static String extractMatchingContent(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

}