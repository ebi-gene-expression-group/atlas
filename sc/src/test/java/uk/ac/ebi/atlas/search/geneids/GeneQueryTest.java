package uk.ac.ebi.atlas.search.geneids;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomKnownBioentityPropertyName;

class GeneQueryTest {
    private static final Species HOMO_SAPIENS =
            new Species(
                    "Homo sapiens",
                    SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of()));

    @ParameterizedTest
    @MethodSource("blankStringProvider")
    void valueFieldIsMandatory(String queryTerm) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> GeneQuery.create(queryTerm, generateRandomKnownBioentityPropertyName(), HOMO_SAPIENS))
                .withMessage("Query term cannot be blank");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> GeneQuery.create(queryTerm, generateRandomKnownBioentityPropertyName()))
                .withMessage("Query term cannot be blank");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> GeneQuery.create(queryTerm, HOMO_SAPIENS))
                .withMessage("Query term cannot be blank");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> GeneQuery.create(queryTerm))
                .withMessage("Query term cannot be blank");
    }

    @Test
    void otherFieldsAreOptional() {
        assertThat(GeneQuery.create("foobar"))
                .hasFieldOrPropertyWithValue("category", Optional.empty())
                .hasFieldOrPropertyWithValue("species", Optional.empty());

        BioentityPropertyName randomKnownPropertyName = generateRandomKnownBioentityPropertyName();
        assertThat(GeneQuery.create("foobar", randomKnownPropertyName))
                .hasFieldOrPropertyWithValue("category", Optional.of(randomKnownPropertyName))
                .hasFieldOrPropertyWithValue("species", Optional.empty());


        assertThat(GeneQuery.create("foobar", HOMO_SAPIENS))
                .hasFieldOrPropertyWithValue("category", Optional.empty())
                .hasFieldOrPropertyWithValue("species", Optional.of(HOMO_SAPIENS));
    }

    private static Iterable<String> blankStringProvider() {
        Set<String> blankStrings = new HashSet<>();
        while (blankStrings.size() < 10) {
            blankStrings.add(stringOfBlanks());
        }
        blankStrings.add(null);
        blankStrings.add("");
        return blankStrings;
    }

    private static String stringOfBlanks() {
        return random(ThreadLocalRandom.current().nextInt(1, 10), " \t\n\r");
    }

}
