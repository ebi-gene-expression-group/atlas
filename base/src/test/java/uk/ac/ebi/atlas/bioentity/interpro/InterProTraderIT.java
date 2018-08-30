package uk.ac.ebi.atlas.bioentity.interpro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterProTraderIT {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Inject
    private Path interProFilePath;

    @Inject
    private InterProTrader subject;

    @ParameterizedTest
    @MethodSource("randomTermProvider")
    void canReadAnyTerm(OntologyTerm interProTerm) {
        assertThat(subject.get(interProTerm.accession())).hasValue(interProTerm);
    }

    @Test
    void returnEmptyIfTermNotfound() {
        assertThat(subject.get("IPRFOOBAR")).isEmpty();
    }

    private Stream<OntologyTerm> randomTermProvider() throws IOException {
        long numLines = Files.lines(interProFilePath).count();

        String randomLine =
                Files.lines(interProFilePath)
                        .skip(RNG.nextLong(0, numLines))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);

        String[] explodedLine = randomLine.split("\t");

        return Stream.of(
                OntologyTerm.create(
                        explodedLine[1],
                        explodedLine[0] + String.format(" (%s)", explodedLine[2].toLowerCase())));
    }
}
