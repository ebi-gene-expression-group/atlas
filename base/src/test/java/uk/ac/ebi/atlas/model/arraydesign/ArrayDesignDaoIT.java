package uk.ac.ebi.atlas.model.arraydesign;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql({"arraydesign-fixture.sql", "designelement_mapping-fixture.sql"})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArrayDesignDaoIT {
    private final static ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private JdbcUtils jdbcUtils;

    @Inject
    private ArrayDesignDao subject;

    private static final String SELECT_IDENTIFIERS_WITH_DESIGNELEMENT_COUNT =
            "SELECT identifier, COUNT(DISTINCT(designelement)) FROM designelement_mapping GROUP BY identifier";

    @Test
    void testGetDesignElements() {
        List<Pair<String, Integer>> results =
                jdbcTemplate.query(
                        SELECT_IDENTIFIERS_WITH_DESIGNELEMENT_COUNT,
                        (rs, rowNum) -> Pair.of(rs.getString("identifier"), rs.getInt("count")));

        Pair<String, Integer> randomResult = results.get(RNG.nextInt(0, results.size()));

        assertThat(subject.getDesignElements(randomResult.getLeft()))
                .isNotEmpty()
                .hasSize(randomResult.getRight());
    }

    @Test
    void unknownGeneReturnsEmptyList() {
        assertThat(subject.getDesignElements("foobar"))
                .isEmpty();
    }

    @Test
    void unknownArrayDesignIsPopulatedWithItsAccession() {
        String randomWord = randomAlphanumeric(10);

        assertThat(subject.getArrayDesign(randomWord))
                .hasFieldOrPropertyWithValue("accession", randomWord)
                .hasFieldOrPropertyWithValue("name", randomWord);
    }

    @Test
    void selectArrayDesign() {
        String randomArrayDesignAccession = jdbcUtils.fetchRandomArrayDesignAccession();
        assertThat(subject.getArrayDesign(randomArrayDesignAccession))
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("accession", randomArrayDesignAccession)
                .matches(arrayDesign -> isNotBlank(arrayDesign.name()));
    }
}
