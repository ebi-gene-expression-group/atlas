package uk.ac.ebi.atlas.experimentimport.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import javax.inject.Inject;
import javax.inject.Named;

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

        TsvReader idfReader = idfReaderFactory.create(experimentAccession);

        String title = "";

        ImmutableSet.Builder<String> pubmedIdsBuilder = new ImmutableSet.Builder<>();
        for (String tsvLine[]: idfReader.readAll()) {
            if (INVESTIGATION_TITLE_ID.equalsIgnoreCase(StringUtils.trim(tsvLine[0]))) {
                title = tsvLine[1];
            } else if (PUBMED_ID.equalsIgnoreCase(tsvLine[0])) {
                for (int i = 1 ; i < tsvLine.length ; i++) {
                    if (!tsvLine[i].isEmpty()) {
                        pubmedIdsBuilder.add(tsvLine[i]);
                    }
                }
            }
        }

        return new ImmutablePair<>(title, pubmedIdsBuilder.build());
    }
}
