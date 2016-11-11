package uk.ac.ebi.atlas.bioentity.go;

import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class GoPoTermTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoPoTermTrader.class);

    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    @Inject
    public GoPoTermTrader(@Value("#{configuration['go.terms.file']}") String goPoTSVFilePath,
                          CsvReaderFactory csvReaderFactory) {

        try (CSVReader tsvReader = csvReaderFactory.createTsvReader(goPoTSVFilePath)) {
            accessionToTerm = new GoPoTermTSVParser(tsvReader).parse();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    public OntologyTerm getTerm(String accession) {
        return accessionToTerm.get(accession);
    }

}
