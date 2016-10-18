package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@Scope("singleton")
public class InterProTrader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterProTrader.class);

    private final ImmutableMap<String, String> interProAccessionToTerm;

    @Inject
    public InterProTrader(@Value("#{configuration['interpro.terms.file']}") String interProTSVFilePath,
                          CsvReaderFactory csvReaderFactory) {

        try (CSVReader tsvReader = csvReaderFactory.createTsvReader(interProTSVFilePath)) {
            interProAccessionToTerm = new InterProTSVParser(tsvReader).parse();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw Throwables.propagate(e);
        }

    }

    public String getTermName(String accession) {
        return interProAccessionToTerm.get(accession);
    }
}
