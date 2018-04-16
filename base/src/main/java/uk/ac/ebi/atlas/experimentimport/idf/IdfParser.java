package uk.ac.ebi.atlas.experimentimport.idf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class IdfParser {
    private static final String INVESTIGATION_TITLE_ID = "Investigation Title";
    private static final String PUBMED_ID = "PubMed ID";
    private static final String PUBLICATION_TITLE_ID = "Publication Title";
    private static final String AE_EXPERIMENT_DISPLAY_NAME_ID = "Comment[AEExperimentDisplayName]";
    private static final String EXPECTED_CLUSTERS_ID = "Comment[ExpectedClusters]";

    private IdfStreamerFactory idfReaderFactory;

    private  Map<String, List<String>> parsedIdf;

    @Inject
    public IdfParser(IdfStreamerFactory idfReaderFactory) {
        this.idfReaderFactory = idfReaderFactory;
    }

    public IdfParserOutput parse(String experimentAccession) {
        try (TsvStreamer idfStreamer = idfReaderFactory.create(experimentAccession)) {

            parsedIdf =
                    idfStreamer.get()
                            .filter(line -> line.length > 1)
                            .filter(line -> INVESTIGATION_TITLE_ID.equalsIgnoreCase(line[0].trim()) ||
                                        AE_EXPERIMENT_DISPLAY_NAME_ID.equalsIgnoreCase(line[0].trim()) ||
                                        PUBMED_ID.equalsIgnoreCase(line[0].trim()) ||
                                        PUBLICATION_TITLE_ID.equalsIgnoreCase(line[0].trim()) ||
                                        EXPECTED_CLUSTERS_ID.equalsIgnoreCase(line[0].trim()))
                            .collect(Collectors.toMap(
                                    line -> line[0].toUpperCase().trim(),
                                    line -> Arrays.stream(line)
                                            .skip(1)
                                            .filter(item -> !item.isEmpty())
                                            .collect(Collectors.toList())));

            String title = getParsedOutputByKey(AE_EXPERIMENT_DISPLAY_NAME_ID, getParsedOutputByKey(INVESTIGATION_TITLE_ID, Collections.emptyList()))
                    .stream()
                    .findFirst()
                    .orElse("");

            Map<String, String> publicationDetails = getPublicationDetailsMap(
                    getParsedOutputByKey(PUBMED_ID, Collections.emptyList()),
                    getParsedOutputByKey(PUBLICATION_TITLE_ID, Collections.emptyList()));

            int expectedClusters = NumberUtils.toInt(getParsedOutputByKey(EXPECTED_CLUSTERS_ID, Collections.emptyList())
                        .stream()
                        .findFirst()
                        .orElse(""),
                    0);

            return new IdfParserOutput(
                    title,
                    publicationDetails,
                    expectedClusters
            );

        }
    }

    private Map<String, String> getPublicationDetailsMap(List<String> pubmedIds, List<String> publicationTitles) {
        Map<String, String> publicationDetails = new HashMap<>();

        if(pubmedIds.size() == publicationTitles.size()) {
            for (int i = 0; i < pubmedIds.size(); i++) {
                publicationDetails.put(pubmedIds.get(i), publicationTitles.get(i));
            }
        }
        else {
            throw new IdfParserException("There is a mismatch between the number of PubMed IDs (" + pubmedIds.size() + ") and the number of publication titles (" + publicationTitles.size() + ")");
        }
        
        return  publicationDetails;
    }

    private List<String> getParsedOutputByKey(String key, List<String> outputIfEmpty) {
        return parsedIdf.getOrDefault(key.toUpperCase(), outputIfEmpty);
    }

    class IdfParserException extends RuntimeException {
        IdfParserException(String message) {
            super(message);
        }
    }
}
