package uk.ac.ebi.atlas.experimentimport.idf;

import org.apache.commons.lang.math.NumberUtils;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.Publication;
import uk.ac.ebi.atlas.resource.DataFileHub;

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
    private static final String EXPERIMENT_DESCRIPTION_ID = "Experiment Description";
    private static final String PUBMED_ID = "PubMed ID";
    private static final String PUBLICATION_TITLE_ID = "Publication Title";
    private static final String PUBLICATION_DOI_ID = "Publication DOI";
    private static final String AE_EXPERIMENT_DISPLAY_NAME_ID = "Comment[AEExperimentDisplayName]";
    private static final String EXPECTED_CLUSTERS_ID = "Comment[EAExpectedClusters]";
    private static final String ADDITIONAL_ATTRIBUTES_ID = "Comment[EAAdditionalAttributes]";

    private static final Set<String> LINE_IDS = Stream.of(INVESTIGATION_TITLE_ID, EXPERIMENT_DESCRIPTION_ID, PUBMED_ID,
            PUBLICATION_TITLE_ID, PUBLICATION_DOI_ID, AE_EXPERIMENT_DISPLAY_NAME_ID, EXPECTED_CLUSTERS_ID,
            ADDITIONAL_ATTRIBUTES_ID)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

    private DataFileHub dataFileHub;

    private Map<String, List<String>> parsedIdf;

    @Inject
    public IdfParser(DataFileHub dataFileHub) {
        this.dataFileHub = dataFileHub;
    }

    public IdfParserOutput parse(String experimentAccession) {
        try (TsvStreamer idfStreamer = dataFileHub.getExperimentFiles(experimentAccession).idf.get()) {

            parsedIdf = idfStreamer.get()
                            .filter(line -> line.length > 1)
                            .filter(line -> LINE_IDS.contains(line[0].trim().toUpperCase()))
                            .collect(Collectors.toMap(
                                    line -> line[0].trim().toUpperCase(),
                                    line -> Arrays.stream(line)
                                            .skip(1)
                                            .filter(item -> !item.isEmpty())
                                            .collect(Collectors.toList()),
                                    (accumulatedValues, newValue) -> accumulatedValues));

            String title;
            // Experiments that are regularly updated in ArrayExpress
            if (experimentAccession.startsWith("E-MTAB") || experimentAccession.startsWith("E-TABM")) {
                 title =
                         getParsedOutputByKey(
                                 AE_EXPERIMENT_DISPLAY_NAME_ID,
                                 getParsedOutputByKey(INVESTIGATION_TITLE_ID, Collections.emptyList()))
                                 .stream()
                                 .findFirst()
                                 .orElse("");
            } else {
                title = getParsedOutputByKey(INVESTIGATION_TITLE_ID, Collections.emptyList())
                        .stream()
                        .findFirst()
                        .orElse("");
            }

            String experimentDescription = getParsedOutputByKey(EXPERIMENT_DESCRIPTION_ID, Collections.emptyList())
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

            List<String> metadataFieldsOfInterest =
                    getParsedOutputByKey(ADDITIONAL_ATTRIBUTES_ID, Collections.emptyList());

            return new IdfParserOutput(
                    title,
                    experimentDescription,
                    publications,
                    expectedClusters,
                    metadataFieldsOfInterest
            );

        }
    }

    // For each publication title, retrieves corresponding PubMed ID and DOI (if they exist) and creates a list of
    // Publication objects
    private List<Publication> createListOfPublications(List<String> pubmedIds,
                                                       List<String> publicationTitles,
                                                       List<String> publicationDois) {
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
