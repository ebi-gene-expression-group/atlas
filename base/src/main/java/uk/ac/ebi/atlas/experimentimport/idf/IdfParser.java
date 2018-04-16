package uk.ac.ebi.atlas.experimentimport.idf;

import org.apache.commons.lang.math.NumberUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
public class IdfParser {
    private static final String INVESTIGATION_TITLE_ID = "Investigation Title";
    private static final String PUBMED_ID = "PubMed ID";
    private static final String PUBLICATION_TITLE_ID = "Publication Title";
    private static final String AE_EXPERIMENT_DISPLAY_NAME_ID = "Comment[AEExperimentDisplayName]";
    private static final String EXPECTED_CLUSTERS_ID = "Comment[ExpectedClusters]";

    private static final Set<String> LINE_IDS = Stream.of(INVESTIGATION_TITLE_ID, PUBMED_ID, PUBLICATION_TITLE_ID, AE_EXPERIMENT_DISPLAY_NAME_ID, EXPECTED_CLUSTERS_ID)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

    private IdfStreamerFactory idfReaderFactory;

    private Map<String, List<String>> parsedIdf;

    @Inject
    public IdfParser(IdfStreamerFactory idfReaderFactory) {
        this.idfReaderFactory = idfReaderFactory;
    }

    public IdfParserOutput parse(String experimentAccession) {
        try (TsvStreamer idfStreamer = idfReaderFactory.create(experimentAccession)) {

            parsedIdf = idfStreamer.get()
                            .filter(line -> line.length > 1)
                            .filter(line -> LINE_IDS.contains(line[0].trim().toUpperCase()))
                            .collect(Collectors.toMap(
                                    line -> line[0].trim().toUpperCase(),
                                    line -> Arrays.stream(line)
                                            .skip(1)
                                            .filter(item -> !item.isEmpty())
                                            .collect(Collectors.toList())));

            String title = getParsedOutputByKey(AE_EXPERIMENT_DISPLAY_NAME_ID, getParsedOutputByKey(INVESTIGATION_TITLE_ID, Collections.emptyList()))
                    .stream()
                    .findFirst()
                    .orElse("");

            Map<String, String> publications = createPubMedIdToTitleMap(
                    getParsedOutputByKey(PUBMED_ID, Collections.emptyList()),
                    getParsedOutputByKey(PUBLICATION_TITLE_ID, Collections.emptyList()));

            int expectedClusters = NumberUtils.toInt(getParsedOutputByKey(EXPECTED_CLUSTERS_ID, Collections.emptyList())
                        .stream()
                        .findFirst()
                        .orElse(""),
                    0);

            return new IdfParserOutput(
                    title,
                    publications,
                    expectedClusters
            );

        }
    }

    private Map<String, String> createPubMedIdToTitleMap(List<String> pubmedIds, List<String> publicationTitles) {
        Map<String, String> publicationDetails = new HashMap<>();

        if (pubmedIds.size() != publicationTitles.size()) {
            throw new IdfParserException(
                    MessageFormat.format("There is a mismatch between the number of PubMed IDs ({0}) and the number of publication titles ({1})",
                            pubmedIds.size(),
                            publicationTitles.size()));
        }

        for (int i = 0; i < pubmedIds.size(); i++) {
            publicationDetails.put(pubmedIds.get(i), publicationTitles.get(i));
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
