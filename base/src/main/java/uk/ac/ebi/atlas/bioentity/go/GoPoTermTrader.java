package uk.ac.ebi.atlas.bioentity.go;

import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
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
            throw Throwables.propagate(e);
        }

    }

    public OntologyTerm getTerm(String accession) {
        return accessionToTerm.get(accession);
    }

}
