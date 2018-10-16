package uk.ac.ebi.atlas.metadata;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.solr.cloud.SolrCloudCollectionProxyFactory;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;
import uk.ac.ebi.atlas.testutils.JdbcUtils;
import uk.ac.ebi.atlas.testutils.RandomDataTestUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CellMetadataDaoIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(CellMetadataDaoIT.class);

    @Inject
    private SolrCloudCollectionProxyFactory solrCloudCollectionProxyFactory;
    @Inject
    private JdbcUtils jdbcUtils;
    @Inject
    private IdfParser idfParser;

    private CellMetadataDao subject;

    @BeforeEach
    void setUp() {
        this.subject = new CellMetadataDao(solrCloudCollectionProxyFactory, idfParser);
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    void validExperimentAccessionHasMetadataFields(String experimentAccession) {
        assertThat(subject.getMetadataFieldNames(experimentAccession)).isNotEmpty();
    }

    @Test
    void invalidExperimentAccessionHasNoMetadata() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();

        assertThat(subject.getMetadataFieldNames(experimentAccession)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    void invalidCellIdHasNoMetadata(String experimentAccession) {
        String cellId = "FOOBAR";

        List<SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames)
                .isNotEmpty()
                .allSatisfy(field ->
                        assertThat(subject.getMetadataValueForCellId(experimentAccession, field, cellId))
                                .isNotPresent());
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    void validCellIdHasMetadataValues(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        LOGGER.info("Retrieving metadata field names for experiment {}", experimentAccession);
        List<SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames)
                .isNotEmpty()
                .allSatisfy(field -> {
                    LOGGER.info(
                            "Retrieving values for {} metadata for cell ID {} from experiment {}",
                            field.displayName(), cellId, experimentAccession);

                    assertThat(subject.getMetadataValueForCellId(experimentAccession, field, cellId)).isPresent();
                });
    }

    @ParameterizedTest
    @MethodSource("experimentsWithFactorsProvider")
    void validExperimentAccessionHasFactorFields(String experimentAccession) {
        String cellId = jdbcUtils.fetchRandomCellFromExperiment(experimentAccession);

        LOGGER.info("Retrieving factor fields for cell ID {} from experiment {}", cellId, experimentAccession);
        assertThat(subject.getFactorFieldNames(experimentAccession, cellId)).isNotEmpty();
    }

    @Test
    void invalidCellIdAndExperimentAccessionHasNoFactorFields() {
        String experimentAccession = RandomDataTestUtils.getRandomExperimentAccession();
        String cellId = "FOO";

        assertThat(subject.getFactorFieldNames(experimentAccession, cellId)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithMetadataProvider")
    void validCellIdsHaveMetadataValues(String experimentAccession) {
        List<String> cellIds =
                jdbcUtils.fetchRandomListOfCellsFromExperiment(
                        experimentAccession, ThreadLocalRandom.current().nextInt(1, 2000));

        LOGGER.info("Retrieving metadata field names for experiment {}", experimentAccession);
        List<SingleCellAnalyticsSchemaField> metadataFieldNames = subject.getMetadataFieldNames(experimentAccession);

        assertThat(metadataFieldNames)
                .isNotEmpty()
                .allSatisfy(field -> {
                    LOGGER.info(
                            "Retrieving values for {} metadata for {} random cell IDs from experiment {}",
                            field.displayName(), cellIds.size(), experimentAccession);

                    assertThat(subject.getMetadataValueForCellIds(experimentAccession, field, cellIds))
                            .isNotEmpty()
                            .containsKeys(cellIds.toArray(new String[0]));
                });
    }

    @Test
    void getFieldValuesForNoFieldsReturnsEmpty() {
        String experimentAccession = jdbcUtils.fetchRandomSingleCellExperimentAccession();

        assertThat(subject.getQueryResultForMultiValueFields(experimentAccession, Optional.empty(), ImmutableSet.of()))
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithAdditionalAttributesProvider")
    void validExperimentIdHasAdditionalAttributes(String experimentAccession) {
        assertThat(subject.getAdditionalAttributesFieldNames(experimentAccession)).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("experimentsWithoutAdditionalAttributesProvider")
    void invalidExperimentAccessionHasNoAdditionalAttributes(String experimentAccession) {
        assertThat(subject.getAdditionalAttributesFieldNames(experimentAccession)).isEmpty();
    }

    private Stream<String> experimentsWithMetadataProvider() {
        // E-GEOD-99058 does not have any metadata (factors or inferred cell types)
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !accession.equalsIgnoreCase("E-GEOD-99058"));
    }

    private Stream<String> experimentsWithFactorsProvider() {
        // E-GEOD-99058 and E-ENAD-13 do not have any factors
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession ->
                        !accession.equalsIgnoreCase("E-GEOD-99058")
                                && !accession.equalsIgnoreCase("E-ENAD-13"));
    }

    private Stream<String> experimentsWithAdditionalAttributesProvider() {
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> !idfParser.parse(accession).getMetadataFieldsOfInterest().isEmpty());
    }

    private Stream<String> experimentsWithoutAdditionalAttributesProvider() {
        return jdbcUtils.getPublicSingleCellExperimentAccessions()
                .stream()
                .filter(accession -> idfParser.parse(accession).getMetadataFieldsOfInterest().isEmpty());
    }
}
