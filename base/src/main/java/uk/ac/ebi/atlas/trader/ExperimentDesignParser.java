package uk.ac.ebi.atlas.trader;

import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
public class ExperimentDesignParser {
    private static final String ONTOLOGY_TERM_DELIMITER = " ";

    static final Pattern SAMPLE_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristic\\[(.*?)\\]\\s*");
    private static final Pattern SAMPLE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Sample Characteristic Ontology Term\\[(.*?)\\]\\s*");

    private static final Pattern FACTOR_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Value\\[(.*?)\\]\\s*");
    private static final Pattern FACTOR_VALUE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN = Pattern.compile("\\s*Factor Value Ontology Term\\[(.*?)\\]\\s*");

    private final DataFileHub dataFileHub;

    @Inject
    ExperimentDesignParser(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public ExperimentDesign parse(String experimentAccession) {

        AtlasResource<TsvReader> r = dataFileHub.getExperimentFiles(experimentAccession).experimentDesign;

        if (!r.exists()) {
            throw new UncheckedIOException(new FileNotFoundException(String.format("%s does not exist", r)));
        }

        try (TsvReader tsvReaderz = r.get()) {
            Iterator<String[]> lineIterator = tsvReaderz.stream().iterator();
            ExperimentDesign experimentDesign = new ExperimentDesign();

            if (lineIterator.hasNext()) {
                String[] headerLine = lineIterator.next();

                Map<String, Integer> sampleHeaderIndexes = extractHeaderIndexes(headerLine, SAMPLE_COLUMN_HEADER_PATTERN);
                Map<String, Integer> sampleValueOntologyTermHeaderIndexes =
                        extractHeaderIndexes(headerLine, SAMPLE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN);

                Map<String, Integer> factorHeaderIndexes = extractHeaderIndexes(headerLine, FACTOR_COLUMN_HEADER_PATTERN);
                Map<String, Integer> factorValueOntologyTermHeaderIndexes =
                        extractHeaderIndexes(headerLine, FACTOR_VALUE_ONTOLOGY_TERM_COLUMN_HEADER_PATTERN);

                int headersStartIndex =
                        headerLine.length - (sampleHeaderIndexes.size() + sampleValueOntologyTermHeaderIndexes.size() +
                                factorHeaderIndexes.size() + factorValueOntologyTermHeaderIndexes.size());

                for (String assayHeaderField : Arrays.copyOf(headerLine, headersStartIndex)) {
                    experimentDesign.addAssayHeader(assayHeaderField);
                }

                while (lineIterator.hasNext()) {
                    String[] line = lineIterator.next();

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
            }

            return experimentDesign;
        }
    }

    private OntologyTerm[] createOntologyTerms(String[] line, Integer ontologyTermIndex) {
        if (ontologyTermIndex == null || line[ontologyTermIndex].isEmpty()) {
            return new OntologyTerm[0];
        }

        ImmutableList.Builder<OntologyTerm> ontologyTermBuilder = new ImmutableList.Builder<>();
        String uriField = line[ontologyTermIndex];
        for (String uri : uriField.split(ONTOLOGY_TERM_DELIMITER)) {
            ontologyTermBuilder.add(OntologyTerm.createFromURI(uri));
        }
        List<OntologyTerm> ontologyTermList = ontologyTermBuilder.build();

        return ontologyTermList.toArray(new OntologyTerm[ontologyTermList.size()]);
    }

    private Map<String, Integer> extractHeaderIndexes(String[] columnHeaders, Pattern columnHeaderPattern) {
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
