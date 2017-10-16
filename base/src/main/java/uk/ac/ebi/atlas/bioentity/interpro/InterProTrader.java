package uk.ac.ebi.atlas.bioentity.interpro;

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

    private final ImmutableMap<String, String> interProAccessionToTerm;

    @Inject
    public InterProTrader(@Value("#{configuration['interpro.terms.file']}") String interProTSVFilePath)
    throws IOException {
        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(interProTSVFilePath)) {
            interProAccessionToTerm = new InterProTSVParser(tsvReader).parse();
        }
    }

    public Optional<String> getTermName(String accession) {
        try {
            return Optional.of(interProAccessionToTerm.get(accession));
        } catch (NullPointerException e) {
            LOGGER.warn("Unknown name for InterPro term with ID {}", accession);
            return Optional.empty();
        }

    }
}
