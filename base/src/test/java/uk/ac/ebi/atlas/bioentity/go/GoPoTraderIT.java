package uk.ac.ebi.atlas.bioentity.go;

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
class GoPoTraderIT {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Inject
    private Path goPoFilePath;

    @Inject
    private GoPoTrader subject;

    @ParameterizedTest
    @MethodSource("randomGoOntologyTermAccessionProvider")
    void canReadAnyGoTerm(OntologyTerm goTerm) {
        assertThat(subject.get(goTerm.accession())).hasValue(goTerm);
    }

    @ParameterizedTest
    @MethodSource("randomPoOntologyTermAccessionProvider")
    void canReadAnyPoTerm(OntologyTerm poTerm) {
        assertThat(subject.get(poTerm.accession())).hasValue(poTerm);
    }

    @Test
    void returnEmptyIfTermNotfound() {
        assertThat(subject.get("FO:OBAR")).isEmpty();
    }

    private Stream<OntologyTerm> randomGoOntologyTermAccessionProvider() throws IOException {
        return Stream.of(randomTermProvider("GO"));
    }

    private Stream<OntologyTerm> randomPoOntologyTermAccessionProvider() throws IOException {
        return Stream.of(randomTermProvider("PO"));
    }

    private OntologyTerm randomTermProvider(String prefix) throws IOException {
        long numLines = Files.lines(goPoFilePath).count();

        String randomLine = "";
        while (!randomLine.startsWith(prefix)) {
            randomLine =
                    Files.lines(goPoFilePath)
                            .skip(RNG.nextLong(0, numLines))
                            .findFirst()
                            .orElseThrow(RuntimeException::new);
        }

        String[] explodedLine = randomLine.split("\t");

        if (explodedLine.length == 3) {
            return OntologyTerm.create(explodedLine[0], explodedLine[1], "", Integer.parseInt(explodedLine[2]));
        } else if (explodedLine.length == 2) {
            return OntologyTerm.create(explodedLine[0], explodedLine[1]);
        }

        throw new RuntimeException();
    }
}
