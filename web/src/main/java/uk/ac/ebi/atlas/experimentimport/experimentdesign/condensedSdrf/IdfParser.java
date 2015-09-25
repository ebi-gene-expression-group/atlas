package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.UrlTsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 29/07/15.
 */

@Named
@Scope("prototype")
public class IdfParser {

    @Value("#{configuration['experiment.magetab.idf.path.template']}")
    private String idfPathTemplate;

    @Value("#{configuration['experiment.magetab.idf.url.template']}")
    private String idfUrlTemplate;

    private static final String INVESTIGATION_TITLE_ID = "Investigation Title";
    private static final String PUBMED_ID = "PubMed ID";

    private FileTsvReaderBuilder fileTsvReaderBuilder;
    private UrlTsvReaderBuilder urlTsvReaderBuilder;

    private String title = "";
    private ImmutableSet<String> pubmedIds;


    @Inject
    public IdfParser(FileTsvReaderBuilder fileTsvReaderBuilder, UrlTsvReaderBuilder urlTsvReaderBuilder) {
        this.urlTsvReaderBuilder = urlTsvReaderBuilder.forTsvFileUrlTemplate(idfUrlTemplate);
        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(idfPathTemplate);
    }

    public void parse(String experimentAccession) {

        TsvReader idfReader;

        try {
            idfReader = urlTsvReaderBuilder.forTsvFileUrlTemplate(idfUrlTemplate).withExperimentAccession(experimentAccession).build();
        } catch (IllegalStateException e) {
            idfReader = fileTsvReaderBuilder.forTsvFilePathTemplate(idfPathTemplate).withExperimentAccession(experimentAccession).build();
        }

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
