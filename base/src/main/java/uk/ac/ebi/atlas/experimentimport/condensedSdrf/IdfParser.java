package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

@Named
public class IdfParser {
    private static final String INVESTIGATION_TITLE_ID = "Investigation Title";
    private static final String PUBMED_ID = "PubMed ID";

    private IdfReaderFactory idfReaderFactory;

    @Inject
    public IdfParser(IdfReaderFactory idfReaderFactory) {
        this.idfReaderFactory = idfReaderFactory;
    }

    public ImmutablePair<String, ImmutableSet<String>> parse(String experimentAccession) {

        try (TsvReader idfTitleReader = idfReaderFactory.create(experimentAccession);
             TsvReader idfPubmedIdsReader = idfReaderFactory.create(experimentAccession)) {
            String title =
                    idfTitleReader.stream()
                            .filter(line -> line.length > 1)
                            .filter(line -> INVESTIGATION_TITLE_ID.equalsIgnoreCase(line[0].trim()))
                            .map(line -> line[1])
                            .findFirst()
                            .orElse("");

            ImmutableSet<String> pubmedIds = ImmutableSet.copyOf(
                    idfPubmedIdsReader.stream()
                            .filter(line -> line.length > 1)
                            .filter(line -> PUBMED_ID.equalsIgnoreCase(line[0].trim()))
                            .flatMap(line -> Arrays.stream(line).skip(1))
                            .iterator());

            return ImmutablePair.of(title, pubmedIds);
        }
    }
}
