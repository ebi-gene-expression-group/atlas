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

package uk.ac.ebi.atlas.trader;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.utils.OntologyTermUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ToDo: (N) to be removed <- (B) and replaced with what??
@Named
@Scope("prototype")
public class ExperimentDesignParser {

    static final Pattern SAMPLE_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristic\\[(.*?)\\]\\s*");
    static final Pattern SAMPLE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristic Ontology Term\\[(.*?)\\]\\s*");

    static final Pattern FACTOR_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Value\\[(.*?)\\]\\s*");
    static final Pattern FACTOR_VALUE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Value Ontology Term\\[(.*?)\\]\\s*");

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String pathTemplate;

    private TsvReaderBuilder tsvReaderBuilder;

    @Inject
    void setTsvReaderBuilder(TsvReaderBuilder tsvReaderBuilder) {
        this.tsvReaderBuilder = tsvReaderBuilder;
    }

    public ExperimentDesign parse(String experimentAccession) {

        TsvReader tsvReader = tsvReaderBuilder.forTsvFilePathTemplate(pathTemplate)
                                              .withExperimentAccession(experimentAccession)
                                              .build();

        List<String[]> csvLines = new ArrayList<>(tsvReader.readAll());

        if (csvLines.isEmpty()) {
            String tsvFilePath = MessageFormat.format(pathTemplate, experimentAccession);
            throw new IllegalStateException(String.format("%s is empty", tsvFilePath));
        }

        String[] headerLine = csvLines.remove(0);

        Map<String, Integer> sampleHeaderIndexes = extractHeaderIndexes(headerLine, SAMPLE_COLUMN_HEADER_PATTERN);
        Map<String, Integer> sampleValueOntologyTermHeaderIndexes = extractHeaderIndexes(headerLine, SAMPLE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN);

        Map<String, Integer> factorHeaderIndexes = extractHeaderIndexes(headerLine, FACTOR_COLUMN_HEADER_PATTERN);
        Map<String, Integer> factorValueOntologyTermHeaderIndexes = extractHeaderIndexes(headerLine, FACTOR_VALUE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN);

        int headersStartIndex = headerLine.length - (sampleHeaderIndexes.size() + sampleValueOntologyTermHeaderIndexes.size() + factorHeaderIndexes.size() + factorValueOntologyTermHeaderIndexes.size());

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

                Integer sampleValueOntologyTermIndex = sampleValueOntologyTermHeaderIndexes.get(sampleHeader);
                OntologyTerm[] sampleValueOntologyTerms = createOntologyTerms(line, sampleValueOntologyTermIndex);
                SampleCharacteristic sampleCharacteristic = SampleCharacteristic.create(sampleHeader, sampleValue, sampleValueOntologyTerms);

                experimentDesign.putSampleCharacteristic(runOrAssay, sampleHeader, sampleCharacteristic);
            }

            for (String factorHeader : factorHeaderIndexes.keySet()) {
                String factorValue = line[factorHeaderIndexes.get(factorHeader)];

                Integer factorValueOntologyTermIndex = factorValueOntologyTermHeaderIndexes.get(factorHeader);
                OntologyTerm[] factorValueOntologyTerms = createOntologyTerms(line, factorValueOntologyTermIndex);

                experimentDesign.putFactor(runOrAssay, factorHeader, factorValue, factorValueOntologyTerms);
            }
        }

        return experimentDesign;
    }

    private OntologyTerm[] createOntologyTerms(String[] line, Integer ontologyTermIndex) {
        if (ontologyTermIndex == null || line[ontologyTermIndex].isEmpty()) {
            return new OntologyTerm[0];
        }

        ImmutableList.Builder<OntologyTerm> ontologyTermBuilder = new ImmutableList.Builder<>();

        String uriField = line[ontologyTermIndex];
        for (String uri : uriField.split(OntologyTermUtils.ONTOLOGY_TERM_DELIMITER)) {
            ontologyTermBuilder.add(OntologyTerm.createFromUri(uri));
        }
        return ontologyTermBuilder.build().toArray(new OntologyTerm[0]);
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
