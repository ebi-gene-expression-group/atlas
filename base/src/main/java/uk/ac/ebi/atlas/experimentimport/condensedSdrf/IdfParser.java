package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
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

    @Inject
    public IdfParser(IdfStreamerFactory idfReaderFactory) {
        this.idfReaderFactory = idfReaderFactory;
    }

    public ImmutablePair<String, ImmutableSet<String>> parse(String experimentAccession) {

        try (
             TsvStreamer titleIdfStreamer = idfReaderFactory.create(experimentAccession);
             TsvStreamer pubmedIdfStreamer = idfReaderFactory.create(experimentAccession)) {

            Map<String, String> titles = titleIdfStreamer.get()
                    .filter(line -> line.length > 1)
                    .filter(line -> AE_EXPERIMENT_DISPLAY_NAME_ID.equalsIgnoreCase(line[0].trim()) || INVESTIGATION_TITLE_ID.equalsIgnoreCase(line[0].trim()))
                    .collect(Collectors.toMap(line -> line[0].toUpperCase().trim(), line-> line[1]));

            String title = titles.getOrDefault(AE_EXPERIMENT_DISPLAY_NAME_ID.toUpperCase(), titles.getOrDefault(INVESTIGATION_TITLE_ID.toUpperCase(), ""));

            ImmutableSet<String> pubmedIds = ImmutableSet.copyOf(
                    pubmedIdfStreamer.get()
                            .filter(line -> line.length > 1)
                            .filter(line -> PUBMED_ID.equalsIgnoreCase(line[0].trim()))
                            .flatMap(line -> Arrays.stream(line).skip(1))
                            .filter(item -> !item.isEmpty())
                            .iterator());

            return ImmutablePair.of(title, pubmedIds);
        }
    }

    public IdfParserOutput newParse(String experimentAccession) {
        try (TsvStreamer idfStreamer = idfReaderFactory.create(experimentAccession)) {

            Map<String, List<String>> parsedIdf =
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

            String title = parsedIdf.getOrDefault(AE_EXPERIMENT_DISPLAY_NAME_ID.toUpperCase(), parsedIdf.get(INVESTIGATION_TITLE_ID.toUpperCase()))
                    .stream()
                    .findFirst()
                    .orElse("");

            Map<String, String> publicationDetails = getPublicationDetailsMap(parsedIdf.get(PUBMED_ID.toUpperCase()), parsedIdf.get(PUBLICATION_TITLE_ID.toUpperCase()));
            int expectedClusters = NumberUtils.toInt(parsedIdf.get(EXPECTED_CLUSTERS_ID.toUpperCase()).stream().findFirst().orElse(""), 0);

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
        
        return  publicationDetails;
    }
}
