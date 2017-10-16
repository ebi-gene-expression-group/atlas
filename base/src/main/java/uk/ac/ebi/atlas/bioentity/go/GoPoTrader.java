package uk.ac.ebi.atlas.bioentity.go;

import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Optional;

@Named
public class GoPoTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoPoTrader.class);

    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    @Inject
    public GoPoTrader(@Value("#{configuration['go.terms.file']}") String goPoTSVFilePath)
    throws IOException {
        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(goPoTSVFilePath)) {
            accessionToTerm = new GoPoTSVParser(tsvReader).parse();
        }
    }

    public Optional<OntologyTerm> getTerm(String accession) {
        return Optional.ofNullable(accessionToTerm.get(accession));
    }

    public Optional<String> getTermName(String accession) {
        try {
            return Optional.of(accessionToTerm.get(accession).name());
        } catch (NullPointerException e) {
            LOGGER.warn("Unknown name for GO/PO term with ID {}", accession);
            return Optional.empty();
        }
    }
}
