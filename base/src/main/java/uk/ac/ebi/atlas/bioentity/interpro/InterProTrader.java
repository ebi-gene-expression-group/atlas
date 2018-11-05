package uk.ac.ebi.atlas.bioentity.interpro;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.model.OntologyTerm;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
public class InterProTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterProTrader.class);
    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    public InterProTrader(Path interProFilePath) {
        try (TsvStreamer tsvStreamer =
                     new TsvStreamer(Files.newBufferedReader(interProFilePath, StandardCharsets.UTF_8))) {
            accessionToTerm = InterProTsvParser.parse(tsvStreamer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Optional<OntologyTerm> get(String accession) {
        if (accessionToTerm.get(accession) == null) {
            LOGGER.warn("Unable to find InterPro accession {}", accession);
        }

        return Optional.ofNullable(accessionToTerm.get(accession));
    }
}
