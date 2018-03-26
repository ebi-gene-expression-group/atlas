package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;

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
    private static final String AE_EXPERIMENT_DISPLAY_NAME = "Comment[AEExperimentDisplayName]";

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
                    .filter(line -> AE_EXPERIMENT_DISPLAY_NAME.equalsIgnoreCase(line[0].trim()) || INVESTIGATION_TITLE_ID.equalsIgnoreCase(line[0].trim()))
                    .collect(Collectors.toMap(line -> line[0], line-> line[1]));

            String title = titles.getOrDefault(AE_EXPERIMENT_DISPLAY_NAME, titles.getOrDefault(INVESTIGATION_TITLE_ID, ""));

            ImmutableSet<String> pubmedIds = ImmutableSet.copyOf(
                    pubmedIdfStreamer.get()
                            .filter(line -> line.length > 1)
                            .filter(line -> PUBMED_ID.equalsIgnoreCase(line[0].trim()))
                            .flatMap(line -> Arrays.stream(line).skip(1))
                            .iterator());

            return ImmutablePair.of(title, pubmedIds);
        }
    }
}
