package uk.ac.ebi.atlas.bioentity.interpro;

import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Optional;

@Named
public class InterProTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterProTrader.class);

    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    @Inject
    public InterProTrader(@Value("#{configuration['interpro.terms.file']}") String interProTSVFilePath)
    throws IOException {
        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(interProTSVFilePath)) {
            accessionToTerm = new InterProTSVParser(tsvReader).parse();
        }
    }

    public Optional<OntologyTerm> get(String accession) {
        if (accessionToTerm.get(accession) == null) {
            LOGGER.warn("Unable to find InterPro accession {}", accession);
        }

        return Optional.ofNullable(accessionToTerm.get(accession));
    }
}
