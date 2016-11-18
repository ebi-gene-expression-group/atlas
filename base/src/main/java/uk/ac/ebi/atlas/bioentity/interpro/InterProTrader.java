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

@Named
public class InterProTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterProTrader.class);

    private final ImmutableMap<String, String> interProAccessionToTerm;

    @Inject
    public InterProTrader(@Value("#{configuration['interpro.terms.file']}") String interProTSVFilePath) {

        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(interProTSVFilePath)) {
            interProAccessionToTerm = new InterProTSVParser(tsvReader).parse();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    public String getTermName(String accession) {
        return interProAccessionToTerm.get(accession);
    }
}
