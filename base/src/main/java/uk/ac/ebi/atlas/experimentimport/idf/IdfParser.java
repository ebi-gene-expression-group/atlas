package uk.ac.ebi.atlas.experimentimport.idf;

import org.apache.commons.lang.math.NumberUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.Publication;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final String PUBLICATION_DOI_ID = "Publication DOI";
    private static final String AE_EXPERIMENT_DISPLAY_NAME_ID = "Comment[AEExperimentDisplayName]";
    private static final String EXPECTED_CLUSTERS_ID = "Comment[ExpectedClusters]";

    private static final Set<String> LINE_IDS = Stream.of(INVESTIGATION_TITLE_ID, PUBMED_ID, PUBLICATION_TITLE_ID, PUBLICATION_DOI_ID, AE_EXPERIMENT_DISPLAY_NAME_ID, EXPECTED_CLUSTERS_ID)
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

            List<Publication> publications = createListOfPublications(
                    getParsedOutputByKey(PUBMED_ID, Collections.emptyList()),
                    getParsedOutputByKey(PUBLICATION_TITLE_ID, Collections.emptyList()),
                    getParsedOutputByKey(PUBLICATION_DOI_ID, Collections.emptyList()));

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
    
    // For each publication title, retrieves corresponding PubMed ID and DOI (if they exist) and creates a list of Publication objects
    private List<Publication> createListOfPublications(List<String> pubmedIds, List<String> publicationTitles, List<String> publicationDois) {
        List<Publication> publications = new ArrayList<>();

        if (!publicationTitles.isEmpty()) {
            for (int i = 0; i < publicationTitles.size(); i++) {
                publications.add(new Publication(
                        getPublicationInformation(i, pubmedIds),
                        getPublicationInformation(i, publicationDois),
                        publicationTitles.get(i))
                );
            }
        }

        return  publications;
    }

    private List<String> getParsedOutputByKey(String key, List<String> outputIfEmpty) {
        return parsedIdf.getOrDefault(key.toUpperCase(), outputIfEmpty);
    }

    private String getPublicationInformation(int index, List<String> list) {
        return index < list.size() ? list.get(index) : null;
    }
}
