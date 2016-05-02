package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class IdfParser {

    private static final String INVESTIGATION_TITLE_ID = "Investigation Title";
    private static final String PUBMED_ID = "PubMed ID";

    private IdfReaderFactory idfReaderFactory;

    private String title = "";
    private ImmutableSet<String> pubmedIds;

    @Inject
    public IdfParser(IdfReaderFactory idfReaderFactory) {
        this.idfReaderFactory = idfReaderFactory;
    }

    public void parse(String experimentAccession) {

        TsvReader idfReader = idfReaderFactory.create(experimentAccession);

        ImmutableSet.Builder<String> pubmedIdsBuilder = new ImmutableSet.Builder<>();
        for (String tsvLine[]: idfReader.readAll()) {
            if (tsvLine[0].equalsIgnoreCase(INVESTIGATION_TITLE_ID)) {
                title = tsvLine[1];
            } else if (tsvLine[0].equalsIgnoreCase(PUBMED_ID)) {
                for (int i = 1 ; i < tsvLine.length ; i++) {
                    if (!tsvLine[i].isEmpty()) {
                        pubmedIdsBuilder.add(tsvLine[i]);
                    }
                }
            }
        }
        pubmedIds = pubmedIdsBuilder.build();

    }

    public String getTitle() {
        return title;
    }

    public ImmutableSet<String> getPubMedIds() {
        return pubmedIds;
    }

}
