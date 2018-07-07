package uk.ac.ebi.atlas.bioentity.go;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Named
public class GoPoTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoPoTrader.class);

    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    @Inject
    public GoPoTrader(@Value("${data.files.location}") String dataFilesLocation)
    throws IOException {
        String filePath = Paths.get(dataFilesLocation, "bioentity_properties", "go", "goIDToTerm.tsv").toString();
        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(filePath)) {
            accessionToTerm = new GoPoTSVParser(tsvReader).parse();
        }
    }

    public Optional<OntologyTerm> get(String accession) {
        if (accessionToTerm.get(accession) == null) {
            LOGGER.warn("Unable to find InterPro accession {}", accession);
        }

        return Optional.ofNullable(accessionToTerm.get(accession));
    }
}
