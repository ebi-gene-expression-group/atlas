package uk.ac.ebi.atlas.experimentimport.sdrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SdrfParserTest {

    private static final String CHARACTERISTICS = "characteristics";
    private static final String FACTORS = "factorvalue";

    private static final String[][] SDRF_TXT_MIXED_SPACING = {
            {"Source Name", "Characteristics [organism]", "Characteristics[developmental stage]", "Characteristics [organism part]", "Factor Value[organism part]", "FactorValue [organism]"},
            {"first_source", "homo sapiens", "adult", "liver", "liver", "homo sapiens"}
    };

    private static final String[][] SDRF_TXT_NO_FACTORS = {
            {"Source Name", "Characteristics [organism]", "Characteristics[developmental stage]", "Characteristics [organism part]"},
            {"first_source", "homo sapiens", "adult", "liver"}
    };

    private MockDataFileHub dataFileHub;

    private SdrfParser subject;

    @BeforeEach
    void setUp() {
        dataFileHub = MockDataFileHub.create();

        subject = new SdrfParser(dataFileHub);
    }

    @Test
    void parseHeaderWithCharacteristicsAndFactors() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();

        dataFileHub.addSdrfFile(experimentAccession, Arrays.asList(SDRF_TXT_MIXED_SPACING));

        Map<String, Set<String>> result = subject.parseHeader(experimentAccession);

        assertThat(result)
                .hasSize(2)
                .containsOnlyKeys(CHARACTERISTICS, FACTORS);

        assertThat(result.get(CHARACTERISTICS))
                .containsExactly("organism", "developmental stage", "organism part");

        assertThat(result.get(FACTORS))
                .containsExactly("organism part", "organism");
    }

    @Test
    void parseHeaderWithCharacteristicsOnly() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();

        dataFileHub.addSdrfFile(experimentAccession, Arrays.asList(SDRF_TXT_NO_FACTORS));

        Map<String, Set<String>> result = subject.parseHeader(experimentAccession);

        assertThat(result)
                .hasSize(1)
                .containsOnlyKeys(CHARACTERISTICS)
                .doesNotContainKeys(FACTORS);

        assertThat(result.get(CHARACTERISTICS))
                .containsExactly("organism", "developmental stage", "organism part");
    }

}
